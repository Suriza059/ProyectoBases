import { Component } from '@angular/core';
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

  // Por ahora, el rol con el que se ingresó (para mostrar un mensaje)
  rolIniciado = '';

  constructor(private authService: AuthService) {}

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

        this.rolIniciado = response.role;

        // MÁS ADELANTE (cuando existan los paneles): inyectar Router en el
        // constructor (private router: Router) y enviar a cada rol a su panel:
        //   if (response.role === 'ADMIN') {
        //     this.router.navigate(['/admin']);
        //   } else if (response.role === 'ANALISTA') {
        //     this.router.navigate(['/analista']);
        //   } else {
        //     this.router.navigate(['/guarda']);
        //   }
      },
      error: (err) => {
        this.error = typeof err.error === 'string'
          ? err.error
          : 'Error al iniciar sesión';
      },
    });
  }
}
