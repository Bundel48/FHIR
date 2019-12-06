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


        Patient patient1 = createPatient();

        Bundle bundle = new Bundle();
        bundle.addEntry().setResource(patient1).getRequest().setUrl(patient1.fhirType()).setMethod(Bundle.HTTPVerb.POST);

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


        List<StringType> street = new ArrayList<StringType>();

        patient.addAddress()
                .setUse(Address.AddressUse.HOME)
                .setType(Address.AddressType.BOTH)
                .addLine("An der Obertrave 49")
                .setCity("Lübeck")
                .setPostalCode("23552")
                .setCountry("DE");

        return patient;

    }



    public void addVaccenateRessources(){

    }
}
