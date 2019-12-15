import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { MatInputModule, MatPaginatorModule, MatProgressSpinnerModule,
         MatSortModule, MatTableModule } from "@angular/material";

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule } from "@angular/common/http";
//InMemory kann spaeter wieder entfernt werden. Nur fuer Offline versuche benoetigt
//import { InMemoryWebApiModule } from "angular-in-memory-web-api";
//import { DataService } from "./data.service";
//import { ProductListComponent } from './product-list/product-list.component';
//import { ProductDetailComponent } from './product-detail/product-detail.component';
import { OverviewPageComponent } from './overview-page/overview-page.component';
import { MainVacPageComponent } from './main-vac-page/main-vac-page.component';
import { TableTestComponent } from './table-test/table-test.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { GelbfieberComponent } from './gelbfieber/gelbfieber.component';
import { OtherVacPageComponent } from './other-vac-page/other-vac-page.component';
import { InfluenzaVacPageComponent } from './influenza-vac-page/influenza-vac-page.component';
import { BcgVacPageComponent } from './bcg-vac-page/bcg-vac-page.component';
import { AntibodyVacPageComponent } from './antibody-vac-page/antibody-vac-page.component';
import { HebBVacPageComponent } from './heb-b-vac-page/heb-b-vac-page.component';
import { HumanVacPageComponent } from './human-vac-page/human-vac-page.component';
import { SerumVacPageComponent } from './serum-vac-page/serum-vac-page.component';
import {MatCheckboxModule} from '@angular/material/checkbox';



@NgModule({
  declarations: [
    AppComponent,
    OverviewPageComponent,
    MainVacPageComponent,
    TableTestComponent,
    GelbfieberComponent,
    OtherVacPageComponent,
    InfluenzaVacPageComponent,
    BcgVacPageComponent,
    AntibodyVacPageComponent,
    HebBVacPageComponent,
    HumanVacPageComponent,
    SerumVacPageComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    //InMemoryWebApiModule.forRoot(DataService),
    AppRoutingModule,
    BrowserAnimationsModule,
    MatInputModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatCheckboxModule,
    MatProgressSpinnerModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
