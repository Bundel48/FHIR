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
  selector: 'app-tub-vac-page',
  templateUrl: './tub-vac-page.component.html',
  styleUrls: ['./tub-vac-page.component.css']
})
export class TubVacPageComponent implements OnInit {

  immunization: Array<ImmunizationData> = [];
    columnsToDisplay = ['Datum', 'Impfstoff', 'Chargennummer', 'Arztinformation'];
    practitioner: any;
    organization: any;
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
      if(typeof compositionData.tubvac.entry !== 'undefined'){
        for(let i = 0; i < compositionData.tubvac.entry.length; i++){
          this.practitioner = compositionData.tubvac.entry[i].encounter.participant;
          this.organization = compositionData.tubvac.entry[i].encounter.serviceProvider;
          let stoffe: Array<string> = [];


          if(typeof compositionData.tubvac.entry[i].protocolApplied !== 'undefined'){
            for(let j = 0; j < compositionData.tubvac.entry[i].protocolApplied[0].targetDisease[0].coding.length; j++){
              stoffe[j] = compositionData.tubvac.entry[i].protocolApplied[0].targetDisease[0].coding[j].display;
            }
          }

          this.immunization[i] = {
            code: compositionData.tubvac.entry[i].vaccineCode.coding[0].code,
            display: compositionData.tubvac.entry[i].vaccineCode.coding[0].display,
            date: compositionData.tubvac.entry[i].occurrenceDateTime,
            lotNumber: compositionData.tubvac.entry[i].lotNumber,
            wirkstoffe: stoffe
          };

          compositionData.tubvac.entry[i];
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
