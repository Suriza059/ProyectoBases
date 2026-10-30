import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';

interface UsuarioPrueba {
  usuario: string;
  contrasena: string;
  rol: string;
  id: number;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  // SIMULACIÓN: usuarios de prueba. Cuando exista el backend, esta lista se borra.
  private usuariosPrueba: UsuarioPrueba[] = [
    { usuario: 'admin', contrasena: '1234', rol: 'ADMIN', id: 1 },
    { usuario: 'analista', contrasena: '1234', rol: 'ANALISTA', id: 2 },
    { usuario: 'guarda', contrasena: '1234', rol: 'GUARDA', id: 3 },
  ];

  // Simula la respuesta del back: si los datos son correctos devuelve
  // token, rol e id; si no, devuelve un error con el texto del mensaje
  login(usuario: string, contrasena: string): Observable<any> {
    for (const u of this.usuariosPrueba) {
      if (u.usuario === usuario && u.contrasena === contrasena) {
        return of({ token: 'token-de-prueba', role: u.rol, id: u.id });
      }
    }
    return throwError(() => ({ error: 'Usuario o contraseña incorrectos' }));
  }

  // MÁS ADELANTE, con el backend, solo cambia el método login():
  //   constructor(private http: HttpClient) {}
  //   login(usuario, contrasena) {
  //     return this.http.post<any>('http://localhost:8080/...', { usuario, contrasena });
  //   }

  guardarToken(token: string): void {
    localStorage.setItem('token', token);
  }

  guardarRol(rol: string): void {
    localStorage.setItem('rol', rol);
  }

  guardarId(id: number): void {
    localStorage.setItem('id', String(id));
  }

  obtenerRol(): string | null {
    return localStorage.getItem('rol');
  }

  cerrarSesion(): void {
    localStorage.clear();
  }
}
