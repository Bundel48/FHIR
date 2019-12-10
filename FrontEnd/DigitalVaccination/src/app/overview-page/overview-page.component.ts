import { Component, OnInit } from '@angular/core';
import { VaccinationService} from '../vaccination.service';

@Component({
  selector: 'app-overview-page',
  templateUrl: './overview-page.component.html',
  styleUrls: ['./overview-page.component.css']
})
export class OverviewPageComponent implements OnInit {

  constructor(private vaccinationService: VaccinationService) { }

  ngOnInit() {
  this.vaccinationService.getVaccination();
  }

}
