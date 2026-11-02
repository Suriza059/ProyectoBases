import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  // Dirección del back (Spring Boot)
  private urlBase = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // Llama al login del back. Manda usuario y contraseña en la URL
  // y recibe el token como texto
  login(usuario: string, contrasena: string): Observable<string> {
    const parametros = new HttpParams()
      .set('nombreUsuario', usuario)
      .set('contrasena', contrasena);

    return this.http.post(this.urlBase + '/auth/login', null, {
      params: parametros,
      responseType: 'text',
    });
  }

  // Traduce la parte del medio del token y devuelve su contenido
  // (rol, idUsuario...). Si algo falla devuelve null
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

  obtenerToken(): string | null {
    return localStorage.getItem('token');
  }

  obtenerRol(): string | null {
    return localStorage.getItem('rol');
  }

  obtenerIdUsuario(): string | null {
    return localStorage.getItem('idUsuario');
  }

  cerrarSesion(): void {
    localStorage.clear();
  }
}
