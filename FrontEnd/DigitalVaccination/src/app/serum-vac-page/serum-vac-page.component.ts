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
  condition: any;
  dataSource : VacDataSource = new VacDataSource([]);
  expandedElement: ImmunizationData | null;
  diabetes: boolean = false;
  haem: boolean = false;
  suppress: boolean = false;
  dialysis: boolean = false;
  spasm: boolean = false;
  transplant: boolean = false;
  allergy: boolean = false;
  immu: boolean = false;

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
    this.condition={
      "code": {
        "coding": [
          {
            "code": "46635009",
            "display": "Diabetes mellitus type 1 (disorder)"
          }
        ]
      },
    }



  }
  async ngOnInit() {
  //TODO: RH-Factor
    let compositionData = await this.compositionService.compositionData;
    if(typeof compositionData.risk.entry !== 'undefined'){
      for(let i = 0; i < compositionData.risk.entry.length; i++ ){
        this.condition = compositionData.risk.entry[i];
        if(this.condition.code.coding[0].code === "46635009"){
          this.diabetes = true;
        }else if( this.condition.code.coding[0].code === "70036007"){
          this.haem = true;
        }else if( this.condition.code.coding[0].code === "45352006"){
          this.spasm = true;

        }else if( this.condition.code.coding[0].code === "236562009"){
          this.dialysis = true;

        }else if( this.condition.code.coding[0].code === "213148006"){
          this.transplant = true;
        }else if( this.condition.code.coding[0].code === "234490009"){
          this.suppress = true;
        }else if( this.condition.code.coding[0].code === "609328004"){
          this.allergy = true;
        }else if( this.condition.code.coding[0].code === "275984001"){
          this.immu = true;
        }
      }


    }

    if(typeof compositionData.serum.entry !== 'undefined'){
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
