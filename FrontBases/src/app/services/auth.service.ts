import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private urlBase = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  login(usuario: string, contrasena: string): Observable<string> {
    const parametros = new HttpParams()
      .set('nombreUsuario', usuario)
      .set('contrasena', contrasena);

    return this.http.post(this.urlBase + '/auth/login', null, {
      params: parametros,
      responseType: 'text',
    });
  }

  leerContenidoToken(token: string): any {
    try {
      const parteMedio = token.split('.')[1];
      const texto = parteMedio.replace(/-/g, '+').replace(/_/g, '/');
      return JSON.parse(atob(texto));
    } catch (e) {
      return null;
    }
  }

  guardarToken(token: string): void {
    localStorage.setItem('token', token);
  }

  guardarRol(rol: string): void {
    localStorage.setItem('rol', rol);
  }

  guardarIdUsuario(id: number): void {
    localStorage.setItem('idUsuario', String(id));
  }

  guardarIdGuarda(id: number | null): void {
    if (id) {
      localStorage.setItem('idGuarda', String(id));
    } else {
      localStorage.removeItem('idGuarda');
    }
  }

  obtenerToken(): string | null {
    return localStorage.getItem('token');
  }

  obtenerRol(): string | null {
    return localStorage.getItem('rol');
  }

  obtenerIdUsuario(): string | null {
    return localStorage.getItem('idUsuario');
  }

  obtenerIdGuarda(): string | null {
    return localStorage.getItem('idGuarda');
  }

  cerrarSesion(): void {
    localStorage.clear();
  }
}
