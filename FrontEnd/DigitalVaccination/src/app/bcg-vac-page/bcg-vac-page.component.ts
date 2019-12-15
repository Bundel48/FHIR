import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-bcg-vac-page',
  templateUrl: './bcg-vac-page.component.html',
  styleUrls: ['./bcg-vac-page.component.css']
})
export class BcgVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Impfstoff', 'Chargennummer', 'Arztinformation'];
  columnsToDisplay2 = ['Datum', 'Probe', 'Reaktion', 'Arztinformation'];
  dataSource : any;
  constructor() { }

  ngOnInit() {
  }

}
