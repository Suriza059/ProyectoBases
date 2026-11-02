import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Inicio } from './publico/inicio/inicio';
import { Login } from './publico/login/login';
import { Guarda } from './guarda/guarda';

const routes: Routes = [
  { path: '', component: Inicio },
  { path: 'login', component: Login },
  { path: 'guarda', component: Guarda },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
