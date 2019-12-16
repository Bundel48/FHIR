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

export interface ObservationData{
  date:any;
  code: any;
  display: any;
  reaction: any;
  pnameprefix: any;
  pnamegiven: any;
  pnamefamily:any;
  oname: any;
  otel: any;
  ostreet: any;
  oplz:any;
  ocity:any;
  ocountry: any;
 }

@Component({
  selector: 'app-serum-vac-page',
  templateUrl: './serum-vac-page.component.html',
  styleUrls: ['./serum-vac-page.component.css']
})
export class SerumVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum','Typ', 'Chargennummer', 'Dosis', 'Arztinformation'];
  columnsToDisplayRH1 = ['AB0','RH'];
  columnsToDisplayRH2 = ['RH','Antibody'];
  columnsToDisplayRH3 = ['Arztinformation'];

  immunization: Array<ImmunizationData> = [];
  observation: Array<ObservationData> = [];
  practitioner: any;
  organization: any;
  condition: any;
  dataSource : VacDataSource = new VacDataSource([]);
  dataSourceObservation: ObsDataSource = new ObsDataSource([]);
  expandedElement: ImmunizationData | null;ImmunizationData
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

    if(typeof compositionData.rh.entry !== 'undefined'){
          for(let i = 0; i < compositionData.serum.entry.length; i++){
            this.observation[i] = {
              date: compositionData.rh.entry[i].effectiveDateTime,
              display: compositionData.rh.entry[i].code.coding[0].display,
              reaction: compositionData.rh.entry[i].interpretation[0].coding[0].display,
              code: compositionData.rh.entry[i].category[0].coding[0].display,
              pnameprefix: compositionData.rh.entry[i].encounter.participant.name[0].prefix[0],
              pnamegiven: compositionData.rh.entry[i].encounter.participant.name[0].given[0],
              pnamefamily: compositionData.rh.entry[i].encounter.participant.name[0].family,
              oname: compositionData.rh.entry[i].encounter.serviceProvider.name,
              oplz: compositionData.rh.entry[i].encounter.serviceProvider.address[0].postalCode,
              ocity: compositionData.rh.entry[i].encounter.serviceProvider.address[0].city,
              ocountry: compositionData.rh.entry[i].encounter.serviceProvider.address[0].country,
              ostreet: compositionData.rh.entry[i].encounter.serviceProvider.address[0].line[0],
              otel:  compositionData.rh.entry[i].encounter.serviceProvider.telecom[0].value
            };

          }
        }
        this.dataSourceObservation = new ObsDataSource(this.observation);


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

export class ObsDataSource extends DataSource<ObservationData> {
 /** Stream of data that is provided to the table. */
 data : BehaviorSubject<Array<ObservationData>>;
 constructor(lines: Array<ObservationData>) {
   super();
   this.data = new BehaviorSubject<Array<ObservationData>>(lines);
 }

 /** Connect function called by the table to retrieve one stream containing the data to render. */
 connect(): Observable<ObservationData[]> {
   return this.data;
 }

 disconnect() {}
}
