import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-influenza-vac-page',
  templateUrl: './influenza-vac-page.component.html',
  styleUrls: ['./influenza-vac-page.component.css']
})
export class InfluenzaVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Chargennummer', 'Arztinformation'];
  constructor() { }

  ngOnInit() {
  }

}
