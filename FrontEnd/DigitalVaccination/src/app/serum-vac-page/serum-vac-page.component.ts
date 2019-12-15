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
  typ: any;

}
@Component({
  selector: 'app-serum-vac-page',
  templateUrl: './serum-vac-page.component.html',
  styleUrls: ['./serum-vac-page.component.css']
})
export class SerumVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum','Typ', 'Chargennummer', 'Dosis', 'Arztinformation'];
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

    for(let i = 0; i < compositionData.serum.entry.length; i++){
      this.practitioner = compositionData.serum.entry[i].encounter.participant;
      this.organization = compositionData.serum.entry[i].encounter.serviceProvider;

      this.immunization[i] = {
        date: compositionData.serum.entry[i].occurrenceDateTime,
        lotNumber: compositionData.serum.entry[i].lotNumber,
        unit:compositionData.serum.entry[i].doseQuantity.unit,
        dose:compositionData.serum.entry[i].doseQuantity.value,
        typ:compositionData.serum.entry[i].note[0].text
      };

      compositionData.serum.entry[i];
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
