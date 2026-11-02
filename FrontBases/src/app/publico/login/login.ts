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

  // Para los roles que todavía no tienen panel
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
        console.log('Contenido del token:', contenido); // BORRAR después de probar

        if (!contenido || !contenido.rol) {
          this.error = 'No se pudo leer la respuesta del servidor';
          this.cambios.detectChanges();
          return;
        }

        this.authService.guardarToken(token);
        this.authService.guardarRol(contenido.rol);
        this.authService.guardarIdUsuario(contenido.idUsuario);

        // Se compara en minúsculas para que no importe cómo escribe el rol el back
        const rol = String(contenido.rol).toLowerCase();

        if (rol === 'guarda') {
          this.router.navigate(['/guarda']);
        } else if (rol === 'administrador') {
          this.router.navigate(['/admin']);
        } else {
          // El panel de Analista todavía no existe
          this.rolIniciado = contenido.rol;
          this.cambios.detectChanges();
        }
      },
      error: (err) => {
        console.log('Error del login:', err); // BORRAR después de probar

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
