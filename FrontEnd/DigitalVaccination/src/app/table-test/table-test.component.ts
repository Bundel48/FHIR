import { Component, OnInit } from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { VaccinationService} from '../vaccination.service';
import { HttpClient } from '@angular/common/http';


export interface Immunization {
  Datum: string;
  Handelsname: string;
  Chargennummer: string;
  Wirkstoffe: string;
  Arztinformation:string;
}

const DataSource: Immunization[] = [
                       {Datum: "Peh", Handelsname: 'Hydrogen', Chargennummer: '123', Wirkstoffe: 'H',Arztinformation:'A'},
                     ];

@Component({
  selector: 'app-table-test',
  templateUrl: './table-test.component.html',
  styleUrls: ['./table-test.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class TableTestComponent implements OnInit{
  immunization: any;
  dataSource = this.immunization;
  columnsToDisplay = ['Datum', 'Handelsname', 'Chargennummer', 'Wirkstoffe', 'Arztinformation'];
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
                           "display": "Tetanus"
                         },
                         {
                           "display": "Diphtheria due to Corynebacterium diphtheriae"
                         },
                         {
                           "display": "Pertussis"
                         },
                         {
                           "display": "Haemophilus influenzae type b infection"
                         },
                         {
                           "display": "Acute poliomyelitis"
                         }
                       ]
                     }
                   ]
                 }
             ]
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


    }

}




