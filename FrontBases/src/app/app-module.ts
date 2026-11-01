import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Inicio } from './publico/inicio/inicio';
import { Login } from './publico/login/login';
import { RegistrarEvento } from './guarda/registrar-evento/registrar-evento';
import {Guarda} from './guarda/guarda';


@NgModule({
  declarations: [App, Inicio, Login, RegistrarEvento, Guarda],
  imports: [BrowserModule, FormsModule, AppRoutingModule],
  providers: [provideBrowserGlobalErrorListeners()],
  bootstrap: [App],
})
export class AppModule {}
