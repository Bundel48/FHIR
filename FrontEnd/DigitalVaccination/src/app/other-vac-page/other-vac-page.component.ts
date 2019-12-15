import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-other-vac-page',
  templateUrl: './other-vac-page.component.html',
  styleUrls: ['./other-vac-page.component.css']
})
export class OtherVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Gegen','Chargennummer', 'Arztinformation'];
  constructor() { }

  ngOnInit() {
  }

}
