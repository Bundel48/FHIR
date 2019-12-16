import { Component, OnInit } from '@angular/core';
import { VaccinationService} from '../vaccination.service';
import { HttpClient } from '@angular/common/http';
import {CompositionService} from '../composition.service';


@Component({
  selector: 'app-overview-page',
  templateUrl: './overview-page.component.html',
  styleUrls: ['./overview-page.component.css']
})
export class OverviewPageComponent implements OnInit {
  patient: any;
  constructor(
    private vaccinationService: VaccinationService,
    private httpClient: HttpClient,
    private compositionService: CompositionService
  ) {
    this.patient = {
      "extension": [
        {
         "valueAddress": {
          "city": "Lübeck",
          "country": "DE"
          }
        }
      ],
      "name": [
         {
          "family": "Vorname",
          "given": [
            "Nachname"
          ]
         }
      ],
      "birthDate": "1900-01-01",
      "address": [
        {
          "line": [
            "Straße mit Nr"
          ],
          "city": "Stadt",
          "postalCode": "PLZ",
          "country": "LAND"
        }
          ]
    }

  }

  async ngOnInit() {
    let compositionData = await this.compositionService.compositionData;
    this.patient = compositionData.subject;
  }

}
