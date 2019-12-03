package fhirClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Bundle.BundleEntryComponent;
import org.hl7.fhir.r4.model.Bundle.HTTPVerb;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;


/**
 * @author Finja Ottink, Leonie Basso
 *
 */

public class MedDatInt {
	

	public static void main(String[] args) {

		// -------------------------2.1---------------------------------------------

		
		// Create a patient object
		Patient patient = new Patient();
		
		// Set Identifier of the patient object
	    patient.addIdentifier()
	    	.setSystem("http://www.kh-uzl.de/fhir/patients")
	    	.setValue(UUID.randomUUID().toString());
	    
	    // Set official name of the patient object
	    patient.addName()
        	.setUse(HumanName.NameUse.OFFICIAL)
        	.setFamily("Grünlich")
        	.addGiven("Antonie");
	    
	    // Set maiden name of the patient object
	    patient.addName()
    		.setUse(HumanName.NameUse.MAIDEN)
    		.setFamily("Bruddenbrooks)")
    		.addGiven("Antonie");
	    
	    // Set birth date of the patient object
	    patient.setBirthDateElement(new DateType("1827-08-06"));
	    
	    // Set marital status of the patient object
	    patient.setMaritalStatus(new CodeableConcept()
	    		.addCoding(new Coding()
                .setCode("M")
                .setSystem("http://terminology.hl7.org/CodeSystem/v3-MaritalStatus")
                .setDisplay("Married"))
	    		);
		

		// Create a context for R4
		FhirContext ctxR4 = FhirContext.forR4();

		// Parser to encode the resource into a string.
		String encoded = ctxR4.newXmlParser().setPrettyPrint(true).encodeResourceToString(patient);
		System.out.println(encoded + "\n");

		// Write XML file
		try {
			// Writing to a file
			File file = new File(
					"C:\\Users\\finja\\Documents\\eclipse\\Java Workspace\\fhirexercise\\src\\main\\java\\fhirClient\\XMLFile.xml");
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			System.out.println("\n Writing XML object to file");
			System.out.println("----------------------- \n");

			fileWriter.write(encoded);
			fileWriter.flush();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// JSON parsing/encoding of the resource.
		IParser jsonParser = ctxR4.newJsonParser();
		jsonParser.setPrettyPrint(true);
		encoded = jsonParser.encodeResourceToString(patient);
		System.out.println(encoded);

		// Write Json file
		try {
			// Writing to a file
			File file = new File(
					"C:\\Users\\finja\\Documents\\eclipse\\Java Workspace\\fhirexercise\\src\\main\\java\\fhirClient\\JsonFileAntonie.json");
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			System.out.println("\n Writing JSON object to file");
			System.out.println("-----------------------");

			fileWriter.write(encoded);
			fileWriter.flush();
			fileWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		// -------------------------2.2---------------------------------------------
		// Connect to a R4 compliant server
		// FhirContext ctxR4 = FhirContext.forR4();
		String serverBase = "https://funke.imi.uni-luebeck.de/public/fhir";

		IGenericClient client = ctxR4.newRestfulGenericClient(serverBase);

		// Perform a search
		Bundle results = client.search().forResource(Patient.class).count(10000)
				.returnBundle(org.hl7.fhir.r4.model.Bundle.class).execute();

		System.out.println("Found " + results.getEntry().size());

		Bundle currentresults = results;
		Bundle nextPage = new Bundle();
		while (currentresults.getLink(Bundle.LINK_NEXT) != null) {
			nextPage = client.loadPage().next(currentresults).execute();
			currentresults = nextPage;
			System.out.println("Found " + nextPage.getEntry().size());
		}

		for (BundleEntryComponent bundleEntryComponent : results.getEntry()) {
			Patient patient2 = (Patient) bundleEntryComponent.getResource();
			if (patient2.getNameFirstRep() != null)
				System.out.print(patient2.getNameFirstRep().getGivenAsSingleString() + " "
						+ patient2.getNameFirstRep().getFamily() + "\n");
			else
				System.out.print("Patient hat keinen Namen :( \n");

		}
		
		for (BundleEntryComponent bundleEntryComponent : nextPage.getEntry()) {
			Patient patient2 = (Patient) bundleEntryComponent.getResource();
			if (patient2.getNameFirstRep() != null)
				System.out.print(patient2.getNameFirstRep().getGivenAsSingleString() + " "
						+ patient2.getNameFirstRep().getFamily() + "\n");
			else
				System.out.print("Patient hat keinen Namen :( \n");

		}

		// -------------------------2.3---------------------------------------------
		// Krankenhaus
		Organization organizationMIO = new Organization();

		organizationMIO.addIdentifier().setSystem("http://www.kh-uzl.de/fhir/organization")
				.setValue(UUID.randomUUID().toString());

		organizationMIO.setName("MIO Krankenhaus");

		organizationMIO.addAddress().setCity("Lübeck").setCountry("Deutschland").setPostalCode("23562");

		
		organizationMIO.setId("146380");

		// Geburtenstation
		Organization orgGeburtenstation = new Organization();

		orgGeburtenstation.addIdentifier().setSystem("http://www.kh-uzl.de/fhir/organization")
				.setValue(UUID.randomUUID().toString());

		orgGeburtenstation.setName("Geburtsstation");

		orgGeburtenstation.setPartOf(new Reference().setIdentifier(organizationMIO.getIdentifierFirstRep())
				.setReference(organizationMIO.fhirType() + "/" + organizationMIO.getId()));
		orgGeburtenstation.setId("146381");

		// -------------------------2.4---------------------------------------------
		
		// Arzt
	    Practitioner arzt = new Practitioner();

	    arzt.addIdentifier()
	            .setSystem("http://www.kh-uzl.de/fhir/practitioner")
	            .setValue(UUID.randomUUID().toString());

	    arzt.addName()
	            .setUse(HumanName.NameUse.OFFICIAL)
	            .setFamily("WHO")
	            .addPrefix("Dr");

	    arzt.addQualification().setCode(new CodeableConcept()
	            .addCoding(new org.hl7.fhir.r4.model.Coding()
	                    .setCode("MD")
	                    .setSystem("http://hl7.org/fhir/v2/0360/2.7")
	                    .setDisplay("Doctor of Medicine")
	            )      
	    );
	    
	    arzt.setId("146411");

	    // Krankenschwester
	    Practitioner schwester = new Practitioner();

	    schwester.addIdentifier()
	            .setSystem("http://www.kh-uzl.de/fhir/practitioner")
	            .setValue(UUID.randomUUID().toString());

	    schwester.addName()
	            .setUse(org.hl7.fhir.r4.model.HumanName.NameUse.OFFICIAL)
	            .setFamily("Tyler")
	            .addGiven("Rose");

	    schwester.addQualification().setCode(new CodeableConcept()
	            .addCoding(new Coding()
	                    .setCode("CRN")
	                    .setSystem("http://hl7.org/fhir/v2/0360/2.7")
	                    .setDisplay("Certified Registered Nurse")
	            )
	    );
	    schwester.setId("146412");

	    // Rollenzuweisung
	    PractitionerRole arztRole =
	            new PractitionerRole()
	                    .setOrganization(new Reference()
	                            .setIdentifier(orgGeburtenstation.getIdentifier().get(0))
	                            .setReference(orgGeburtenstation.fhirType() + "/" + orgGeburtenstation.getId())
	                    )
	                    .setPractitioner(new Reference()
	                            .setIdentifier(arzt.getIdentifier().get(0))
	                            .setReference(arzt.fhirType() + "/" + arzt.getId())
	                    );
	    
	    arztRole.setId("146421");

	    PractitionerRole schwesterRole = new PractitionerRole()
	            .setOrganization(new Reference()
	                    .setIdentifier(orgGeburtenstation.getIdentifier().get(0))
	                    .setReference(orgGeburtenstation.fhirType() + "/" + orgGeburtenstation.getId())
	            )
	            .setPractitioner(new Reference()
	                    .setIdentifier(schwester.getIdentifier().get(0))
	                    .setReference(schwester.fhirType() + "/" + schwester.getId())
	            );
	    
	    schwesterRole.setId("146422");


		// -------------------------2.5---------------------------------------------
	    // Patient Tony
	    Patient patientTony = new Patient();

	    patientTony.addIdentifier()
	            .setSystem("http://www.kh-uzl.de/fhir/patients")
	            .setValue(UUID.randomUUID().toString());
	    patientTony.addName()
	            .setUse(org.hl7.fhir.r4.model.HumanName.NameUse.OFFICIAL)
	            .setFamily("Fhirabend")
	            .addGiven("Toni");
	    patientTony.setId("146423");

	    // Geplanter Krankenhausaufenthalt zur Geburt
	    ServiceRequest serviceRequest = new ServiceRequest();

	    serviceRequest.setSubject(new Reference()
	            .setIdentifier(patientTony.getIdentifierFirstRep())
	            .setReference(patientTony.fhirType()+"/" + patientTony.getId())
	    );

	    serviceRequest.addPerformer()
	            .setIdentifier(orgGeburtenstation.getIdentifierFirstRep())
	            .setReference(orgGeburtenstation.fhirType()+"/" + orgGeburtenstation.getId());
	    serviceRequest.setOccurrence(new DateTimeType("1846-10-01"));
	           
	    serviceRequest.addReasonCode(new CodeableConcept()
	            .addCoding(new Coding()
	                    .setCode("3950001")
	                    .setSystem("http://snomed.info/sct")
	                    .setDisplay("Birth")
	            )
	    );
	    
	    serviceRequest.setId("146424");

		// -------------------------2.6---------------------------------------------
		
		Bundle bundle = new Bundle();
		bundle.addEntry().setResource(organizationMIO).getRequest().setUrl(organizationMIO.fhirType()).setMethod(HTTPVerb.POST);
		bundle.addEntry().setResource(orgGeburtenstation).getRequest().setUrl(orgGeburtenstation.fhirType()).setMethod(HTTPVerb.POST);
		bundle.addEntry().setResource(arzt).getRequest().setUrl(arzt.fhirType()).setMethod(HTTPVerb.POST);
		bundle.addEntry().setResource(schwester).getRequest().setUrl(schwester.fhirType()).setMethod(HTTPVerb.POST);
		bundle.addEntry().setResource(arztRole).getRequest().setUrl(arztRole.fhirType()).setMethod(HTTPVerb.POST);
		bundle.addEntry().setResource(schwesterRole).getRequest().setUrl(schwesterRole.fhirType()).setMethod(HTTPVerb.POST);
		bundle.addEntry().setResource(patientTony).getRequest().setUrl(patientTony.fhirType()).setMethod(HTTPVerb.POST);
		bundle.addEntry().setResource(serviceRequest).getRequest().setUrl(serviceRequest.fhirType()).setMethod(HTTPVerb.POST);
		
		encoded = ctxR4.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle);
		System.out.println(encoded);

		/*
		FhirContext ctxR4 = FhirContext.forR4();
		String encoded = ctxR4.newJsonParser().setPrettyPrint(true).encodeResourceToString(bundle);
		System.out.println(encoded);
		
		
		String serverBase = "https://funke.imi.uni-luebeck.de/public/fhir";
		IGenericClient client = ctxR4.newRestfulGenericClient(serverBase);
		*/
		
		// for pushing upon server !
		//Bundle resp = client.transaction().withBundle(bundle).execute();

		// Log the response
		//System.out.println(ctxR4.newJsonParser().setPrettyPrint(true).encodeResourceToString(resp));

	}	

}
