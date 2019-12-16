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
}


@Component({
  selector: 'app-bcg-vac-page',
  templateUrl: './bcg-vac-page.component.html',
  styleUrls: ['./bcg-vac-page.component.css']
})
export class BcgVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Probe', 'Reaktion', 'Arztinformation'];
  observation: Array<ObservationData> = [];
   practitioner: any;
   organization: any;
   dataSource:  ObsDataSource = new ObsDataSource([]);
   expandedElement: ObservationData | null;
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
     if(typeof compositionData.tub.entry !== 'undefined'){
       for(let i = 0; i < compositionData.tub.entry.length; i++){
         this.practitioner = compositionData.tub.entry[i].encounter.participant;
         this.organization = compositionData.tub.entry[i].encounter.serviceProvider;


         this.observation[i] = {
           display: compositionData.tub.entry[i].code.coding[0].display,
           date: compositionData.tub.entry[i].effectiveDateTime,
           reaction: compositionData.tub.entry[i].interpretation[0].coding[0].display,
           code: compositionData.tub.entry[i].category[0].coding[0].display,
         };

         compositionData.tub.entry[i];
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
