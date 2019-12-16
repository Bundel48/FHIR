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
  node: any;
}
@Component({
  selector: 'app-heb-b-vac-page',
  templateUrl: './heb-b-vac-page.component.html',
  styleUrls: ['./heb-b-vac-page.component.css']
})
export class HebBVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Test', 'Ergebnis', 'Arztinformation'];
  columnsToDisplay2 = ['Datum', 'Test', 'Titer','Schutz', 'Arztinformation'];
  observationHep: Array<ObservationData> = [];
  observationRBody: Array<ObservationData> = [];
  practitioner: any;
  organization: any;
  dataSource:  ObsDataSource = new ObsDataSource([]);
  dataSource2: ObsDataSource = new ObsDataSource([]);
  constructor(
   private vaccinationService: VaccinationService,
   private compositionService: CompositionService,
   private httpClient: HttpClient
  ) {}

 async ngOnInit() {
  let compositionData = await this.compositionService.compositionData;
/*_______________Hepatitis Test___________________________________________*/
  if(typeof compositionData.hep.entry !== 'undefined'){
    for(let i = 0; i < compositionData.hep.entry.length; i++){
      this.observationHep[i] = {
        date: compositionData.hep.entry[i].effectiveDateTime,
        display: compositionData.hep.entry[i].code.coding[0].display,
        reaction: compositionData.hep.entry[i].interpretation[0].coding[0].display,
        code: compositionData.hep.entry[i].category[0].coding[0].display,
        pnameprefix: compositionData.hep.entry[i].encounter.participant.name[0].prefix[0],
        pnamegiven: compositionData.hep.entry[i].encounter.participant.name[0].given[0],
        pnamefamily: compositionData.hep.entry[i].encounter.participant.name[0].family,
        oname: compositionData.hep.entry[i].encounter.serviceProvider.name,
        oplz: compositionData.hep.entry[i].encounter.serviceProvider.address[0].postalCode,
        ocity: compositionData.hep.entry[i].encounter.serviceProvider.address[0].city,
        ocountry: compositionData.hep.entry[i].encounter.serviceProvider.address[0].country,
        ostreet: compositionData.hep.entry[i].encounter.serviceProvider.address[0].line[0],
        otel:  compositionData.hep.entry[i].encounter.serviceProvider.telecom[0].value,
        node: compositionData.hep.entry[i].category[0].coding[0].display
      };

    }
  }
   this.dataSource = new ObsDataSource(this.observationHep);
/*_______________rBody-Test___________________________________________*/

  if(typeof compositionData.rbody.entry !== 'undefined'){
    for(let i = 0; i < compositionData.rbody.entry.length; i++){
      this.observationRBody[i] = {
        date: compositionData.rbody.entry[i].effectiveDateTime,
        display: compositionData.rbody.entry[i].code.coding[0].display,
        reaction: compositionData.rbody.entry[i].valueQuantity.value,
        code: compositionData.rbody.entry[i].valueQuantity.unit,
        node: compositionData.rbody.entry[i].note[0].text,
        pnameprefix: compositionData.rbody.entry[i].encounter.participant.name[0].prefix[0],
        pnamegiven: compositionData.rbody.entry[i].encounter.participant.name[0].given[0],
        pnamefamily: compositionData.rbody.entry[i].encounter.participant.name[0].family,
        oname: compositionData.rbody.entry[i].encounter.serviceProvider.name,
        oplz: compositionData.rbody.entry[i].encounter.serviceProvider.address[0].postalCode,
        ocity: compositionData.rbody.entry[i].encounter.serviceProvider.address[0].city,
        ocountry: compositionData.rbody.entry[i].encounter.serviceProvider.address[0].country,
        ostreet: compositionData.rbody.entry[i].encounter.serviceProvider.address[0].line[0],
        otel:  compositionData.rbody.entry[i].encounter.serviceProvider.telecom[0].value
      };
      console.log("hier",this.observationRBody[0] );

    }
  }
   this.dataSource2 = new ObsDataSource(this.observationRBody);









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

