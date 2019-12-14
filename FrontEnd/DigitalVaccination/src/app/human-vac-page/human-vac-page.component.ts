import { Component, OnInit } from '@angular/core';
import { VaccinationService} from '../vaccination.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-human-vac-page',
  templateUrl: './human-vac-page.component.html',
  styleUrls: ['./human-vac-page.component.css']
})
export class HumanVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Chargennummer', 'Dosis', 'Arztinformation'];
immunization: any;
   practitioner: any;
   organization: any;
   constructor(
     private vaccinationService: VaccinationService,
     private httpClient: HttpClient
   )
   {
     this.immunization={
       "vaccineCode": {
         "coding": [
           {
             "code": "IFIP",
             "display": "Infanrix-IPV"
           }
         ]
       },
      "occurrenceDateTime": "1997-08-23",
      "lotNumber": "A20CB210A",
     }
     this.practitioner={
       "name": [
            {
              "family": "Wolf",
              "given": [
                "Lucas"
              ],
              "prefix": [
                "Prof. Dr. med."
              ]
            }
          ],
          "qualification": [
            {
              "code": {
                "coding": [
                  {
                    "display": "Doctor of Medicine"
                  }
                ]
              }
            }
          ]
     }

     this.organization={
          "address": [
            {
              "line": [
                "Straße mit Nr"
              ],
              "city": "Stadt",
              "postalCode": "PLZ",
              "country": "LAND"
            }
          ],
          "name": "Medizinisches Gesundheitszentrum",
          "telecom": [
            {
              "value": "+494516748374"
            }
          ],
     }



   }


   ngOnInit() {
       this.vaccinationService.getVaccination();
       //TODO: Im Ueberelement schauen und fuer jede Immunization eigene Get schicken
       this.httpClient.get('http://funke.imi.uni-luebeck.de/public/fhir/Immunization/146813').subscribe(
         data => {
           this.immunization= data;
         }, error => {
           console.log('error: ', error);
         }
       );

       this.httpClient.get('http://funke.imi.uni-luebeck.de/public/fhir/Practitioner/146791').subscribe(
               data => {
                 this.practitioner= data;
               }, error => {
                 console.log('error: ', error);
               }
             );

      this.httpClient.get('http://funke.imi.uni-luebeck.de/public/fhir/Practitioner/146791').subscribe(
                    data => {
                      this.organization= data;
                    }, error => {
                      console.log('error: ', error);
                    }
                  );
     }

 }

