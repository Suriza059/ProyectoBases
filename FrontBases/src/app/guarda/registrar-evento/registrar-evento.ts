import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import {
  EventoService,
  Estacion,
  Modalidad,
  EventoRegistrado,
} from '../../services/evento.service';

@Component({
  selector: 'app-registrar-evento',
  standalone: false,
  templateUrl: './registrar-evento.html',
  styleUrl: './registrar-evento.css',
})
export class RegistrarEvento implements OnInit {
  // Lo que el guarda escribe o elige
  fecha = '';
  numeroPersonas: number | null = null;
  idEstacion: number | null = null;
  idModalidad: number | null = null;

  // Listas para los desplegables y para la lista de abajo
  estaciones: Estacion[] = [];
  modalidades: Modalidad[] = [];
  eventosRegistrados: EventoRegistrado[] = [];

  // Fecha de hoy en formato 2026-10-30 (para no permitir fechas futuras)
  hoy = '';

  // Mensajes
  error = '';
  exito = '';

  constructor(
    private router: Router,
    private authService: AuthService,
    private eventoService: EventoService
  ) {}

  ngOnInit(): void {
    // Si no inició sesión como guarda, lo devolvemos al login
    if (this.authService.obtenerRol() !== 'GUARDA') {
      this.router.navigate(['/login']);
      return;
    }

    this.calcularHoy();

    this.eventoService.listarEstaciones().subscribe({
      next: (lista) => {
        this.estaciones = lista;
      },
    });

    this.eventoService.listarModalidades().subscribe({
      next: (lista) => {
        this.modalidades = lista;
      },
    });

    this.cargarEventos();
  }

  // Arma la fecha de hoy con el día de tu computador
  calcularHoy(): void {
    const ahora = new Date();
    const anio = ahora.getFullYear();
    const mes = String(ahora.getMonth() + 1).padStart(2, '0');
    const dia = String(ahora.getDate()).padStart(2, '0');
    this.hoy = anio + '-' + mes + '-' + dia;
  }

  cargarEventos(): void {
    this.eventoService.listarEventos().subscribe({
      next: (lista) => {
        this.eventosRegistrados = lista;
      },
    });
  }

  registrar(): void {
    this.error = '';
    this.exito = '';

    if (!this.fecha) {
      this.error = 'La fecha es obligatoria';
      return;
    }
    if (this.fecha > this.hoy) {
      this.error = 'La fecha no puede ser futura';
      return;
    }
    if (this.numeroPersonas === null) {
      this.error = 'El número de personas es obligatorio';
      return;
    }
    if (this.numeroPersonas < 1 || !Number.isInteger(this.numeroPersonas)) {
      this.error = 'El número de personas debe ser un entero mayor que 0';
      return;
    }
    if (this.idEstacion === null) {
      this.error = 'Selecciona la estación';
      return;
    }
    if (this.idModalidad === null) {
      this.error = 'Selecciona la modalidad';
      return;
    }

    this.eventoService
      .registrarEvento({
        fecha: this.fecha,
        numeroPersonas: this.numeroPersonas,
        idEstacion: this.idEstacion,
        idModalidad: this.idModalidad,
      })
      .subscribe({
        next: () => {
          this.exito = 'Evento registrado correctamente';
          this.limpiarFormulario();
          this.cargarEventos();
        },
        error: (err) => {
          this.error =
            typeof err.error === 'string'
              ? err.error
              : 'Error al registrar el evento';
        },
      });
  }

  limpiarFormulario(): void {
    this.fecha = '';
    this.numeroPersonas = null;
    this.idEstacion = null;
    this.idModalidad = null;
  }

  cerrarSesion(): void {
    this.authService.cerrarSesion();
    this.router.navigate(['/login']);
  }
}
