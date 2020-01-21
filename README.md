# Digital vaccination document with HL7 FHIR
In our project of module
*Medizinische Datenintegration - eHealth – CS4361 WiSe 19/20*
it was our task to implement and present a representation of the
vaccination card with HL7 FHIR.
Our solution is divided into two main parts.
The creation of the instance in FHIR and the digital representation of
the document.

## General
We decided to create a **maven project** so that we could easily load
the FHIR resources.
To create the instance we use HAPI-FHIR
and for representation we use Angular.
More information about the submodules can be found in the readme-documents
of the respective folder.

## Creating the instance
The code for creating the instance of the document can be found in
IntellijProject/MIOProjektImpfausweis.
For further information have a look at the respective readme-document.
To generate the instance you have to use 
**Java version 1.11** and
**hapi-fhir version 4.1.0 (r4 structures of HL7 FHIR)**

## Representation of the document
For the representation we decided to use a web application using
the Angular framework.
To realize this we build an **angular 8** application.
For further information please refer to the readme of the corresponding
folder. You can find the implementation in the folder
**FrontEnd.DigitalVaccination**.