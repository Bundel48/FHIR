import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
    constructor(private http:HttpClient, private location: Location, private router: Router) {
    }
  title = 'DigitalVaccination';
  currentPage: number;
  nextLink = "/1";
  prevLink = "/2";
  maxPage = 2;


  ngOnInit() {
    this.updateButtons();

    this.router.events.subscribe(val => {
      this.updateButtons();
    });
  }

  updateButtons() {
    let paths = this.location.path().split('/');
    this.currentPage = parseInt(paths[paths.length - 1]);
    if (this.currentPage > this.maxPage - 1) {
      this.nextLink = "";
    } else {
      this.nextLink = "/" + ((this.currentPage + 1));
    }
    if (this.currentPage > 1) {
      this.prevLink = "/" + (this.currentPage - 1);
    } else {
      this.prevLink = "";
    }
  }
}
