import { Component, OnInit } from '@angular/core';
import { VaccinationService} from '../vaccination.service';
import { CompositionService} from '../composition.service';
import { HttpClient } from '@angular/common/http';
import {DataSource} from '@angular/cdk/collections';
import {BehaviorSubject, Observable} from 'rxjs';
import {animate, state, style, transition, trigger} from '@angular/animations';

export interface ObservationData{
  date:any;
  code: any;
  display: any;
  reaction:any
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
  selector: 'app-antibody-vac-page',
  templateUrl: './antibody-vac-page.component.html',
  styleUrls: ['./antibody-vac-page.component.css']
})
export class AntibodyVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Test', 'Ergebnis', 'Arztinformation'];
  observation: Array<ObservationData> = [];
  practitioner: any;
  organization: any;
  dataSource:  ObsDataSource = new ObsDataSource([]);
  constructor(
   private vaccinationService: VaccinationService,
   private compositionService: CompositionService,
   private httpClient: HttpClient
  ) {}

 async ngOnInit() {
  let compositionData = await this.compositionService.compositionData;
  if(typeof compositionData.antibody.entry !== 'undefined'){
    for(let i = 0; i < compositionData.antibody.entry.length; i++){
      this.observation[i] = {
        date: compositionData.antibody.entry[i].effectiveDateTime,
        display: compositionData.antibody.entry[i].code.coding[0].display,
        reaction: compositionData.antibody.entry[i].interpretation[0].coding[0].display,
        code: compositionData.antibody.entry[i].category[0].coding[0].display,
        pnameprefix: compositionData.antibody.entry[i].encounter.participant.name[0].prefix[0],
        pnamegiven: compositionData.antibody.entry[i].encounter.participant.name[0].given[0],
        pnamefamily: compositionData.antibody.entry[i].encounter.participant.name[0].family,
        oname: compositionData.antibody.entry[i].encounter.serviceProvider.name,
        oplz: compositionData.antibody.entry[i].encounter.serviceProvider.address[0].postalCode,
        ocity: compositionData.antibody.entry[i].encounter.serviceProvider.address[0].city,
        ocountry: compositionData.antibody.entry[i].encounter.serviceProvider.address[0].country,
        ostreet: compositionData.antibody.entry[i].encounter.serviceProvider.address[0].line[0],
        otel:  compositionData.antibody.entry[i].encounter.serviceProvider.telecom[0].value
      };

          }
        }
   this.dataSource = new ObsDataSource(this.observation);
   }
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
