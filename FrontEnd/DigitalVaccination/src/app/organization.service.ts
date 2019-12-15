import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class OrganizationService {
API_URL: string = "https://funke.imi.uni-luebeck.de/public/fhir/";
  constructor(private httpClient: HttpClient) { }

  getOrganization(id : number) {
    return this.httpClient.get(this.API_URL + 'Organization/'+id).pipe(
      map(data => {
        return data;
      })
    );
  }
}
