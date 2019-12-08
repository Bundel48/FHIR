package IntellijProject.MIOProjektImpfausweis.src.main.java;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

import java.util.UUID;

/**
 * Class to create a certificate of vaccinations.
 *
 * @author Leonie Basso, Natascha Koch, Finja Alexandra Ottink
 */
public class Impfausweis {
    
    public static void main(String args[]){

        // Create a context for R4
        FhirContext ctxR4 = FhirContext.forR4();

        // Connect to a R4 compliant server
        //String serverBase = "https://funke.imi.uni-luebeck.de/public/fhir";
        //IGenericClient client = ctxR4.newRestfulGenericClient(serverBase);



        Patient patient = createPatient("Reid", "Erik", "MALE", "1997-05-17", "An der Obertrave 49", "Lübeck", "23552", "DE", "Kiel", "DE");
        // TODO mehrere Organisationen: z.B. Krankenhaus oder Impfstelle, Kinderarztpraxis, vaccinationOrganization
        Organization organization = createOrganization(true, "MIO Krankenhaus", "Lübeck" , "DE", "23562");
        // TODO mehr practitioner: z.B. Kinderarzt und Arzt
        Practitioner practitioner = createPractitioner("Lucas", "Wolf", "Dr.", "MD", "Doctor of Medicine");
        // TODO Rollen für jeden Practitioner
        PractitionerRole practitionerRole = createPractitionerRole(organization, practitioner);
        // TODO Encounter für jede einzelne Impfung anlegen
        Encounter encounter = createEncounter(patient, practitioner, organization,"1997-08-12");
        // TODO mehr Impfungen anlegen
        Immunization immunization = createImmunization(patient, encounter, practitioner, "IFPA", "urn:oid:1.2.36.1.2001.1005.17", "Infanrix Penta","1997-08-12","S2409F");


        Bundle bundle = new Bundle();
        bundle.addEntry().setResource(patient).getRequest().setUrl(patient.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(organization).getRequest().setUrl(organization.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(practitioner).getRequest().setUrl(practitioner.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(practitionerRole).getRequest().setUrl(practitionerRole.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter).getRequest().setUrl(encounter.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization).getRequest().setUrl(immunization.fhirType()).setMethod(Bundle.HTTPVerb.POST);


        // Parser to encode the resource into a string in json format
        String encoded = ctxR4.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle);
        System.out.println(encoded);


        // for pushing upon server !
        //Bundle resp = client.transaction().withBundle(bundle).execute();

        // Log the response
        //System.out.println(ctxR4.newJsonParser().setPrettyPrint(true).encodeResourceToString(resp));

    }



    /**
     * The method createPatient() creates a new Patient object.
     *
     * @param givenName The given name of the patient as String.
     * @param familyName The surname of the patient as String.
     * @param gender The gender of the patient as String. Possible values: MALE, FEMALE, OTHER, UNKNOWN.
     * @param birthdate The birthdate of the patient as String. Format: YYYY-MM-DD
     * @param line The street where the patient lives as String.
     * @param city The city where the patient lives as String.
     * @param postalCode The postal code of the city the patient lives in as String.
     * @param country The country code as String of the country the patient lives in. For example: DE for Germany
     * @param birthplaceCity The city where the patient was born as String.
     * @param birthplaceCountry The country code as String where the patient was born. For example: DE for Germany.
     * @return the patient object
     */
    public static Patient createPatient(String givenName, String familyName, String gender, String birthdate, String line, String city, String postalCode, String country, String birthplaceCity, String birthplaceCountry){
        // Create a patient object
        Patient patient = new Patient();

        // Set Identifier of the patient object
        patient.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/patients")
                .setValue(UUID.randomUUID().toString());

        // Set official name of the patient object
        patient.addName()
                .setUse(HumanName.NameUse.OFFICIAL)
                .setFamily(familyName)
                .addGiven(givenName);

        // Set gender of the patient object
        if(gender.equals("MALE")){
            patient.setGender(Enumerations.AdministrativeGender.MALE);
        }
        else if(gender.equals("FEMALE")){
            patient.setGender(Enumerations.AdministrativeGender.FEMALE);
        }
        else if(gender.equals("OTHER")){
            patient.setGender(Enumerations.AdministrativeGender.OTHER);
        }
        else{
            patient.setGender(Enumerations.AdministrativeGender.UNKNOWN);
        }

        // Set birth date of the patient object
        patient.setBirthDateElement(new DateType(birthdate));

        // Set address date of the patient object
        patient.addAddress()
                .setUse(Address.AddressUse.HOME)
                .setType(Address.AddressType.BOTH)
                .addLine(line)
                .setCity(city)
                .setPostalCode(postalCode)
                .setCountry(country);

        // Set birthplace of the patient object
        Extension ext = new Extension();
        ext.setUrl("http://hl7.org/fhir/StructureDefinition/patient-birthPlace");
        Address birthplace = new Address();
        birthplace.setCity(birthplaceCity).setCountry(birthplaceCountry);
        ext.setValue(birthplace);
        patient.addExtension(ext);

        return patient;
    }



    /**
     * The method createOrganization() creates a new organization objects which can be for example a hosptial.
     *
     * @param active The parameter active declares whether the organization's record is still in active use.
     *               Possible values: true, false
     * @param organizationName The name of the organisation as String.
     * @param organizationCity The city where the organization is located as String.
     * @param organizationCountry The country code where the organization is located as Sting, for example DE for Germany.
     * @param organizationPostalCode The postalCode of the organization as String.
     * @return a new Organization object
     */
    public static Organization createOrganization(Boolean active, String organizationName, String organizationCity, String organizationCountry, String organizationPostalCode){
        // Create an organization object
        Organization organization= new Organization();

        // Set Identifier of the organization object
        organization.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/organization")
                .setValue(UUID.randomUUID().toString());

        // Set if the organization's record is still in active use
        organization.setActive(active);

        // Set kind of organization
        organization.addType(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("prov")
                        .setSystem("http://terminology.hl7.org/CodeSystem/organization-type")
                        .setDisplay("Healthcare Provider")));

        // Set name of the organization object
        organization.setName(organizationName);

        // Set address of the organization object
        organization.addAddress()
                .setCity(organizationCity)
                .setCountry(organizationCountry)
                .setPostalCode(organizationPostalCode);
        //TODO evtl. addLine

        return organization;
    }



    /**
     * The method createPractitioner() creates a new practitioner object, for example a doctor.
     *
     * @param givenName The given name of the practitioner as String.
     * @param familiyName The surname of the practitioner as String.
     * @param prefix The prefix of the practitioner as String. For example Dr. if the practitioner is a doctor. If the
     *               practitioner does not have any prefix, type NONE.
     * @param qualificationCode The qualification code which represents the qualification of the practitioner as String.
     *                          The code is from http://hl7.org/fhir/v2/0360/2.7/
     * @param qualificationDisplay The String that belongs to the qualification code and that is displayed.
     * @return a practitioner object
     */
    public static Practitioner createPractitioner(String givenName, String familiyName, String prefix, String qualificationCode, String qualificationDisplay){
        // Create a practitioner object
        Practitioner practitioner  =  new Practitioner();

        // Set Identifier of the practitioner object
        practitioner.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/practitioner")
                .setValue(UUID.randomUUID().toString());

        // Set name of the practitioner object

        HumanName name = new HumanName();
        name.setUse(HumanName.NameUse.OFFICIAL);
        name.setFamily(familiyName);
        name.addGiven(givenName);

        if(!prefix.equals("NONE")){
            name.addPrefix(prefix);
        }

        practitioner.addName(name);

        // TODO add address,gender,birthdate? Ist das notwendig?

        // Set qualification of the practitioner object
        practitioner.addQualification().setCode(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode(qualificationCode)
                        .setSystem("http://hl7.org/fhir/v2/0360/2.7")
                        .setDisplay(qualificationDisplay)));

        return practitioner;
    }



    /**
     * The method createPractitionerRole creates a role that the practitioner may perform at an organization.
     *
     * @param organization The organization object the practitioner belongs to. The role references the organization.
     *                     This may be for example a hospital the practitioner is working for.
     * @param practitioner The practitioner object that the role belongs to.
     * @return returns a practitionerRole object
     */
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

        // add role the practitioner may perform
        /*
        practitionerRole.addCode(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("doctor")
                        .setSystem("http://hl7.org/fhir/ValueSet/practitioner-role")
                        .setDisplay("Doctor")));
         */

        return practitionerRole;
    }



    /**
     * The method createEncounter() creates a new Encounter object. This describes the date of the encounter and the
     * participating patient and practitioner and the organization that is responsible for the encounter. The encounter
     * is referenced by one immunization.
     *
     * @param patient The patient object that was present at the encounter and got the immunization.
     * @param practitioner The practitioner object that who was involved in the encounter.
     * @param organization The organization object that is responsible for the encounter, for example a hospital.
     * @param date The date when the encounter took place as String.
     * @return returns an Encounter object.
     */
    public static Encounter createEncounter(Patient patient, Practitioner practitioner, Organization organization, String date){
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
                .setStartElement(new DateTimeType(date))
                .setEndElement(new DateTimeType(date)));

        // Set reference to the organization that is responsible for the encounter
        encounter.setServiceProvider(new Reference()
                .setIdentifier(organization.getIdentifierFirstRep())
                .setReference(organization.fhirType() + "/" + organization.getId()));

        return encounter;
    }


    /**
     * The method createImmunization() creates a new immunization. That immunization references a patient, who has been
     * immunized and an encounter that belongs to this immunization.
     * This method can only be used to create immunizations for Yellow Fever, standard vaccinations (like tetanus,
     * diphtheria, pertussis, Hib, Hepatitis B, Poliomyelitis, Measles, Mumps, Rubella), other vaccinations (like
     * cholera, FSME, Hepatitis A, Meningokokken, Pneumokokken, Typhus, Varizellen), Vaccinations against Influenza and
     * Vaccinations against tuberculosis (BCG). For other immunizations another method has to be used.
     *
     * @param patient The patient object that has been immunized.
     * @param encounter The encounter object for this immunization.
     * @param practitioner The practitioner who was performing the immunization.
     * @param immunizationCode The code of the vaccine product.
     * @param immunizationSystem The system used can be found here http://hl7.org/fhir/valueset-vaccine-code.html
     * @param immunizationDisplay The display infromation that belongs to the immunizationCode.
     * @param date The date when the immunization took place.
     * @return a new Immunization object
     */
    public static Immunization createImmunization(Patient patient, Encounter encounter, Practitioner practitioner, String immunizationCode, String immunizationSystem, String immunizationDisplay, String date, String lotNumber) {
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
                    .setCode(immunizationCode)
                    .setSystem(immunizationSystem)
                    .setDisplay(immunizationDisplay)
            )
        );
        // TODO Kann man irgendwie codieren, gegen welche Krankheiten man bei Kombinationsimpfstoffen geimpft wird?

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

        //TODO Location???
        //TODO Manufacturer???

        //Set lotNumber
        immunization.setLotNumber(lotNumber);

        // Set reference to the actor(practitioner) who performed the immunization
        immunization.addPerformer().setActor(new Reference()
                .setIdentifier(practitioner.getIdentifierFirstRep())
                .setReference(practitioner.fhirType() + "/" + practitioner.getId()));

        return immunization;
    }

}
