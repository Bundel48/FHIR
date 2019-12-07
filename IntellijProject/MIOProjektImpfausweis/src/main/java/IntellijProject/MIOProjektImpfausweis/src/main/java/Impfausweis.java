package IntellijProject.MIOProjektImpfausweis.src.main.java;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

import java.util.Date;
import java.util.UUID;

public class Impfausweis {

    public static void main(String args[]){

        // Create a context for R4
        FhirContext ctxR4 = FhirContext.forR4();

        // Connect to a R4 compliant server
        //String serverBase = "https://funke.imi.uni-luebeck.de/public/fhir";
        //IGenericClient client = ctxR4.newRestfulGenericClient(serverBase);


        Patient patient = createPatient();
        // TODO mehr practitioner: z.B. Kinderarzt und Arzt
        Practitioner practitioner = createPractitioner();
        // TODO mehrere Organisationen: z.B. Krankenhaus oder Impfstelle, Kinderarztpraxis, vaccinationOrganization
        Organization organization = createOrganization();
        // TODO Encounter für jede einzelne Impfung anlegen
        Encounter encounter = createEncounter(patient, practitioner, organization);
        // TODO Rollen für jeden Practitioner
        PractitionerRole practitionerRole = createPractitionerRole(organization, practitioner);
        // TODO mehr Impfungen anlegen
        Immunization immunization = createVaccination(patient, encounter, practitioner, "IFPA", "urn:oid:1.2.36.1.2001.1005.17", "Infanrix Penta","1997-08-12");


        Bundle bundle = new Bundle();
        bundle.addEntry().setResource(patient).getRequest().setUrl(patient.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter).getRequest().setUrl(encounter.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(practitioner).getRequest().setUrl(practitioner.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(organization).getRequest().setUrl(organization.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(practitionerRole).getRequest().setUrl(practitionerRole.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization).getRequest().setUrl(immunization.fhirType()).setMethod(Bundle.HTTPVerb.POST);


        // Parser to encode the resource into a string in json format
        String encoded = ctxR4.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle);
        System.out.println(encoded);


        // for pushing upon server !
        //Bundle resp = client.transaction().withBundle(bundle).execute();

        // Log the response
        //System.out.println(ctxR4.newJsonParser().setPrettyPrint(true).encodeResourceToString(resp));

    }



    public static Patient createPatient(){
        // Create a patient object
        Patient patient = new Patient();

        // Set Identifier of the patient object
        patient.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/patients")
                .setValue(UUID.randomUUID().toString());

        // Set official name of the patient object
        patient.addName()
                .setUse(HumanName.NameUse.OFFICIAL)
                .setFamily("Reid")
                .addGiven("Erik");

        // Set gender of the patient object
        patient.setGender(Enumerations.AdministrativeGender.MALE);

        // Set birth date of the patient object
        patient.setBirthDateElement(new DateType("1997-05-17"));

        // Set address date of the patient object
        patient.addAddress()
                .setUse(Address.AddressUse.HOME)
                .setType(Address.AddressType.BOTH)
                .addLine("An der Obertrave 49")
                .setCity("Lübeck")
                .setPostalCode("23552")
                .setCountry("DE");

        // Set birthplace of the patient object
        Extension ext = new Extension();
        ext.setUrl("http://hl7.org/fhir/StructureDefinition/patient-birthPlace");
        Address birthplace = new Address();
        birthplace.setCity("Kiel").setCountry("DE");
        ext.setValue(birthplace);
        patient.addExtension(ext);

        return patient;

    }



    public static Immunization createVaccination(Patient patient, Encounter encounter, Practitioner practitioner, String vacCode, String vacSystem, String vacDisplay, String date) {
        // Create an immunization object
        Immunization immunization = new Immunization();

        // Set Identifier of the immunization object
        immunization.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/vaccination")
                .setValue(UUID.randomUUID().toString());

        // Set status of the immunization object
        immunization.setStatus(Immunization.ImmunizationStatus.COMPLETED);

        // Set VaccineCode of the immunization object
        immunization.setVaccineCode(new CodeableConcept()
            .addCoding(new Coding()
                    .setCode(vacCode)
                    .setSystem(vacSystem)
                    .setDisplay(vacDisplay)
            )
        );

        // Set reference to the patient belonging to the immunization
        immunization.setPatient(new Reference()
                .setIdentifier(patient.getIdentifierFirstRep())
                .setReference(patient.fhirType() + "/" +patient.getId()));

        // Set reference to the encounter belonging to the immunization
        immunization.setEncounter(new Reference()
                .setIdentifier(encounter.getIdentifierFirstRep())
                .setReference(encounter.fhirType() + "/" + encounter.getId()));

        // Set vaccine administration date
        immunization.setOccurrence(new DateTimeType(date));

        //Location
        //Manufacturer

        //Set lotNumber
        immunization.setLotNumber("S2409F");

        // Set reference to the actor(practitioner) who performed the immunization
        immunization.addPerformer().setActor(new Reference()
                .setIdentifier(practitioner.getIdentifierFirstRep())
                .setReference(practitioner.fhirType() + "/" + practitioner.getId()));

        return immunization;
    }



    public static Encounter createEncounter(Patient patient, Practitioner practitioner, Organization organization){
        // Create an encounter object
        Encounter encounter = new Encounter();

        // Set Identifier of the encounter object
        encounter.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/encounter")
                .setValue(UUID.randomUUID().toString());

        // Set Status of the encounter object
        encounter.setStatus(Encounter.EncounterStatus.FINISHED);

        // Set classification of the encounter object
        encounter.setClass_(new Coding()
                .setCode("AMB")
                .setSystem("http://terminology.hl7.org/CodeSystem/v3-ActCode")
                .setDisplay("ambulatory"));

        // Set specific type of service of the encounter object
        encounter.setServiceType(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("57")
                        .setSystem("http://terminology.hl7.org/CodeSystem/service-type")
                        .setDisplay("Immunization")));

        // Set reference to the patient that was present at the encounter
        encounter.setSubject(new Reference()
                .setIdentifier(patient.getIdentifierFirstRep())
                .setReference(patient.fhirType() + "/" + patient.getId()));

        // Set reference to the practitioner who was involved in the encounter
        encounter.addParticipant().setIndividual(new Reference()
                .setIdentifier(practitioner.getIdentifierFirstRep())
                .setReference(practitioner.fhirType() + "/" + practitioner.getId()));

        // Set period when the immunization took place
        encounter.setPeriod(new Period()
                .setStartElement(new DateTimeType("1997-08-12"))
                .setEndElement(new DateTimeType("1997-08-12")));

        // Set reference to the organization that is responsible for the encounter
        encounter.setServiceProvider(new Reference()
                .setIdentifier(organization.getIdentifierFirstRep())
                .setReference(organization.fhirType() + "/" + organization.getId()));

        return encounter;
    }



    public static Practitioner createPractitioner(){
        // Create a practitioner object
        Practitioner practitioner  =  new Practitioner();

        // Set Identifier of the practitioner object
        practitioner.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/practitioner")
                .setValue(UUID.randomUUID().toString());

        // Set name of the practitioner object
        practitioner.addName()
                .setUse(HumanName.NameUse.OFFICIAL)
                .setFamily("Wolf")
                .addGiven("Lucas")
                .addPrefix("Dr.");

        // Set qualification of the practitioner object
        practitioner.addQualification().setCode(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("MD")
                        .setSystem("http://hl7.org/fhir/v2/0360/2.7")
                        .setDisplay("Doctor of Medicine")));

        return practitioner;
    }



    public static Organization createOrganization(){
        // Create an organization object
        Organization organization= new Organization();

        // Set Identifier of the organization object
        organization.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/organization")
                .setValue(UUID.randomUUID().toString());

        // Set if the organization's record is still in active use
        organization.setActive(true);

        // Set kind of organization
        organization.addType(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("prov")
                        .setSystem("http://terminology.hl7.org/CodeSystem/organization-type")
                        .setDisplay("Healthcare Provider")));

        // Set name of the organization object
        organization.setName("MIO Krankenhaus");

        // Set address of the organization object
        organization.addAddress()
                .setCity("Lübeck")
                .setCountry("Deutschland")
                .setPostalCode("23562");
        //TODO evtl. addLine

        return organization;
    }



    public static PractitionerRole createPractitionerRole(Organization organization, Practitioner practitioner){
        // Create practitionerRole
        PractitionerRole practitionerRole =
                new PractitionerRole()
                        .setOrganization(new Reference()
                                .setIdentifier(organization.getIdentifier().get(0))
                                .setReference(organization.fhirType() + "/" + organization.getId()))
                        .setPractitioner(new Reference()
                                .setIdentifier(practitioner.getIdentifier().get(0))
                                .setReference(practitioner.fhirType() + "/" + practitioner.getId()));

        return practitionerRole;
    }

}
