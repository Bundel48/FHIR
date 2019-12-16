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
  selector: 'app-other-vac-page',
  templateUrl: './other-vac-page.component.html',
  styleUrls: ['./other-vac-page.component.css']
})
export class OtherVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Gegen','Chargennummer', 'Arztinformation'];
  immunization: Array<ImmunizationData> = [];
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

    async ngOnInit() {
      let compositionData = await this.compositionService.compositionData;
      if(typeof compositionData.others.entry !== 'undefined'){
        for(let i = 0; i < compositionData.others.entry.length; i++){
          this.practitioner = compositionData.others.entry[i].encounter.participant;
          this.organization = compositionData.others.entry[i].encounter.serviceProvider;
          let stoffe: Array<string> = [];


          if(typeof compositionData.others.entry[i].note !== 'undefined'){
            for(let j = 0; j < compositionData.others.entry[i].note.length; j++){
              stoffe[j] = compositionData.others.entry[i].note[j].text;
            }
          }

          this.immunization[i] = {
            code: compositionData.others.entry[i].vaccineCode.coding[0].code,
            display: compositionData.others.entry[i].vaccineCode.coding[0].display,
            date: compositionData.others.entry[i].occurrenceDateTime,
            lotNumber: compositionData.others.entry[i].lotNumber,
            wirkstoffe: stoffe
          };

          compositionData.others.entry[i];
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

