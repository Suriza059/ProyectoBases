import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit {
  usuario = '';
  contrasena = '';
  error = '';

  // Por ahora, el rol con el que se ingresó (para mostrar un mensaje)
  rolIniciado = '';

  // BOOLEANOS DE ANIMACIÓN
  mostrarTitulo = false;
  mostrarFormulario = false;

  constructor(private authService: AuthService) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.mostrarTitulo = true;
    }, 200);

    setTimeout(() => {
      this.mostrarFormulario = true;
    }, 700);
  }

  ingresar(): void {
    this.error = '';
    this.rolIniciado = '';

    if (!this.usuario || this.usuario.trim() === '') {
      this.mostrarError('El usuario es obligatorio');
      return;
    }
    if (!this.contrasena || this.contrasena.trim() === '') {
      this.mostrarError('La contraseña es obligatoria');
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
        this.mostrarError(
          typeof err.error === 'string' ? err.error : 'Error al iniciar sesión'
        );
      },
    });
  }

  // Borra el mensaje y lo vuelve a poner un instante después, para que
  // la animación de sacudida se repita en cada intento fallido
  mostrarError(texto: string): void {
    this.error = '';
    setTimeout(() => {
      this.error = texto;
    }, 50);
  }
}
