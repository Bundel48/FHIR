import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-serum-vac-page',
  templateUrl: './serum-vac-page.component.html',
  styleUrls: ['./serum-vac-page.component.css']
})
export class SerumVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum','Typ', 'Chargennummer', 'Dosis', 'Arztinformation'];
  dataSource : any;

  constructor() { }

  ngOnInit() {
  }

}
