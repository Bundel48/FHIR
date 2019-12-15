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
@Component({
  selector: 'app-influenza-vac-page',
  templateUrl: './influenza-vac-page.component.html',
  styleUrls: ['./influenza-vac-page.component.css']
})
export class InfluenzaVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Chargennummer', 'Arztinformation'];
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
    if(typeof compositionData.influenz.entry !== 'undefined'){
      for(let i = 0; i < compositionData.influenz.entry.length; i++){
        this.practitioner = compositionData.influenz.entry[i].encounter.participant;
        this.organization = compositionData.influenz.entry[i].encounter.serviceProvider;

        this.immunization[i] = {
          code: compositionData.influenz.entry[i].vaccineCode.coding[0].code,
          display: compositionData.influenz.entry[i].vaccineCode.coding[0].display,
          date: compositionData.influenz.entry[i].occurrenceDateTime,
          lotNumber: compositionData.influenz.entry[i].lotNumber,
        };

        compositionData.influenz.entry[i];
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

