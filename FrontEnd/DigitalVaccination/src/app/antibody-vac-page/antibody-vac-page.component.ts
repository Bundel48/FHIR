import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-antibody-vac-page',
  templateUrl: './antibody-vac-page.component.html',
  styleUrls: ['./antibody-vac-page.component.css']
})
export class AntibodyVacPageComponent implements OnInit {
  columnsToDisplay = ['Datum', 'Test', 'Ergebnis', 'Arztinformation'];
  constructor() { }

  ngOnInit() {
  }

}
