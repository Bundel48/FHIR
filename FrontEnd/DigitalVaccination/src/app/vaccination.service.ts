import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class VaccinationService {
  API_URL: string = "api/";
  constructor(private httpClient: HttpClient) { }

    getVaccination() {
      return this.httpClient.get(this.API_URL + 'vaccination')
    }
}
