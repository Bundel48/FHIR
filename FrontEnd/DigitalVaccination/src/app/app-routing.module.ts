import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {OverviewPageComponent} from "./overview-page/overview-page.component";
import {MainVacPageComponent} from "./main-vac-page/main-vac-page.component";
import {HumanVacPageComponent} from "./human-vac-page/human-vac-page.component";
import {SerumVacPageComponent} from "./serum-vac-page/serum-vac-page.component";
import {AntibodyVacPageComponent} from "./antibody-vac-page/antibody-vac-page.component";
import {HebBVacPageComponent} from "./heb-b-vac-page/heb-b-vac-page.component";
import {RubellaVacPageComponent} from "./rubella-vac-page/rubella-vac-page.component";
import {InfluenzaVacPageComponent} from "./influenza-vac-page/influenza-vac-page.component";
import {BcgVacPageComponent} from "./bcg-vac-page/bcg-vac-page.component";
import {OtherVacPageComponent} from "./other-vac-page/other-vac-page.component";
import {RiskInfoVacPageComponent} from "./risk-info-vac-page/risk-info-vac-page.component";
import {TableTestComponent} from "./table-test/table-test.component";
import {GelbfieberComponent} from "./gelbfieber/gelbfieber.component";

const routes: Routes =[
                      {path: '',  redirectTo: '/1', pathMatch: 'full' },
                      {path: '1' , component: OverviewPageComponent},
                      {path: '2' , component: GelbfieberComponent},
                      {path: '3' , component: MainVacPageComponent},
                      {path: '4' , component: HumanVacPageComponent},
                      {path: '5' , component: SerumVacPageComponent},
                      {path: '6' , component: AntibodyVacPageComponent},
                      {path: '7' , component: HebBVacPageComponent},
                      {path: '8' , component: RubellaVacPageComponent},
                      {path: '9' , component: InfluenzaVacPageComponent},
                      {path: '10' , component: BcgVacPageComponent},
                      {path: '11' , component: OtherVacPageComponent},
                      {path: '12' , component: RiskInfoVacPageComponent},
                      ];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
