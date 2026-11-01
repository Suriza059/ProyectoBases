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

  // Eventos registrados (por ahora de prueba, luego vendrán del back)
  registros: any[] = [
    { id: 1, fecha: '2026-10-28', estacion: 'Portal Norte', modalidad: 'Por el torniquete', numeroPersonas: 3 },
    { id: 2, fecha: '2026-10-29', estacion: 'Calle 100', modalidad: 'Por la puerta', numeroPersonas: 1 },
  ];

  // Formulario de registrar agresión
  nuevaAgresion = {
    idEvento: null as number | null,
    descripcion: ''
  };

  // Agresiones registradas
  agresiones: any[] = [];

  constructor(private router: Router) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.entradaLista = true;
    }, 100);
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
