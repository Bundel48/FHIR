import { Component, OnInit } from '@angular/core';
import { VaccinationService} from '../vaccination.service';
import { CompositionService} from '../composition.service';
import { HttpClient } from '@angular/common/http';
import {DataSource} from '@angular/cdk/collections';
import {BehaviorSubject, Observable} from 'rxjs';
import {animate, state, style, transition, trigger} from '@angular/animations';



export interface ImmunizationData{
  unit: any;
  date: any;
  lotNumber: any;
  dose: any;

}

@Component({
  selector: 'app-human-vac-page',
  templateUrl: './human-vac-page.component.html',
  styleUrls: ['./human-vac-page.component.css']
})
export class HumanVacPageComponent implements OnInit {
    immunization: Array<ImmunizationData> = [];
    practitioner: any;
    organization: any;
    columnsToDisplay = ['Datum', 'Chargennummer','Dosis','Arztinformation'];
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
    async ngOnInit() {
      let compositionData = await this.compositionService.compositionData;

      for(let i = 0; i < compositionData.human.entry.length; i++){
        this.practitioner = compositionData.human.entry[i].encounter.participant;
        this.organization = compositionData.human.entry[i].encounter.serviceProvider;

        this.immunization[i] = {
          date: compositionData.human.entry[i].occurrenceDateTime,
          lotNumber: compositionData.human.entry[i].lotNumber,
          unit:compositionData.human.entry[i].doseQuantity.unit,
          dose:compositionData.human.entry[i].doseQuantity.value
        };

        compositionData.standardimpfung.entry[i];
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
