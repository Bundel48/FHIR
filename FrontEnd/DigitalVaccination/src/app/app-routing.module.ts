import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

//Meine Produkt-Komponenten -> Saaeter ersetzen
//import { ProductListComponent } from "./product-list/product-list.component";
//import { ProductDetailComponent } from "./product-detail/product-detail.component";


//Inserted my own routes

const routes: Routes =[
                      //empty path
                      //{path: '',  redirectTo: '/vaccination', pathMatch: 'full' },
                      //{path: 'overview' , component: overview-page},
                      ];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
