package IntellijProject.MIOProjektImpfausweis.src.main.java;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Impfausweis {

    public static void main(String args[]){

        // Create a context for R4
        FhirContext ctxR4 = FhirContext.forR4();

        // Connect to a R4 compliant server
        //String serverBase = "https://funke.imi.uni-luebeck.de/public/fhir";
        //IGenericClient client = ctxR4.newRestfulGenericClient(serverBase);


        Patient patient = createPatient();
        Encounter encounter1 = createEncounter(patient);
        Practitioner practitioner1 = createPractitioner();
        Organization organization = createOrganization();
        PractitionerRole practitionerRole = createPractitionerRole(organization, practitioner1);
        Immunization vac1 = createVaccination(patient, encounter1, practitioner1, "IFPA", "urn:oid:1.2.36.1.2001.1005.17", "Infanrix Penta","1997-08-12");


        Bundle bundle = new Bundle();
        bundle.addEntry().setResource(patient).getRequest().setUrl(patient.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter1).getRequest().setUrl(encounter1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(practitioner1).getRequest().setUrl(practitioner1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(organization).getRequest().setUrl(organization.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(practitionerRole).getRequest().setUrl(practitionerRole.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(vac1).getRequest().setUrl(vac1.fhirType()).setMethod(Bundle.HTTPVerb.POST);


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

        // Set birth date of the patient object
        patient.setBirthDateElement(new DateType("1995-05-17"));

        patient.addAddress()
                .setUse(Address.AddressUse.HOME)
                .setType(Address.AddressType.BOTH)
                .addLine("An der Obertrave 49")
                .setCity("Lübeck")
                .setPostalCode("23552")
                .setCountry("DE");

        Extension ext = new Extension();
        ext.setUrl("http://hl7.org/fhir/StructureDefinition/patient-birthPlace");
        Address birthplace = new Address();
        birthplace.setCity("Kiel");
        ext.setValue(birthplace);
        patient.addExtension(ext);

        return patient;

    }



    public static Immunization createVaccination(Patient patient, Encounter encounter, Practitioner practitioner, String vacCode, String vacSystem, String vacDisplay, String date) {
        Immunization vac = new Immunization();

        vac.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/vaccination")
                .setValue(UUID.randomUUID().toString());

        vac.setStatus(Immunization.ImmunizationStatus.COMPLETED);

        vac.setVaccineCode(new CodeableConcept()
            .addCoding(new Coding()
                    .setCode(vacCode)
                    .setSystem(vacSystem)
                    .setDisplay(vacDisplay)
            )
        );

        vac.setPatient(new Reference()
                .setIdentifier(patient.getIdentifierFirstRep())
                .setReference(patient.fhirType() + "/" +patient.getId()));

        vac.setEncounter(new Reference()
                .setIdentifier(encounter.getIdentifierFirstRep())
                .setReference(encounter.fhirType() + "/" + encounter.getId()));

        vac.setOccurrence(new DateTimeType(date));

        //Location
        //Manufacturer

        //lotNumber
        vac.setLotNumber("S2409F");

        //*performer -> Practitioner
        vac.addPerformer().setActor(new Reference()
                .setIdentifier(practitioner.getIdentifierFirstRep())
                .setReference(practitioner.fhirType() + "/" + practitioner.getId()));



        return vac;
    }



    public static Encounter createEncounter(Patient patient){
        // Create an encounter object
        Encounter encounter = new Encounter();

        // Set Identifier of the encounter object
        encounter.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/encounter")
                .setValue(UUID.randomUUID().toString());

        encounter.setStatus(Encounter.EncounterStatus.FINISHED);

        //TODO Code eventuell auch AMB mit display value ambulatory
        encounter.setClass_(new Coding()
                .setCode("NONAC")
                .setSystem("http://terminology.hl7.org/CodeSystem/v3-ActCode")
                .setDisplay("inpatient non-acute"));

        encounter.setServiceType(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("57")
                        .setSystem("http://terminology.hl7.org/CodeSystem/service-type")
                        .setDisplay("Immunization")));

        encounter.setSubject(new Reference()
                .setIdentifier(patient.getIdentifierFirstRep())
                .setReference(patient.fhirType() + "/" + patient.getId()));

        //TODO Arzt und Zeit irgendwie hinzufügen

        return encounter;
    }



    public static Practitioner createPractitioner(){
        Practitioner practitioner  =  new Practitioner();

        practitioner.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/practitioner")
                .setValue(UUID.randomUUID().toString());

        practitioner.addName()
                .setUse(HumanName.NameUse.OFFICIAL)
                .setFamily("Wolf")
                .addGiven("Lucas")
                .addPrefix("Dr");

        practitioner.addQualification().setCode(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("MD")
                        .setSystem("http://hl7.org/fhir/v2/0360/2.7")
                        .setDisplay("Doctor of Medicine")));

        return practitioner;
    }



    public static Organization createOrganization(){
        // Krankenhaus
        Organization organization= new Organization();

        organization.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/organization")
                .setValue(UUID.randomUUID().toString());

        organization.setName("MIO Krankenhaus");

        organization.addAddress()
                .setCity("Lübeck")
                .setCountry("Deutschland")
                .setPostalCode("23562");

        return organization;
    }


    
    public static PractitionerRole createPractitionerRole(Organization organization, Practitioner practitioner){
        // Rollenzuweisung
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
