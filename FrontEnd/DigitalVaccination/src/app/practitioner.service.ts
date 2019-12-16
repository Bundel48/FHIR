import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class PractitionerService {
API_URL: string = "https://funke.imi.uni-luebeck.de/public/fhir/";
  constructor(private httpClient: HttpClient) { }

  getPractitioner(id : number) {
    return this.httpClient.get(this.API_URL + 'Practitioner/'+id).pipe(
      map(data => {
        return data;
      })
    );
  }
}
