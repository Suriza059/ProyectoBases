import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-guarda',
  standalone: false,
  styleUrl: './guarda.css',
  templateUrl: './guarda.html',
})
export class Guarda implements OnInit {

  // Sección que se está viendo: 'registrar', 'registros' o 'agresion'
  seccionActiva: string = 'registrar';

  // Animación de entrada (setTimeout + booleano)
  entradaLista: boolean = false;

  // Fecha de hoy en formato aaaa-mm-dd (se calcula en ngOnInit)
  fechaHoy: string = '';

  // Mensajes para el guarda
  mensajeExito: string = '';
  mensajeError: string = '';

  // Datos de prueba: cámbialos por los de tu proyecto
  estaciones: string[] = ['Portal Norte', 'Calle 100', 'Av. Jiménez', 'Portal Américas'];
  modalidades: string[] = ['Por el torniquete', 'Por la puerta', 'Saltando la barrera'];

  // Formulario de registrar colado
  nuevoColado = {
    estacion: '',
    modalidad: '',
    numeroPersonas: 1,
    fecha: ''
  };

  // Eventos registrados (empieza vacío; luego vendrán del back)
  registros: any[] = [];

  // Formulario de registrar agresión
  nuevaAgresion = {
    idEvento: null as number | null,
    descripcion: ''
  };

  // Agresiones registradas
  agresiones: any[] = [];

  constructor(private router: Router) {}

  ngOnInit(): void {
    this.fechaHoy = this.obtenerFechaHoy();

    setTimeout(() => {
      this.entradaLista = true;
    }, 100);
  }

  // Devuelve la fecha de hoy como texto aaaa-mm-dd
  obtenerFechaHoy(): string {
    const hoy = new Date();
    const anio = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, '0');
    const dia = String(hoy.getDate()).padStart(2, '0');
    return anio + '-' + mes + '-' + dia;
  }

  cambiarSeccion(nombre: string): void {
    this.seccionActiva = nombre;
    this.mensajeExito = '';
    this.mensajeError = '';
  }

  registrarColado(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.nuevoColado.estacion || !this.nuevoColado.modalidad || !this.nuevoColado.fecha) {
      this.mensajeError = 'Falta llenar la estación, la modalidad o la fecha.';
      return;
    }
    if (this.nuevoColado.fecha > this.fechaHoy) {
      this.mensajeError = 'La fecha no puede ser posterior a hoy.';
      return;
    }
    if (this.nuevoColado.numeroPersonas < 1) {
      this.mensajeError = 'El número de personas debe ser mínimo 1.';
      return;
    }

    // El id lo asignará la base de datos; aquí es solo de prueba
    const nuevoId = this.registros.length + 1;

    this.registros.unshift({
      id: nuevoId,
      fecha: this.nuevoColado.fecha,
      estacion: this.nuevoColado.estacion,
      modalidad: this.nuevoColado.modalidad,
      numeroPersonas: this.nuevoColado.numeroPersonas
    });

    this.nuevoColado = { estacion: '', modalidad: '', numeroPersonas: 1, fecha: '' };
    this.mensajeExito = 'Colado registrado.';
    setTimeout(() => {
      this.mensajeExito = '';
    }, 3000);
  }

  registrarAgresion(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (this.nuevaAgresion.idEvento === null || !this.nuevaAgresion.descripcion.trim()) {
      this.mensajeError = 'Escoge el evento y escribe qué pasó.';
      return;
    }

    const evento = this.registros.find(r => r.id === this.nuevaAgresion.idEvento);

    this.agresiones.unshift({
      idEvento: this.nuevaAgresion.idEvento,
      estacion: evento ? evento.estacion : '',
      fecha: evento ? evento.fecha : '',
      descripcion: this.nuevaAgresion.descripcion
    });

    this.nuevaAgresion = { idEvento: null, descripcion: '' };
    this.mensajeExito = 'Agresión registrada.';
    setTimeout(() => {
      this.mensajeExito = '';
    }, 3000);
  }

  cerrarSesion(): void {
    this.router.navigate(['/login']);
  }
}
