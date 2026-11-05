import { ChangeDetectorRef, Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  usuario = '';
  contrasena = '';
  error = '';
  rolIniciado = '';

  constructor(
      private router: Router,
      private authService: AuthService,
      private cambios: ChangeDetectorRef
  ) {}

  ingresar(): void {
    this.error = '';
    this.rolIniciado = '';

    if (!this.usuario || this.usuario.trim() === '') {
      this.error = 'El usuario es obligatorio';
      return;
    }
    if (!this.contrasena || this.contrasena.trim() === '') {
      this.error = 'La contraseña es obligatoria';
      return;
    }

    this.authService.login(this.usuario.trim(), this.contrasena).subscribe({
      next: (token) => {
        const contenido = this.authService.leerContenidoToken(token);

        if (!contenido || !contenido.rol) {
          this.error = 'No se pudo leer la respuesta del servidor';
          this.cambios.detectChanges();
          return;
        }

        this.authService.guardarToken(token);
        this.authService.guardarRol(contenido.rol);
        this.authService.guardarIdUsuario(contenido.idUsuario);

        const rol = String(contenido.rol).toLowerCase();

        if (rol === 'guarda') {
          this.router.navigate(['/guarda']);
        } else if (rol === 'administrador') {
          this.router.navigate(['/admin']);
        } else if (rol === 'analista') {
          this.router.navigate(['/analista']);
        } else {
          this.rolIniciado = contenido.rol;
          this.cambios.detectChanges();
        }
      },
      error: (err) => {
        if (err.status === 0) {
          this.error = 'No se pudo conectar con el servidor. Revisa que el back esté prendido.';
        } else if (typeof err.error === 'string' && err.error !== '') {
          this.error = err.error;
        } else {
          this.error = 'Error al iniciar sesión';
        }
        this.cambios.detectChanges();
      },
    });
  }
}
