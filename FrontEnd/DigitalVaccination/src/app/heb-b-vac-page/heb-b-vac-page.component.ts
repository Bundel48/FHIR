import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-heb-b-vac-page',
  templateUrl: './heb-b-vac-page.component.html',
  styleUrls: ['./heb-b-vac-page.component.css']
})
export class HebBVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Test', 'Ergebnis', 'Arztinformation'];
  columnsToDisplay2 = ['Datum', 'Test', 'Titer','Schutz', 'Arztinformation'];
  dataSource : any;
  dataSource2 : any;

  constructor() { }

  ngOnInit() {
  }

}
