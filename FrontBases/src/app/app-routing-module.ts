import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Inicio } from './publico/inicio/inicio';
import { Login } from './publico/login/login';
import { Guarda } from './guarda/guarda';
import { Admin } from './admin/admin';
import { Analista } from './analista/analista';

const routes: Routes = [
  { path: '', component: Inicio },
  { path: 'login', component: Login },
  { path: 'guarda', component: Guarda },
  { path: 'admin', component: Admin },
  { path: 'analista', component: Analista },
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
