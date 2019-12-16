import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { map } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class ImmunizationService {
API_URL: string = "https://funke.imi.uni-luebeck.de/public/fhir/";
  constructor(private httpClient: HttpClient) { }

  getImmunization(id : number) {
    return this.httpClient.get(this.API_URL + 'Immunization/'+id).pipe(
      map(data => {
        return data;
      })
    );
  }
}
