import { Component, OnInit } from '@angular/core';
import { VaccinationService} from '../vaccination.service';
import { HttpClient } from '@angular/common/http';


@Component({
  selector: 'app-overview-page',
  templateUrl: './overview-page.component.html',
  styleUrls: ['./overview-page.component.css']
})
export class OverviewPageComponent implements OnInit {
  patient: Object;
  constructor(
    private vaccinationService: VaccinationService,
    private httpClient: HttpClient
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

  ngOnInit() {
    this.vaccinationService.getVaccination();
    this.httpClient.get('http://funke.imi.uni-luebeck.de/public/fhir/Patient/146790').subscribe(
      data => {
        this.patient = data;
      }, error => {
        console.log('error: ', error);
      }
    );

  }

}
