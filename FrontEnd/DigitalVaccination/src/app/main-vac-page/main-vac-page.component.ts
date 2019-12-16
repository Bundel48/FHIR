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
  wirkstoffe: Array <any>;

}

@Component({
  selector: 'app-main-vac-page',
  templateUrl: './main-vac-page.component.html',
  styleUrls: ['./main-vac-page.component.css'],
  animations: [
      trigger('detailExpand', [
        state('collapsed', style({height: '0px', minHeight: '0'})),
        state('expanded', style({height: '*'})),
        transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
      ]),
  ],

})
export class MainVacPageComponent implements OnInit {
  immunization: Array<ImmunizationData> = [];
  practitioner: any;
  organization: any;
  columnsToDisplay = ['Datum', 'Handelsname', 'Chargennummer','Wirkstoffe','Arztinformation'];
  dataSource : VacDataSource = new VacDataSource([]);
  expandedElement: ImmunizationData | null;
  constructor(
    private vaccinationService: VaccinationService,
    private compositionService: CompositionService,
    private httpClient: HttpClient
  )
  {
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
                   "display": ""
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
               ""
             ],
             "city": "",
             "postalCode": "",
             "country": ""
           }
         ],
         "name": "",
         "telecom": [
           {
             "value": ""
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
    if(typeof compositionData.standardimpfung.entry !== 'undefined'){
      for(let i = 0; i < compositionData.standardimpfung.entry.length; i++){
        this.practitioner = compositionData.standardimpfung.entry[i].encounter.participant;
        this.organization = compositionData.standardimpfung.entry[i].encounter.serviceProvider;
        let stoffe: Array<string> = [];


        if(typeof compositionData.standardimpfung.entry[i].protocolApplied !== 'undefined'){
          for(let j = 0; j < compositionData.standardimpfung.entry[i].protocolApplied[0].targetDisease[0].coding.length; j++){
            stoffe[j] = compositionData.standardimpfung.entry[i].protocolApplied[0].targetDisease[0].coding[j].display;
          }
        }

        this.immunization[i] = {
          code: compositionData.standardimpfung.entry[i].vaccineCode.coding[0].code,
          display: compositionData.standardimpfung.entry[i].vaccineCode.coding[0].display,
          date: compositionData.standardimpfung.entry[i].occurrenceDateTime,
          lotNumber: compositionData.standardimpfung.entry[i].lotNumber,
          wirkstoffe: stoffe
        };

        compositionData.standardimpfung.entry[i];
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
