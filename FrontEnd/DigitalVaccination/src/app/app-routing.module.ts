import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {OverviewPageComponent} from "./overview-page/overview-page.component";
import {MainVacPageComponent} from "./main-vac-page/main-vac-page.component";

const routes: Routes =[
                      {path: '',  redirectTo: '', pathMatch: 'full' },
                      {path: '1' , component: OverviewPageComponent},
                      {path: '2' , component: MainVacPageComponent},
                      ];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
