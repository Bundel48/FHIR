package IntellijProject.MIOProjektImpfausweis.src.main.java;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

import java.sql.Ref;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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



        // ------------------------------Create Patient----------------------------------------------------------------
        Patient patient = createPatient("Reid", "Erik", "MALE", "1997-05-17",
                "An der Obertrave 49", "Lübeck", "23552", "DE", "Lübeck",
                "DE");
        //patient.setId("");


        // ------------------------------Create Organizations----------------------------------------------------------
        // TODO vaccinationOrganization?
        Organization organizationHospital = createOrganization(true,
                "Universitätsklinikum Schleswig-Holstein Campus Lübeck",
                "Ratzeburger Allee 160","Lübeck" , "23538",
                "DE");
        //organizationHospital.setId("#OrganizationID");
        // Reference: Organization/#OrganizationID

        Organization organizationInstitute = createOrganization(true,
                "Institut für Transfusionsmedizin", "Ratzeburger Allee 160, Haus 31",
                "Lübeck", "23538", "DE");
        organizationInstitute.setPartOf(new Reference()
                .setIdentifier(organizationHospital.getIdentifierFirstRep())
                .setReference(organizationHospital.fhirType() + "/" + organizationHospital.getId()));
        //organizationInstitute.setId("");

        Organization organizationMedicalCenter = createOrganization(true,
                "Medizinisches Gesundheitszentrum", "Paul-Ehrlich-Straße 3",
                "Lübeck", "23562", "DE");
        //organizationMedicalCenter.setId("");


        // ------------------------------Create Practitioners----------------------------------------------------------
        // TODO mehr practitioner: z.B. Kinderarzt und Arzt
        Practitioner practitioner1 = createPractitioner("Lucas", "Wolf", "Prof. Dr.",
                "MD", "Doctor of Medicine");
        Practitioner practitioner2 = createPractitioner("Frieda", "May", "Dr.",
                "MD", "Doctor of Medicine");
        Practitioner practitioner3 = createPractitioner("Laura", "Adams", "Dr.",
                "MD", "Doctor of Medicine");
        Practitioner practitioner4 = createPractitioner("Artur", "Beck", "Dr.",
                "MD", "Doctor of Medicine");
        //practitioner1.setId("");
        //practitioner2.setId("");
        //practitioner3.setId("");
        //practitioner4.setId("");


        /*
        // ------------------------------Create PractitionerRoles------------------------------------------------------
        // TODO Rollen für jeden Practitioner
        PractitionerRole practitionerRole1 = createPractitionerRole(organizationMedicalCenter, practitioner1);
        PractitionerRole practitionerRole2 = createPractitionerRole(organizationMedicalCenter, practitioner2);
        PractitionerRole practitionerRole3 = createPractitionerRole(organizationMedicalCenter, practitioner3);
        PractitionerRole practitionerRole4 = createPractitionerRole(organizationInstitute, practitioner4);
        //practitionerRole1.setId("");
        //practitionerRole2.setId("");
        //practitionerRole3.setId("");
        //practitionerRole4.setId("");
        */


        // ------------------------------Create Encounters-------------------------------------------------------------
        Encounter encounter1 = createEncounter(patient, practitioner1, organizationMedicalCenter,"1997-08-23");
        Encounter encounter2 = createEncounter(patient, practitioner1, organizationMedicalCenter,"1997-09-20");
        Encounter encounter3 = createEncounter(patient, practitioner2, organizationMedicalCenter,"1997-10-27");
        Encounter encounter4 = createEncounter(patient, practitioner3, organizationMedicalCenter,"1998-09-12");
        Encounter encounter5 = createEncounter(patient, practitioner1, organizationMedicalCenter,"1998-12-10");
        Encounter encounter6 = createEncounter(patient, practitioner3, organizationMedicalCenter,"2000-02-10");
        Encounter encounter7 = createEncounter(patient, practitioner3, organizationMedicalCenter,"2000-03-12");
        Encounter encounter8 = createEncounter(patient, practitioner2, organizationMedicalCenter,"2000-08-06");
        Encounter encounter9 = createEncounter(patient, practitioner1, organizationMedicalCenter,"2001-04-30");
        Encounter encounter10 = createEncounter(patient, practitioner3, organizationMedicalCenter,"2006-08-28");
        Encounter encounter11 = createEncounter(patient, practitioner2, organizationMedicalCenter,"2011-03-13");
        //encounter1.setId("");
        //encounter2.setId("");
        //encounter3.setId("");
        //encounter4.setId("");
        //encounter5.setId("");
        //encounter6.setId("");
        //encounter7.setId("");
        //encounter8.setId("");
        //encounter9.setId("");
        //encounter10.setId("");
        //encounter11.setId("");


        // ------------------------------Create Immunizations----------------------------------------------------------
        // IFIP for Tetanus, Diphththerie, Pertussis, Hib, Poliomyelitis ?
        // ENGP for Hepatitis B
        // MMRSKB for Measles, Mumps, Rubella
        // IFX for Tetanus, Diphththerie, Pertussis
        //TODO 2,10,11 IFIP right?, lotNumber anpassen
        //TODO fragen, welche Impfungen hinter welchem Code stecken; was wenn es die entsprechende Impfung nicht mehr
        // so gibt oder keinen Code dafür; ist das bei fhir die aktuelle Liste an Codes

        Immunization immunization1 = createImmunization(patient, encounter1, "IFIP",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix-IPV",
                "1997-08-23","S2409F");
        Immunization immunization2 = createImmunization(patient, encounter2, "IFIP",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix-IPV",
                "1997-09-20","S2409F");
        Immunization immunization3 = createImmunization(patient, encounter3, "IFIP",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix-IPV",
                "1997-10-27","S2409F");
        Immunization immunization4 = createImmunization(patient, encounter4, "MMRSKB",
                "urn:oid:1.2.36.1.2001.1005.17", "Priorix",
                "1998-09-12","S2409F");
        Immunization immunization5 = createImmunization(patient, encounter5, "IFIP",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix-IPV",
                "1998-12-10","S2409F");
        Immunization immunization6 = createImmunization(patient, encounter6, "ENGP",
                "urn:oid:1.2.36.1.2001.1005.17", "Engerix B",
                "2000-02-10","S2409F");
        Immunization immunization7 = createImmunization(patient, encounter7, "ENGP",
                "urn:oid:1.2.36.1.2001.1005.17", "Engerix B",
                "2000-03-12","S2409F");
        Immunization immunization8 = createImmunization(patient, encounter8, "MMRSKB",
                "urn:oid:1.2.36.1.2001.1005.17", "Priorix",
                "2000-08-06","S2409F");
        Immunization immunization9 = createImmunization(patient, encounter9, "ENGP",
                "urn:oid:1.2.36.1.2001.1005.17", "Engerix B",
                "2001-04-30","S2409F");
        Immunization immunization10 = createImmunization(patient, encounter10, "IFIP",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix-IPV",
                "2006-08-28","S2409F");
        Immunization immunization11 = createImmunization(patient, encounter11, "IFX",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix",
                "2011-03-13","S2409F");
        //immunization1.setId("");
        //immunization2.setId("");
        //immunization3.setId("");
        //immunization4.setId("");
        //immunization5.setId("");
        //immunization6.setId("");
        //immunization7.setId("");
        //immunization8.setId("");
        //immunization9.setId("");
        //immunization10.setId("");
        //immunization11.setId("");


        // ------------------------------Create Observations----------------------------------------------------------
        Observation observation1 = createObservation(patient, encounter1,"39263-9",
                "Tuberculin screen test status CPHS", "1997-08-23");


        // ------------------------------Create Conditions------------------------------------------------------------
        Condition condition1 = createCondition(patient, encounter1,  "77465005",
                " Transplantation (procedure)", "1997-08-23");


        // ------------------------------Create Array Lists needed for Composition------------------------------------
        ArrayList<Immunization> ArrayListYellowFever = new ArrayList<Immunization>();

        ArrayList<Immunization> ArrayListStandardVaccinations = new ArrayList<Immunization>();
        ArrayListStandardVaccinations.add(immunization1);
        ArrayListStandardVaccinations.add(immunization2);

        ArrayList<Immunization> ArrayListOtherVaccinations = new ArrayList<Immunization>();

        ArrayList<Immunization> ArrayListInfluenza = new ArrayList<Immunization>();

        ArrayList<Immunization> ArrayListTuberculosis = new ArrayList<Immunization>();

        ArrayList<Observation> ArrayListTuberculinTests = new ArrayList<Observation>();

        ArrayList<Observation> ArrayListAntibodyAssays = new ArrayList<Observation>();

        ArrayList<Observation> ArrayListHepatitisB = new ArrayList<Observation>();

        ArrayList<Observation> ArrayListRubella = new ArrayList<Observation>();

        ArrayList<Immunization> ArrayListPassiveImmunizations = new ArrayList<Immunization>();

        ArrayList<Immunization> ArrayListSerumInjection = new ArrayList<Immunization>();

        ArrayList<Condition> ArrayListCondition = new ArrayList<Condition>();

        ArrayList<Observation> ArrayListBlood = new ArrayList<Observation>();

        LocalDate currentDate = java.time.LocalDate.now();

        // ------------------------------Create Composition-----------------------------------------------------------
        Composition composition = createComposition(patient, practitioner1, currentDate.toString(),
                ArrayListYellowFever,
                ArrayListStandardVaccinations,
                ArrayListOtherVaccinations,
                ArrayListInfluenza,
                ArrayListTuberculosis,
                ArrayListTuberculinTests,
                ArrayListAntibodyAssays,
                ArrayListHepatitisB,
                ArrayListRubella,
                ArrayListPassiveImmunizations,
                ArrayListSerumInjection,
                ArrayListCondition,
                ArrayListBlood);



        Bundle bundle = new Bundle();
        bundle.addEntry().setResource(patient).getRequest().setUrl(patient.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(organizationHospital).getRequest().setUrl(organizationHospital.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(organizationInstitute).getRequest().setUrl(organizationInstitute.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(organizationMedicalCenter).getRequest().setUrl(organizationMedicalCenter.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(practitioner1).getRequest().setUrl(practitioner1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(practitioner2).getRequest().setUrl(practitioner2.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(practitioner3).getRequest().setUrl(practitioner3.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(practitioner4).getRequest().setUrl(practitioner4.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter1).getRequest().setUrl(encounter1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter2).getRequest().setUrl(encounter2.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter3).getRequest().setUrl(encounter3.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter4).getRequest().setUrl(encounter4.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter5).getRequest().setUrl(encounter5.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter6).getRequest().setUrl(encounter6.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter7).getRequest().setUrl(encounter7.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter8).getRequest().setUrl(encounter8.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter9).getRequest().setUrl(encounter9.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter10).getRequest().setUrl(encounter10.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(encounter11).getRequest().setUrl(encounter11.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization1).getRequest().setUrl(immunization1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization2).getRequest().setUrl(immunization2.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization3).getRequest().setUrl(immunization3.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization4).getRequest().setUrl(immunization4.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization5).getRequest().setUrl(immunization5.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization6).getRequest().setUrl(immunization6.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization7).getRequest().setUrl(immunization7.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization8).getRequest().setUrl(immunization8.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization9).getRequest().setUrl(immunization9.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization10).getRequest().setUrl(immunization10.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(immunization11).getRequest().setUrl(immunization11.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(observation1).getRequest().setUrl(observation1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(condition1).getRequest().setUrl(condition1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(composition).getRequest().setUrl(composition.fhirType()).setMethod(Bundle.HTTPVerb.POST);


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
    public static Patient createPatient(String givenName, String familyName, String gender, String birthdate,
                                        String line, String city, String postalCode, String country,
                                        String birthplaceCity, String birthplaceCountry){
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
    public static Organization createOrganization(Boolean active, String organizationName, String organizationLine,
                                                  String organizationCity, String organizationPostalCode,
                                                  String organizationCountry){
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
                .addLine(organizationLine)
                .setCity(organizationCity)
                .setCountry(organizationCountry)
                .setPostalCode(organizationPostalCode);

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
    public static Practitioner createPractitioner(String givenName, String familiyName, String prefix,
                                                  String qualificationCode, String qualificationDisplay){
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
    public static Encounter createEncounter(Patient patient, Practitioner practitioner, Organization organization,
                                            String date){
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
     * @param immunizationCode The code of the vaccine product.
     * @param immunizationSystem The system used can be found here http://hl7.org/fhir/valueset-vaccine-code.html
     * @param immunizationDisplay The display infromation that belongs to the immunizationCode.
     * @param date The date when the immunization took place.
     * @return a new Immunization object
     */
    public static Immunization createImmunization(Patient patient, Encounter encounter, String immunizationCode,
                                                  String immunizationSystem, String immunizationDisplay, String date,
                                                  String lotNumber) {
        // Create an immunization object
        Immunization immunization = new Immunization();

        // Set Identifier of the immunization object
        immunization.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/immunization")
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

        //TODO Manufacturer???

        //Set lotNumber
        immunization.setLotNumber(lotNumber);

        return immunization;
    }


    /**
     * The method createObservation() creates a new observation. That observation references a patient the observation
     * belongs to and an encounter that belongs to this observation. The Observation can be used for tuberculin-tests,
     * antibody assays, Hepatitis B, Rubella antibody assays.
     *
     * @param patient The patient object that the observation belongs to.
     * @param encounter The encounter this observation was part of.
     * @param observationTypeCode The code of the type of observation (a Loinc Code).
     * @param observationTypeDisplay The displaytext of the type of the observation.
     * @param date The date when the Observation took place.
     * @return a new Observation object.
     */
    public static Observation createObservation(Patient patient, Encounter encounter, String observationTypeCode,
                                                String observationTypeDisplay, String date){
        // Create an observation object
        Observation observation = new Observation();

        // Set Identifier of the observation object
        observation.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/observation")
                .setValue(UUID.randomUUID().toString());

        // Set status of the observation object
        observation.setStatus(Observation.ObservationStatus.FINAL);

        // Set classification type of observation
        observation.addCategory(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("laboratory")
                        .setSystem("http://terminology.hl7.org/CodeSystem/observation-category")
                        .setDisplay("Laboratory")));

        // Set type of observation
        observation.setCode(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode(observationTypeCode)
                        .setSystem("http://loinc.org")
                        .setDisplay(observationTypeDisplay)));

        // Set reference to a patient as a subject of the observation
        observation.setSubject(new Reference()
                .setIdentifier(patient.getIdentifierFirstRep())
                .setReference(patient.fhirType() + "/" + patient.getId()));

        // Set reference to an encounter
        observation.setEncounter(new Reference()
                .setIdentifier(encounter.getIdentifierFirstRep())
                .setReference(encounter.fhirType() + "/" + encounter.getId()));

        // Set time of the observation
        observation.setEffective(new DateTimeType(date));

        // TODO  value, interpretation, note, reference range?

        return observation;
    }


    /**
     * The method createCondition() creates a new condition object. This can be used to represent medical comments on
     * risk factors concerning vaccinations (for example Haemophilia, Diabetes, etc)
     *
     * @param patient The patient object that the condition belongs to.
     * @param encounter The encounter this condition was part of.
     * @param conditionTypeCode Identification of the condition, problem or diagnosis. The code for this (Loinc code).
     * @param conditionTypeDisplay The display text for the code of the condition.
     * @param date The date when the condition was first recorded.
     * @return a new Condition object.
     */
    public static Condition createCondition(Patient patient, Encounter encounter, String conditionTypeCode,
                                            String conditionTypeDisplay, String date){
        // Create a condition object
        Condition condition = new Condition();

        // Set Identifier of the condition object
        condition.addIdentifier()
                .setSystem("http://www.kh-uzl.de/fhir/condition")
                .setValue(UUID.randomUUID().toString());

        // Set clinical status of the condition
        condition.setClinicalStatus(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("active")
                        .setSystem("http://terminology.hl7.org/CodeSystem/condition-clinical")
                        .setDisplay("Active")));

        // Set verification status of the condition
        condition.setVerificationStatus(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("confirmed")
                        .setSystem("http://terminology.hl7.org/CodeSystem/condition-ver-status")
                        .setDisplay("Confirmed")));

        // TODO oder hier ICD-10?
        // Set Identification of the condition, problem or diagnosis
        condition.setCode(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode(conditionTypeCode)
                        .setSystem("http://snomed.info/sct")
                        .setDisplay(conditionTypeDisplay)));

        // Set reference to a patient, that the condition belongs to
        condition.setSubject(new Reference()
                .setIdentifier(patient.getIdentifierFirstRep())
                .setReference(patient.fhirType() + "/" + patient.getId()));

        // Set reference to an encounter, the condition was part of
        condition.setEncounter(new Reference()
                .setIdentifier(encounter.getIdentifierFirstRep())
                .setReference(encounter.fhirType() + "/" + encounter.getId()));

        // Set date when the condition was first recorded
        condition.setRecordedDateElement(new DateTimeType(date));

        return condition;
    }


    /**
     * The method createComposition() creates a new composition object that contains all the information that is
     * included in a Certificate of Vaccinations
     *
     * @param patient The patient the Certificate of Vaccinations belongs to.
     * @param practitioner The doctor who issued the Certificate of Vaccinations.
     * @param date The composition editing time.
     * @param ArrayListYellowFever List containing all vaccinations against yellow fever.
     * @param ArrayListStandardVaccinations List containing all standard vaccinations.
     * @param ArrayListOtherVaccinations List containing all other vaccinations.
     * @param ArrayListInfluenza List containing all vaccinations against influenza.
     * @param ArrayListTuberculosis List containing all vaccinations against tuberculosis.
     * @param ArrayListTuberculinTests List containing all tuberculin-tests.
     * @param ArrayListAntibodyAssays List containing all antibody assays.
     * @param ArrayListHepatitisB List containing all antibody assays for hepatitis B.
     * @param ArrayListRubella List containing all rubella antibody assays.
     * @param ArrayListPassiveImmunizations List containing all passive immunizations with human immunoglobulins.
     * @param ArrayListSerumInjection List containing all serum injections.
     * @param ArrayListCondition List containing all medical comments on risk factors concerning vaccinations.
     * @param ArrayListBlood List containing an Observation with the blood group.
     * @return a new Composition object
     */
    public static Composition createComposition(Patient patient, Practitioner practitioner, String date,
                                                ArrayList<Immunization> ArrayListYellowFever,
                                                ArrayList<Immunization> ArrayListStandardVaccinations,
                                                ArrayList<Immunization> ArrayListOtherVaccinations,
                                                ArrayList<Immunization> ArrayListInfluenza,
                                                ArrayList<Immunization> ArrayListTuberculosis,
                                                ArrayList<Observation> ArrayListTuberculinTests,
                                                ArrayList<Observation> ArrayListAntibodyAssays,
                                                ArrayList<Observation> ArrayListHepatitisB,
                                                ArrayList<Observation> ArrayListRubella,
                                                ArrayList<Immunization> ArrayListPassiveImmunizations,
                                                ArrayList<Immunization> ArrayListSerumInjection,
                                                ArrayList<Condition> ArrayListCondition,
                                                ArrayList<Observation> ArrayListBlood){
        // Create a new Composition object
        Composition composition = new Composition();

        // Set Identifier of the composition object
        composition.setIdentifier(new Identifier()
                .setSystem("http://www.kh-uzl.de/fhir/composition")
                .setValue(UUID.randomUUID().toString()));

        // Set status of composition
        composition.setStatus(Composition.CompositionStatus.FINAL);

        // Set kind of composition
        composition.setType(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode("11369-6")
                        .setSystem("http://loinc.org")
                        .setDisplay("History of Immunization Narrative")));

        // Set reference to a patient, all the data in the composition belongs to
        composition.setSubject(new Reference()
                .setIdentifier(patient.getIdentifierFirstRep())
                .setReference(patient.fhirType() + "/" + patient.getId()));

        // Set composition editing time
        composition.setDateElement(new DateTimeType(date));

        // Set author of the composition
        composition.addAuthor(new Reference()
                .setIdentifier(practitioner.getIdentifierFirstRep())
                .setReference(practitioner.fhirType() + "/" + practitioner.getId()));

        // Set title of the composition
        composition.setTitle("Internationale Bescheinigungen über Impfungen und Impfbuch");


        // Section for the owner of the certificate of vaccinations
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Impfausweisinhaber")
                .addEntry(new Reference()
                        .setIdentifier(patient.getIdentifierFirstRep())
                        .setReference(patient.fhirType() + "/" + patient.getId())));


        // Section for yellow fever
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Gelbfieber"));

        for(int i = 0; i < ArrayListYellowFever.size(); i++){
            composition.getSection().get(1).addEntry(new Reference()
                    .setReference(ArrayListYellowFever.get(i).fhirType() + "/" + ArrayListYellowFever.get(i).getId()));
        }


        // Section for standard vaccinations
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Standardimpfungen"));

        for(int i = 0; i < ArrayListStandardVaccinations.size(); i++){
            composition.getSection().get(2).addEntry(new Reference()
                    .setReference(ArrayListStandardVaccinations.get(i).fhirType() + "/" + ArrayListStandardVaccinations.get(i).getId()));
        }


        // Section for other vaccinations
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Sonstige Schutzimpfungen"));

        for(int i = 0; i < ArrayListOtherVaccinations.size(); i++){
            composition.getSection().get(3).addEntry(new Reference()
                    .setReference(ArrayListOtherVaccinations.get(i).fhirType() + "/" + ArrayListOtherVaccinations.get(i).getId()));
        }


        // Section for vaccinations against influenza
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Schutzimpfungen gegen Influenza"));

        for(int i = 0; i < ArrayListInfluenza.size(); i++){
            composition.getSection().get(4).addEntry(new Reference()
                    .setReference(ArrayListInfluenza.get(i).fhirType() + "/" + ArrayListInfluenza.get(i).getId()));
        }


        // Section for vaccinations against tuberculosis
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Bescheinigung über Tuberkulose-Schutzimpfungen (BCG)"));

        for(int i = 0; i < ArrayListTuberculosis.size(); i++){
            composition.getSection().get(5).addEntry(new Reference()
                    .setReference(ArrayListTuberculosis.get(i).fhirType() + "/" + ArrayListTuberculosis.get(i).getId()));
        }


        // Section for Tuberculin-tests
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Ergebnis von Tuberkulinproben"));

        for(int i = 0; i < ArrayListTuberculinTests.size(); i++){
            composition.getSection().get(6).addEntry(new Reference()
                    .setReference(ArrayListTuberculinTests.get(i).fhirType() + "/" + ArrayListTuberculinTests.get(i).getId()));
        }


        // Section for antibody assays
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Ergebnisse von Antikörperuntersuchungen"));

        for(int i = 0; i < ArrayListAntibodyAssays.size(); i++){
            composition.getSection().get(7).addEntry(new Reference()
                    .setReference(ArrayListAntibodyAssays.get(i).fhirType() + "/" + ArrayListAntibodyAssays.get(i).getId()));
        }


        // Section for Hepatitis B
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Virushepatitis B"));

        for(int i = 0; i < ArrayListHepatitisB.size(); i++){
            composition.getSection().get(8).addEntry(new Reference()
                    .setReference(ArrayListHepatitisB.get(i).fhirType() + "/" + ArrayListHepatitisB.get(i).getId()));
        }


        // Section for Rubella antibody assays
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Röteln-Antikörper-Bestimmungen"));

        for(int i = 0; i < ArrayListRubella.size(); i++){
            composition.getSection().get(9).addEntry(new Reference()
                    .setReference(ArrayListRubella.get(i).fhirType() + "/" + ArrayListRubella.get(i).getId()));
        }


        // Section for Passive immunizations with human immunoglobulins
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Passive Immunisierungen mit humanen Immunoglobulinen"));

        for(int i = 0; i < ArrayListPassiveImmunizations.size(); i++){
            composition.getSection().get(10).addEntry(new Reference()
                    .setReference(ArrayListPassiveImmunizations.get(i).fhirType() + "/" + ArrayListPassiveImmunizations.get(i).getId()));
        }


        // Section for serum injections
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Serum-Injektion"));

        for(int i = 0; i < ArrayListSerumInjection.size(); i++){
            composition.getSection().get(11).addEntry(new Reference()
                    .setReference(ArrayListSerumInjection.get(i).fhirType() + "/" + ArrayListSerumInjection.get(i).getId()));
        }


        // Section for medical comments on risk factors concerning vaccinations
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Ärztliche Vermerke über medizinische Risikofaktoren bei Impfungen"));

        for(int i = 0; i < ArrayListCondition.size(); i++){
            composition.getSection().get(12).addEntry(new Reference()
                    .setReference(ArrayListCondition.get(i).fhirType() + "/" + ArrayListCondition.get(i).getId()));
        }


        // Section for blood group and rhesus factor
        composition.addSection(new Composition.SectionComponent()
                .setTitle("Blutgruppe und Rh-Faktor"));

        for(int i = 0; i < ArrayListBlood.size(); i++){
            composition.getSection().get(13).addEntry(new Reference()
                    .setReference(ArrayListBlood.get(i).fhirType() + "/" + ArrayListBlood.get(i).getId()));
        }


        return composition;
    }

}
