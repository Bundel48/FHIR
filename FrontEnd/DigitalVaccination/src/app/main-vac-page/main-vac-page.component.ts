import { Component, OnInit } from '@angular/core';
import { VaccinationService} from '../vaccination.service';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-main-vac-page',
  templateUrl: './main-vac-page.component.html',
  styleUrls: ['./main-vac-page.component.css']
})
export class MainVacPageComponent implements OnInit {
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
     "protocolApplied": [
                 {
                   "targetDisease": [
                     {
                       "coding": [
                         {
                           "code": "76902006",
                           "display": "Tetanus"
                         },
                         {
                           "code": "397430003",
                           "display": "Diphtheria due to Corynebacterium diphtheriae"
                         },
                         {
                           "code": "27836007",
                           "display": "Pertussis"
                         },
                         {
                           "code": "709410003",
                           "display": "Haemophilus influenzae type b infection"
                         },
                         {
                           "code": "398102009",
                           "display": "Acute poliomyelitis"
                         }
                       ]
                     }
                   ]
                 }
             ]
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
