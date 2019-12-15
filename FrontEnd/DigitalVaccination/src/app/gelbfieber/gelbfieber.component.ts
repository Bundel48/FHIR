import { Component, OnInit } from '@angular/core';
import { VaccinationService} from '../vaccination.service';
import { CompositionService} from '../composition.service';
import { HttpClient } from '@angular/common/http';
import {DataSource} from '@angular/cdk/collections';
import {BehaviorSubject, Observable} from 'rxjs';
import {animate, state, style, transition, trigger} from '@angular/animations';



export interface ImmunizationData{
  code: any;
  display: any;
  date: any;
  lotNumber: any;

}


export interface ImmunizationData{
  code: any;
  display: any;
  date: any;
  lotNumber: any;
  wirkstoffe: Array <any>;

}

@Component({
  selector: 'app-gelbfieber',
  templateUrl: './gelbfieber.component.html',
  styleUrls: ['./gelbfieber.component.css']
})
export class GelbfieberComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Unterschrift', 'Chargennummer', 'Impfcenter'];
  immunization: Array<ImmunizationData> = [];
  practitioner: any;
  patient: any;
  organization: any;
  dataSource : VacDataSource = new VacDataSource([]);
  expandedElement: ImmunizationData | null;
  constructor(
    private vaccinationService: VaccinationService,
    private compositionService: CompositionService,
    private httpClient: HttpClient
  )
  {
    this.patient = {
          "gender": "___",
          "birthDate": "_____________",
        }


    this.practitioner={
      "name": [
           {
             "family": "",
             "given": [
               ""
             ],
             "prefix": [
               ""
             ]
           }
         ],
         "qualification": [
           {
             "code": {
               "coding": [
                 {
                   "display": " "
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

  /*
    code: string;
    display: string;
    date: string;
    lotNumber: string;
    wirkstoffe: Array <string>;
  */
    async ngOnInit() {
      let compositionData = await this.compositionService.compositionData;
      this.patient = compositionData.subject;
      if(typeof compositionData.yellowfever.entry !== 'undefined'){
        for(let i = 0; i < compositionData.yellowfever.entry.length; i++){
          this.practitioner = compositionData.yellowfever.entry[i].encounter.participant;
          this.organization = compositionData.yellowfever.entry[i].encounter.serviceProvider;
          let stoffe: Array<string> = [];


          if(typeof compositionData.yellowfever.entry[i].protocolApplied !== 'undefined'){
            for(let j = 0; j < compositionData.yellowfever.entry[i].protocolApplied[0].targetDisease[0].coding.length; j++){
              stoffe[j] = compositionData.yellowfever.entry[i].protocolApplied[0].targetDisease[0].coding[j].display;
            }
          }

          this.immunization[i] = {
            code: compositionData.yellowfever.entry[i].vaccineCode.coding[0].code,
            display: compositionData.yellowfever.entry[i].vaccineCode.coding[0].display,
            date: compositionData.yellowfever.entry[i].occurrenceDateTime,
            lotNumber: compositionData.yellowfever.entry[i].lotNumber,
            wirkstoffe: stoffe
          };

          compositionData.yellowfever.entry[i];
        }
       }
      this.dataSource = new VacDataSource(this.immunization);
      }
  }


  export class VacDataSource extends DataSource<ImmunizationData> {
    /** Stream of data that is provided to the table. */
    data : BehaviorSubject<Array<ImmunizationData>>;
    constructor(lines: Array<ImmunizationData>) {
      super();
      this.data = new BehaviorSubject<Array<ImmunizationData>>(lines);
    }

    /** Connect function called by the table to retrieve one stream containing the data to render. */
    connect(): Observable<ImmunizationData[]> {
      return this.data;
    }

    disconnect() {}
  }
