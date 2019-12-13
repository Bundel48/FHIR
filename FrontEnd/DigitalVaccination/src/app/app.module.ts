import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

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


@NgModule({
  declarations: [
    AppComponent,
    //ProductListComponent,
    //ProductDetailComponent,
    OverviewPageComponent,
    MainVacPageComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    //InMemoryWebApiModule.forRoot(DataService),
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
