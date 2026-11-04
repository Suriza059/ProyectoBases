import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing-module';
import { App } from './app';
import { Inicio } from './publico/inicio/inicio';
import { Login } from './publico/login/login';
import { RegistrarEvento } from './guarda/registrar-evento/registrar-evento';
import { Guarda } from './guarda/guarda';
import { provideHttpClient } from '@angular/common/http';
import { Admin } from './admin/admin';
import { Analista } from './analista/analista';

@NgModule({
  declarations: [App, Inicio, Login, RegistrarEvento, Guarda, Admin, Analista],
  imports: [BrowserModule, FormsModule, AppRoutingModule],
  providers: [provideBrowserGlobalErrorListeners(), provideHttpClient()],
  bootstrap: [App],
})
export class AppModule {}
