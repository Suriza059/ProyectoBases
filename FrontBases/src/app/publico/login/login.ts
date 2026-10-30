import { Component } from '@angular/core';
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

  constructor(private router: Router, private authService: AuthService) {}

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
      next: (response) => {
        this.authService.guardarToken(response.token);
        this.authService.guardarRol(response.role);
        this.authService.guardarId(response.id);

        if (response.role === 'GUARDA') {
          this.router.navigate(['/guarda']);
        } else {
          // Los paneles de ADMIN y ANALISTA todavía no existen
          this.rolIniciado = response.role;
        }
      },
      error: (err) => {
        this.error = typeof err.error === 'string'
          ? err.error
          : 'Error al iniciar sesión';
      },
    });
  }
}
