package IntellijProject.MIOProjektImpfausweis.src.main.java;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import org.hl7.fhir.r4.model.*;

import java.lang.reflect.Type;
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
        String serverBase = "https://funke.imi.uni-luebeck.de/public/fhir";
        IGenericClient client = ctxR4.newRestfulGenericClient(serverBase);



        // ------------------------------Create Patient----------------------------------------------------------------
        Patient patient = createPatient("Reid", "Erik", "MALE", "1997-05-17",
                "An der Obertrave 49", "Lübeck", "23552", "DE", "Lübeck",
                "DE");
        patient.setId("146790");


        // ------------------------------Create Organizations----------------------------------------------------------
        Organization organizationHospital = createOrganization(true,
                "Universitätsklinikum Schleswig-Holstein Campus Lübeck", "045150045678",
                "Ratzeburger Allee 160","Lübeck" , "23538",
                "DE");
        organizationHospital.setId("146795");

        Organization organizationInstitute = createOrganization(true,
                "Institut für Transfusionsmedizin", "+4945150098765","Ratzeburger Allee 160, Haus 31",
                "Lübeck", "23538", "DE");
        organizationInstitute.setPartOf(new Reference()
                .setIdentifier(organizationHospital.getIdentifierFirstRep())
                .setReference(organizationHospital.fhirType() + "/" + organizationHospital.getId()));
        organizationInstitute.setId("146797");

        Organization organizationMedicalCenter = createOrganization(true,
                "Medizinisches Gesundheitszentrum", "+494516748374","Paul-Ehrlich-Straße 3",
                "Lübeck", "23562", "DE");
        organizationMedicalCenter.setId("146796");


        // ------------------------------Create Practitioners----------------------------------------------------------
        Practitioner practitioner1 = createPractitioner("Lucas", "Wolf", "Prof. Dr. med.",
                "MD", "Doctor of Medicine");
        Practitioner practitioner2 = createPractitioner("Frieda", "May", "Dr. med.",
                "MD", "Doctor of Medicine");
        Practitioner practitioner3 = createPractitioner("Laura", "Adams", "Dr. med.",
                "MD", "Doctor of Medicine");
        Practitioner practitioner4 = createPractitioner("Artur", "Beck", "Dr. med.",
                "MD", "Doctor of Medicine");
        practitioner1.setId("146791");
        practitioner2.setId("146792");
        practitioner3.setId("146793");
        practitioner4.setId("146794");


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
        Encounter encounter10 = createEncounter(patient, practitioner4, organizationInstitute,"2006-08-28");
        Encounter encounter11 = createEncounter(patient, practitioner2, organizationMedicalCenter,"2011-03-13");

        Encounter encounter12 = createEncounter(patient, practitioner4, organizationInstitute,"2014-03-02");
        Encounter encounter13 = createEncounter(patient, practitioner4, organizationInstitute,"2017-08-13");

        Encounter encounter14 = createEncounter(patient, practitioner2, organizationMedicalCenter,"2004-01-15");

        Encounter encounter15 = createEncounter(patient, practitioner4, organizationInstitute,"2016-09-04");

        Encounter encounter16 = createEncounter(patient, practitioner4, organizationInstitute,"2018-04-17");

        Encounter encounter17 = createEncounterImmunology(patient, practitioner4, organizationInstitute,"2015-01-22");

        Encounter encounter18 = createEncounterImmunology(patient, practitioner1, organizationMedicalCenter,"2018-11-24");

        encounter1.setId("146798");
        encounter2.setId("146799");
        encounter3.setId("146800");
        encounter4.setId("146801");
        encounter5.setId("146802");
        encounter6.setId("146803");
        encounter7.setId("146804");
        encounter8.setId("146805");
        encounter9.setId("146806");
        encounter10.setId("146807");
        encounter11.setId("146808");
        encounter12.setId("146809");
        encounter13.setId("146810");
        encounter14.setId("146811");
        encounter15.setId("146812");
        encounter16.setId("146828");
        encounter17.setId("146830");
        encounter18.setId("146832");


        // ------------------------------Create Array Lists needed for Series of Vaccine------------------------------
        // Array List for Measles, Mumps, Rubella
        ArrayList<String> targetDiseaseCode1 = new ArrayList<String>();
        targetDiseaseCode1.add("14189004");
        targetDiseaseCode1.add("36989005");
        targetDiseaseCode1.add("36653000");

        ArrayList<String> targetDiseaseDisplay1 = new ArrayList<String>();
        targetDiseaseDisplay1.add("Measles");
        targetDiseaseDisplay1.add("Mumps");
        targetDiseaseDisplay1.add("Rubella");

        // Array List for Tetanus, Diphtheria, Pertussis, HiB, Polio
        ArrayList<String> targetDiseaseCode2 = new ArrayList<String>();
        targetDiseaseCode2.add("76902006");
        targetDiseaseCode2.add("397430003");
        targetDiseaseCode2.add("27836007");
        targetDiseaseCode2.add("709410003");
        targetDiseaseCode2.add("398102009");

        ArrayList<String> targetDiseaseDisplay2 = new ArrayList<String>();
        targetDiseaseDisplay2.add("Tetanus");
        targetDiseaseDisplay2.add("Diphtheria due to Corynebacterium diphtheriae");
        targetDiseaseDisplay2.add("Pertussis");
        targetDiseaseDisplay2.add("Haemophilus influenzae type b infection");
        targetDiseaseDisplay2.add("Acute poliomyelitis");

        // Array List for Tetanus, Diphtheria, Pertussis, Polio
        ArrayList<String> targetDiseaseCode3 = new ArrayList<String>();
        targetDiseaseCode3.add("76902006");
        targetDiseaseCode3.add("397430003");
        targetDiseaseCode3.add("27836007");
        targetDiseaseCode3.add("398102009");

        ArrayList<String> targetDiseaseDisplay3 = new ArrayList<String>();
        targetDiseaseDisplay3.add("Tetanus");
        targetDiseaseDisplay3.add("Diphtheria due to Corynebacterium diphtheriae");
        targetDiseaseDisplay3.add("Pertussis");
        targetDiseaseDisplay3.add("Acute poliomyelitis");

        // Array List for Tetanus, Diphtheria, Pertussis, Polio
        ArrayList<String> targetDiseaseCode4 = new ArrayList<String>();
        targetDiseaseCode4.add("76902006");
        targetDiseaseCode4.add("397430003");
        targetDiseaseCode4.add("27836007");

        ArrayList<String> targetDiseaseDisplay4 = new ArrayList<String>();
        targetDiseaseDisplay4.add("Tetanus");
        targetDiseaseDisplay4.add("Diphtheria due to Corynebacterium diphtheriae");
        targetDiseaseDisplay4.add("Pertussis");


        // ------------------------------Create Immunizations---------------------------------------------------------
        // ------------------------------Immunizations Yellow Fever---------------------------------------------------

        // ------------------------------Standard Immunizations-------------------------------------------------------
        // IFIP for Tetanus, Diphththerie, Pertussis, Hib, Poliomyelitis
        // ENGP for Hepatitis B
        // MMRSKB for Measles, Mumps, Rubella
        // IFX for Tetanus, Diphththerie, Pertussis
        // QDCL for Tetanus, Diphththerie, Pertussis, Poliomyelitis

        Immunization immunization_standard1 = createImmunizationSeries(patient,encounter1,"IFIP",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix-IPV", "1997-08-23",
                "A20CB210A", targetDiseaseCode2, targetDiseaseDisplay2, 1);

        Immunization immunization_standard2 = createImmunizationSeries(patient,encounter2,"IFIP",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix-IPV", "1997-09-20",
                "A20CB347A", targetDiseaseCode2, targetDiseaseDisplay2,2);

        Immunization immunization_standard3 = createImmunizationSeries(patient,encounter3,"IFIP",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix-IPV", "1997-10-27",
                "A20CB549A", targetDiseaseCode2, targetDiseaseDisplay2,3);

        Immunization immunization_standard4 = createImmunizationSeries(patient,encounter4,"MMRSKB",
                "urn:oid:1.2.36.1.2001.1005.17", "Priorix", "1998-09-12",
                "A69CC740A", targetDiseaseCode1, targetDiseaseDisplay1,1);

        Immunization immunization_standard5 = createImmunizationSeries(patient,encounter5,"IFIP",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix-IPV", "1998-12-10",
                "A20CB721A", targetDiseaseCode2, targetDiseaseDisplay2,4);

        Immunization immunization_standard6 = createSingleImmunization(patient, encounter6, "ENGP",
                "urn:oid:1.2.36.1.2001.1005.17", "Engerix B",
                "2000-02-10","ENG3109B9", "Hepatitis B");

        Immunization immunization_standard7 = createSingleImmunization(patient, encounter7, "ENGP",
                "urn:oid:1.2.36.1.2001.1005.17", "Engerix B",
                "2000-03-12","ENG3124B9", "Hepatitis B");

        Immunization immunization_standard8 = createImmunizationSeries(patient,encounter8,"MMRSKB",
                "urn:oid:1.2.36.1.2001.1005.17", "Priorix", "2000-08-06",
                "A69CC740A", targetDiseaseCode1, targetDiseaseDisplay1,2);

        Immunization immunization_standard9 = createSingleImmunization(patient, encounter9, "ENGP",
                "urn:oid:1.2.36.1.2001.1005.17", "Engerix B",
                "2001-04-30","ENG3130B9", "Hepatitis B");

        Immunization immunization_standard10 = createImmunizationSeries(patient,encounter10,"QDCL",
                "urn:oid:1.2.36.1.2001.1005.17", "Quadracel", "2006-08-28",
                "AC39B008BC", targetDiseaseCode3, targetDiseaseDisplay3, 5);

        Immunization immunization_standard11 = createImmunizationSeries(patient,encounter11,"IFX",
                "urn:oid:1.2.36.1.2001.1005.17", "Infanrix", "2011-03-13",
                "A21CB172A", targetDiseaseCode4, targetDiseaseDisplay4, 6);

        immunization_standard1.setId("146813");
        immunization_standard2.setId("146814");
        immunization_standard3.setId("146815");
        immunization_standard4.setId("146816");
        immunization_standard5.setId("146817");
        immunization_standard6.setId("146818");
        immunization_standard7.setId("146819");
        immunization_standard8.setId("146820");
        immunization_standard9.setId("146821");
        immunization_standard10.setId("146822");
        immunization_standard11.setId("146823");

        // ------------------------------Other Immunizations-------------------------------------------------------
        Immunization immunization_others1 = createSingleImmunization(patient, encounter12, "MENTEC",
                "urn:oid:1.2.36.1.2001.1005.17", "Meningitec",
                "2014-03-02","25858", "Meningokokken");

        Immunization immunization_others2 = createSingleImmunization(patient, encounter13, "GNJEN",
                "urn:oid:1.2.36.1.2001.1005.17", "Japanese Encephalitis",
                "2017-08-13","25858", "Japanische Enzephalitis");

        immunization_others1.setId("146824");
        immunization_others2.setId("146825");

        // ------------------------------Immunizations Influenza------------------------------------------------------
        Immunization immunization_influenza1 = createSingleImmunization(patient, encounter14, "INFLUV",
                "urn:oid:1.2.36.1.2001.1005.17", "Influvac",
                "2004-01-15","18858B9", "Influenza");

        immunization_influenza1.setId("146826");

        // ------------------------------Immunizations Tuberculosis---------------------------------------------------

        // ------------------------------Passive Immunizations -------------------------------------------------------
        Immunization immunization_passive = createImmunizationWithDose(patient, encounter15, "18",
                "http://hl7.org/fhir/sid/cvx", "rabies, intramuscular injection",
                "2016-09-04", "44307A", 5.0, "ml", "Tollwut");

        immunization_passive.setId("146827");

        // ------------------------------Serum injection--------------------------------------------------------------



        // ------------------------------Create Observations----------------------------------------------------------
        // ------------------------------Tuberculin-Tests-------------------------------------------------------------
        Observation observation_tuberculin1 = createObservationTuberculinTest(patient, encounter16, practitioner4,
                "39263-9", "Tuberculin screen test status CPHS",
                "2018-04-17", "NEG", "Negative", "Intrakutantest");

        observation_tuberculin1.setId("146829");

        // ------------------------------Antibody Assays--------------------------------------------------------------

        // ------------------------------Hepatitis B antibody assays--------------------------------------------------
        Observation observation_antibody1 = createObservationAntibodyAssays(patient, encounter17, practitioner4,
                "22322-2", "Hepatitis B virus surface Ab [Presence] in Serum",
                "2015-01-22", "N", "Normal");

        observation_antibody1.setId("146831");

        // ------------------------------Rubella antibody assays------------------------------------------------------
        Observation observation_rubella1 = createObservationRubella(patient, encounter18, practitioner1,
                "13279-5", "Rubella virus IgG Ab [Units/volume]",
                "2018-11-24", 12.0, "IU/ml", "ja");

        observation_rubella1.setId("146833");

        // ------------------------------Blood Group-----------------------------------------------------------------



        // ------------------------------Create Conditions------------------------------------------------------------
        // ------------------------------Medical comments on risk factors---------------------------------------------
        Condition condition1 = createCondition(patient, encounter1,  "77465005",
                " Transplantation (procedure)", "1997-08-23");


        // ------------------------------Create Array Lists needed for Composition------------------------------------
        ArrayList<Immunization> ArrayListYellowFever = new ArrayList<Immunization>();

        ArrayList<Immunization> ArrayListStandardVaccinations = new ArrayList<Immunization>();
        ArrayListStandardVaccinations.add(immunization_standard1);
        ArrayListStandardVaccinations.add(immunization_standard2);
        ArrayListStandardVaccinations.add(immunization_standard3);
        ArrayListStandardVaccinations.add(immunization_standard4);
        ArrayListStandardVaccinations.add(immunization_standard5);
        ArrayListStandardVaccinations.add(immunization_standard6);
        ArrayListStandardVaccinations.add(immunization_standard7);
        ArrayListStandardVaccinations.add(immunization_standard8);
        ArrayListStandardVaccinations.add(immunization_standard9);
        ArrayListStandardVaccinations.add(immunization_standard10);
        ArrayListStandardVaccinations.add(immunization_standard11);

        ArrayList<Immunization> ArrayListOtherVaccinations = new ArrayList<Immunization>();
        ArrayListOtherVaccinations.add(immunization_others1);
        ArrayListOtherVaccinations.add(immunization_others2);

        ArrayList<Immunization> ArrayListInfluenza = new ArrayList<Immunization>();
        ArrayListInfluenza.add(immunization_influenza1);

        ArrayList<Immunization> ArrayListTuberculosis = new ArrayList<Immunization>();

        ArrayList<Observation> ArrayListTuberculinTests = new ArrayList<Observation>();
        ArrayListTuberculinTests.add(observation_tuberculin1);

        ArrayList<Observation> ArrayListAntibodyAssays = new ArrayList<Observation>();

        ArrayList<Observation> ArrayListHepatitisB = new ArrayList<Observation>();
        ArrayListHepatitisB.add(observation_antibody1);

        ArrayList<Observation> ArrayListRubella = new ArrayList<Observation>();
        ArrayListRubella.add(observation_rubella1);

        ArrayList<Immunization> ArrayListPassiveImmunizations = new ArrayList<Immunization>();
        ArrayListPassiveImmunizations.add(immunization_passive);

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
        //bundle.addEntry().setResource(patient).getRequest().setUrl(patient.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(organizationHospital).getRequest().setUrl(organizationHospital.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(organizationInstitute).getRequest().setUrl(organizationInstitute.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(organizationMedicalCenter).getRequest().setUrl(organizationMedicalCenter.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(practitioner1).getRequest().setUrl(practitioner1.fhirType()).setMethod(Bundle.HTTPVerb.PUT);
        //bundle.addEntry().setResource(practitioner2).getRequest().setUrl(practitioner2.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(practitioner3).getRequest().setUrl(practitioner3.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(practitioner4).getRequest().setUrl(practitioner4.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter1).getRequest().setUrl(encounter1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter2).getRequest().setUrl(encounter2.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter3).getRequest().setUrl(encounter3.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter4).getRequest().setUrl(encounter4.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter5).getRequest().setUrl(encounter5.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter6).getRequest().setUrl(encounter6.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter7).getRequest().setUrl(encounter7.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter8).getRequest().setUrl(encounter8.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter9).getRequest().setUrl(encounter9.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter10).getRequest().setUrl(encounter10.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter11).getRequest().setUrl(encounter11.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter12).getRequest().setUrl(encounter12.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter13).getRequest().setUrl(encounter13.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter14).getRequest().setUrl(encounter14.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter15).getRequest().setUrl(encounter15.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter16).getRequest().setUrl(encounter16.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter17).getRequest().setUrl(encounter17.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(encounter18).getRequest().setUrl(encounter18.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard1).getRequest().setUrl(immunization_standard1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard2).getRequest().setUrl(immunization_standard2.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard3).getRequest().setUrl(immunization_standard3.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard4).getRequest().setUrl(immunization_standard4.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard5).getRequest().setUrl(immunization_standard5.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard6).getRequest().setUrl(immunization_standard6.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard7).getRequest().setUrl(immunization_standard7.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard8).getRequest().setUrl(immunization_standard8.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard9).getRequest().setUrl(immunization_standard9.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard10).getRequest().setUrl(immunization_standard10.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_standard11).getRequest().setUrl(immunization_standard11.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_others1).getRequest().setUrl(immunization_others1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_others2).getRequest().setUrl(immunization_others2.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_influenza1).getRequest().setUrl(immunization_influenza1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(immunization_passive).getRequest().setUrl(immunization_passive.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(observation_tuberculin1).getRequest().setUrl(observation_tuberculin1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(observation_antibody1).getRequest().setUrl(observation_antibody1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        //bundle.addEntry().setResource(observation_rubella1).getRequest().setUrl(observation_rubella1.fhirType()).setMethod(Bundle.HTTPVerb.POST);


        /*
        bundle.addEntry().setResource(observation1).getRequest().setUrl(observation1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(condition1).getRequest().setUrl(condition1.fhirType()).setMethod(Bundle.HTTPVerb.POST);
        bundle.addEntry().setResource(composition).getRequest().setUrl(composition.fhirType()).setMethod(Bundle.HTTPVerb.POST);
         */


        // example how to update something
        //bundle.addEntry().setResource(practitioner1).getRequest().setUrl("Practitioner/146792/_history/1").setMethod(Bundle.HTTPVerb.PUT);


        // Parser to encode the resource into a string in json format
        String encoded = ctxR4.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle);
        //System.out.println(encoded);



        // for pushing upon server !
        Bundle resp = client.transaction().withBundle(bundle).execute();

        // Log the response
        System.out.println(ctxR4.newJsonParser().setPrettyPrint(true).encodeResourceToString(resp));

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
     * @param phoneNumber The phone number of the organization as String.
     * @param organizationLine The street of the organisation as String.
     * @param organizationCity The city where the organization is located as String.
     * @param organizationPostalCode The postalCode of the organization as String.
     * @param organizationCountry The country code where the organization is located as Sting, for example DE for Germany.
     * @return  a new Organization object
     */
    public static Organization createOrganization(Boolean active, String organizationName, String phoneNumber,
                                                  String organizationLine, String organizationCity,
                                                  String organizationPostalCode, String organizationCountry){
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

        organization.addTelecom(new ContactPoint()
                .setSystem(ContactPoint.ContactPointSystem.PHONE)
                .setValue(phoneNumber));

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

        // Set qualification of the practitioner object
        practitioner.addQualification().setCode(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode(qualificationCode)
                        .setSystem("http://terminology.hl7.org/CodeSystem/v2-0360/2.7")
                        .setDisplay(qualificationDisplay)));

        return practitioner;
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
     * The method createEncounterImmunology() creates a new Encounter object. This describes the date of the encounter
     * and the participating patient and practitioner and the organization that is responsible for the encounter.
     * The encounter is referenced by one observation.
     *
     * @param patient The patient object that was present at the encounter and got the immunization.
     * @param practitioner The practitioner object that who was involved in the encounter.
     * @param organization The organization object that is responsible for the encounter, for example a hospital.
     * @param date The date when the encounter took place as String.
     * @return returns an Encounter object.
     */
    public static Encounter createEncounterImmunology(Patient patient, Practitioner practitioner,
                                                      Organization organization, String date){
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
                        .setCode("172")
                        .setSystem("http://terminology.hl7.org/CodeSystem/service-type")
                        .setDisplay("Immunology & Allergy")));

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
     * The method createSingleImmunization() creates a new immunization. That immunization references a patient, who has been
     * immunized and an encounter that belongs to this immunization.
     * This method can only be used to create immunizations for Yellow Fever, other vaccinations (like
     * cholera, FSME, Hepatitis A, Meningokokken, Pneumokokken, Typhus, Varizellen), Vaccinations against Influenza and
     * Vaccinations against tuberculosis (BCG). For other immunizations another method has to be used.
     *
     * @param patient The patient object that has been immunized.
     * @param encounter The encounter object for this immunization.
     * @param immunizationCode The code of the vaccine product.
     * @param immunizationSystem The system used can be found here http://hl7.org/fhir/valueset-vaccine-code.html
     * @param immunizationDisplay The display infromation that belongs to the immunizationCode.
     * @param date The date when the immunization took place.
     * @param lotNumber The lot number of the vaccine product.
     * @param noteDisease String for the disease the vaccination is against.
     * @return a new Immunization object
     */
    public static Immunization createSingleImmunization(Patient patient, Encounter encounter, String immunizationCode,
                                                  String immunizationSystem, String immunizationDisplay, String date,
                                                  String lotNumber, String noteDisease) {
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

        //Set lotNumber
        immunization.setLotNumber(lotNumber);

        immunization.addNote(new Annotation().setText(noteDisease));

        return immunization;
    }


    /**
     * The method createImmunizationSeries() creates a new immunization. That immunization references a patient, who has been
     * immunized and an encounter that belongs to this immunization.
     * This method can only be used to create immunizations for immunization series like standard vaccinations likely
     * are (like tetanus, diphtheria, pertussis, Hib, Hepatitis B, Poliomyelitis, Measles, Mumps, Rubella)
     *
     * @param patient The patient object that has been immunized.
     * @param encounter The encounter object for this immunization.
     * @param immunizationCode The code of the vaccine product.
     * @param immunizationSystem The system used can be found here http://hl7.org/fhir/valueset-vaccine-code.html
     * @param immunizationDisplay The display infromation that belongs to the immunizationCode.
     * @param date The date when the immunization took place.
     * @param lotNumber The lot number of the vaccine product.
     * @param targetDiseaseCode All target disease codes that are covered with the vaccine product.
     * @param targetDiseaseDisplay All target diseases codes display values that are covered with the vaccine product.
     * @return a new Immunization object
     */
    public static Immunization createImmunizationSeries(Patient patient, Encounter encounter, String immunizationCode,
                                                        String immunizationSystem, String immunizationDisplay, String date,
                                                        String lotNumber, ArrayList<String> targetDiseaseCode,
                                                        ArrayList<String> targetDiseaseDisplay, Integer doseNumberPosInt) {
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

        //Set lotNumber
        immunization.setLotNumber(lotNumber);

        // Set target diseases of a vaccination series
        CodeableConcept codeableConcept = new CodeableConcept();

        for(int i = 0; i < targetDiseaseCode.size(); i++){
            codeableConcept.addCoding(new Coding()
                    .setCode(targetDiseaseCode.get(i))
                    .setSystem("http://snomed.info/sct")
                    .setDisplay(targetDiseaseDisplay.get(i)));
        }

        immunization.addProtocolApplied()
                .addTargetDisease(codeableConcept)
                .setDoseNumber(new PositiveIntType(doseNumberPosInt));


        return immunization;
    }



    /**
     * The method createImmunizationWithDose() creates a new immunization. That immunization references a patient, who
     * has been immunized and an encounter that belongs to this immunization.
     * This method can only be used to create immunizations for passive immunisations with human immunoglobulins and for
     * serum injections.
     *
     * @param patient The patient object that has been immunized.
     * @param encounter The encounter object for this immunization.
     * @param immunizationCode The code of the vaccine product.
     * @param immunizationSystem The system used can be found here http://hl7.org/fhir/valueset-vaccine-code.html
     * @param immunizationDisplay The display information that belongs to the immunizationCode.
     * @param date The date when the immunization took place.
     * @param lotNumber The lot number of the vaccine product.
     * @param doseQuantity A double value for the dose quantity of the vaccine that has been given to the patient.
     * @param doseQuantityUnit The unit of the dose quantity.
     * @param noteDisease String for the disease the vaccination is against.
     * @return a new Immunization object
     */
    public static Immunization createImmunizationWithDose(Patient patient, Encounter encounter, String immunizationCode,
                                                          String immunizationSystem, String immunizationDisplay,
                                                          String date, String lotNumber, double doseQuantity,
                                                          String doseQuantityUnit, String noteDisease) {
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

        //Set lotNumber
        immunization.setLotNumber(lotNumber);

        immunization.setDoseQuantity(new Quantity()
                .setValue(doseQuantity)
                .setUnit(doseQuantityUnit));

        immunization.addNote(new Annotation().setText(noteDisease));

        return immunization;
    }



    /**
     * The method createObservationTuberculinTest() creates a new observation. That observation references a patient
     * the observation belongs to and an encounter that belongs to this observation.
     * The Observation can be used for tuberculin-tests.
     *
     * @param patient The patient object that the observation belongs to.
     * @param encounter The encounter this observation was part of.
     * @param practitioner The practitioner who is responsible for the observation.
     * @param observationTypeCode The code of the type of observation (a Loinc Code).
     * @param observationTypeDisplay The displaytext of the type of the observation.
     * @param date The date when the Observation took place.
     * @param interpretationCode Interpretation Code can only have the values NEG or POS.
     * @param interpretationDisplay The display text belonging to the interpretation Code can only be Negative for code
     *                              NEG and Positive for code POS
     * @param kindOfTest A String that has one of the two values: Stempeltest or Intrakutantest
     * @return a new Observation object.
     */
    public static Observation createObservationTuberculinTest(Patient patient, Encounter encounter,
                                                              Practitioner practitioner, String observationTypeCode,
                                                              String observationTypeDisplay, String date,
                                                              String interpretationCode, String interpretationDisplay,
                                                              String kindOfTest){
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
                        .setCode("vital-signs")
                        .setSystem("http://terminology.hl7.org/CodeSystem/observation-category")
                        .setDisplay("Vital Signs")));

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

        // Set performer of the observation
        observation.addPerformer(new Reference()
                .setIdentifier(practitioner.getIdentifierFirstRep())
                .setReference(practitioner.fhirType() + "/" + practitioner.getId()));

        // Add interpretation/results of the observation
        observation.addInterpretation(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode(interpretationCode)
                        .setSystem("http://terminology.hl7.org/CodeSystem/v3-ObservationInterpretation")
                        .setDisplay(interpretationDisplay)));

        // Set text for the kind of test that has been used
        observation.addNote(new Annotation().setText(kindOfTest));

        return observation;
    }



    /**
     * The method createObservationTuberculinTest() creates a new observation. That observation references a patient
     * the observation belongs to and an encounter that belongs to this observation.
     * The Observation can be used for Antibody assays (but not rubella antibody assays).
     *
     * @param patient The patient object that the observation belongs to.
     * @param encounter The encounter this observation was part of.
     * @param practitioner The practitioner who is responsible for the observation.
     * @param observationTypeCode The code of the type of observation (a Loinc Code).
     * @param observationTypeDisplay The displaytext of the type of the observation.
     * @param date The date when the Observation took place.
     * @param interpretationCode Interpretation Code from http://terminology.hl7.org/CodeSystem/v3-ObservationInterpretation
     * @param interpretationDisplay The display text belonging to the interpretation Code
     * @return a new Observation object.
     */
    public static Observation createObservationAntibodyAssays(Patient patient, Encounter encounter,
                                                              Practitioner practitioner, String observationTypeCode,
                                                              String observationTypeDisplay, String date,
                                                              String interpretationCode, String interpretationDisplay){
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

        // Set performer of the observation
        observation.addPerformer(new Reference()
                .setIdentifier(practitioner.getIdentifierFirstRep())
                .setReference(practitioner.fhirType() + "/" + practitioner.getId()));

        // Add interpretation/results of the observation
        observation.addInterpretation(new CodeableConcept()
                .addCoding(new Coding()
                        .setCode(interpretationCode)
                        .setSystem("http://terminology.hl7.org/CodeSystem/v3-ObservationInterpretation")
                        .setDisplay(interpretationDisplay)));

        return observation;
    }



    /**
     * The method createObservationTuberculinTest() creates a new observation. That observation references a patient
     * the observation belongs to and an encounter that belongs to this observation.
     * The Observation can be used for tuberculin-tests.
     *
     * @param patient The patient object that the observation belongs to.
     * @param encounter The encounter this observation was part of.
     * @param practitioner The practitioner who is responsible for the observation.
     * @param observationTypeCode The code of the type of observation (a Loinc Code).
     * @param observationTypeDisplay The displaytext of the type of the observation.
     * @param date The date when the Observation took place.
     * @param value The value as double of the observation.
     * @param valueUnit The unit of the measured value.
     * @param protection A String that has one of the two values: ja, nein
     * @return a new Observation object.
     */
    public static Observation createObservationRubella(Patient patient, Encounter encounter, Practitioner practitioner,
                                                       String observationTypeCode, String observationTypeDisplay,
                                                       String date, double value, String valueUnit, String protection){
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

        // Set performer of the observation
        observation.addPerformer(new Reference()
                .setIdentifier(practitioner.getIdentifierFirstRep())
                .setReference(practitioner.fhirType() + "/" + practitioner.getId()));

        observation.setValue(new Quantity()
                .setValue(value)
                .setUnit(valueUnit));

        // Set text for the kind of test that has been used
        observation.addNote(new Annotation().setText(protection));

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
