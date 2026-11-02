import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-guarda',
  standalone: false,
  styleUrl: './guarda.css',
  templateUrl: './guarda.html',
})
export class Guarda implements OnInit {

  // Dirección del back
  urlBase: string = 'http://localhost:8080';

  // TEMPORAL: es el id del guarda de prueba. Cuando el token del login
  // traiga el idGuarda, hay que cambiar esta línea para leerlo de ahí.
  idGuarda: number = 1;

  // Sección que se está viendo: 'registrar', 'registros' o 'agresion'
  seccionActiva: string = 'registrar';

  // Animación de entrada (setTimeout + booleano)
  entradaLista: boolean = false;

  // Fecha de hoy en formato aaaa-mm-dd
  fechaHoy: string = '';

  // Mensajes para el guarda
  mensajeExito: string = '';
  mensajeError: string = '';

  // Valores de la gravedad (cambiar si Esteban dice otros)
  gravedades: string[] = ['Leve', 'Media', 'Grave'];

  // Listas que vienen del back
  estaciones: any[] = [];
  modalidades: any[] = [];
  tarifas: any[] = [];
  registros: any[] = [];
  agresiones: any[] = [];

  // Formulario de registrar colado
  nuevoColado = {
    idEstacion: null as number | null,
    idModalidad: null as number | null,
    numeroPersonas: 1,
    fecha: ''
  };

  // Formulario de registrar agresión
  nuevaAgresion = {
    idEvento: null as number | null,
    gravedad: '',
    fecha: '',
    descripcion: ''
  };

  constructor(
    private router: Router,
    private http: HttpClient,
    private cambios: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // Si no hay token guardado, no se puede estar aquí
    if (!localStorage.getItem('token')) {
      this.router.navigate(['/login']);
      return;
    }

    this.fechaHoy = this.obtenerFechaHoy();

    this.cargarEstaciones();
    this.cargarModalidades();
    this.cargarTarifas();
    this.cargarEventos();
    this.cargarAgresiones();

    setTimeout(() => {
      this.entradaLista = true;
      this.cambios.markForCheck();
    }, 100);
  }

  // ---------- Ayudas ----------

  // Devuelve la fecha de hoy como texto aaaa-mm-dd
  obtenerFechaHoy(): string {
    const hoy = new Date();
    const anio = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, '0');
    const dia = String(hoy.getDate()).padStart(2, '0');
    return anio + '-' + mes + '-' + dia;
  }

  // Encabezado con el token (la "manilla") para cada llamada al back
  private encabezados(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({ Authorization: 'Bearer ' + token });
  }

  // Solo las estaciones activas (el back escribe "Si" o "si")
  estacionesActivas(): any[] {
    return this.estaciones.filter(e => String(e.activa).toLowerCase() === 'si');
  }

  nombreEstacion(id: number): string {
    const estacion = this.estaciones.find(e => e.id === id);
    return estacion ? estacion.nombre : 'Estación ' + id;
  }

  nombreModalidad(id: number): string {
    const modalidad = this.modalidades.find(m => m.id === id);
    return modalidad ? modalidad.nombre : 'Modalidad ' + id;
  }

  mostrarExito(texto: string): void {
    this.mensajeExito = texto;
    this.cambios.markForCheck();
    setTimeout(() => {
      this.mensajeExito = '';
      this.cambios.markForCheck();
    }, 3000);
  }

  manejarError(err: any): void {
    // 401: el token venció o no sirve, hay que iniciar sesión otra vez
    if (err.status === 401) {
      localStorage.clear();
      this.router.navigate(['/login']);
      return;
    }

    if (err.status === 0) {
      this.mensajeError = 'No se pudo conectar con el servidor. Revisa que el back esté prendido.';
    } else if (typeof err.error === 'string' && err.error !== '') {
      this.mensajeError = err.error;
    } else {
      this.mensajeError = 'Ocurrió un error. Intenta de nuevo.';
    }
    this.cambios.markForCheck();
  }

  cambiarSeccion(nombre: string): void {
    this.seccionActiva = nombre;
    this.mensajeExito = '';
    this.mensajeError = '';
  }

  // ---------- Cargar datos del back ----------

  cargarEstaciones(): void {
    this.http.get<any[]>(this.urlBase + '/estacion/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.estaciones = lista;
        this.cambios.markForCheck();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarModalidades(): void {
    this.http.get<any[]>(this.urlBase + '/modalidad/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.modalidades = lista;
        this.cambios.markForCheck();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarTarifas(): void {
    this.http.get<any[]>(this.urlBase + '/tarifa/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.tarifas = lista;
        this.cambios.markForCheck();
      },
      error: (err) => this.manejarError(err)
    });
  }

  // Trae todos los eventos y deja solo los de este guarda, el más nuevo primero
  cargarEventos(): void {
    this.http.get<any[]>(this.urlBase + '/evento/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.registros = lista
          .filter(e => e.idGuarda === this.idGuarda)
          .sort((a, b) => b.id - a.id);
        this.cambios.markForCheck();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarAgresiones(): void {
    this.http.get<any[]>(this.urlBase + '/agresion/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.agresiones = lista
          .filter(a => a.idGuarda === this.idGuarda)
          .sort((a, b) => b.id - a.id);
        this.cambios.markForCheck();
      },
      error: (err) => this.manejarError(err)
    });
  }

  // ---------- Guardar en el back ----------

  registrarColado(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (this.nuevoColado.idEstacion === null || this.nuevoColado.idModalidad === null || !this.nuevoColado.fecha) {
      this.mensajeError = 'Falta llenar la estación, la modalidad o la fecha.';
      return;
    }
    if (this.nuevoColado.fecha > this.fechaHoy) {
      this.mensajeError = 'La fecha no puede ser posterior a hoy.';
      return;
    }
    if (!this.nuevoColado.numeroPersonas || this.nuevoColado.numeroPersonas < 1) {
      this.mensajeError = 'El número de personas debe ser mínimo 1.';
      return;
    }

    // Se busca la tarifa que cubre la fecha del colado
    const tarifa = this.tarifas.find(t =>
      this.nuevoColado.fecha >= t.fechaInicio && this.nuevoColado.fecha <= t.fechaFin
    );
    if (!tarifa) {
      this.mensajeError = 'No hay una tarifa vigente para esa fecha.';
      return;
    }

    const parametros = new HttpParams()
      .set('fecha', this.nuevoColado.fecha)
      .set('numeroPersonas', String(this.nuevoColado.numeroPersonas))
      .set('idEstacion', String(this.nuevoColado.idEstacion))
      .set('idTarifa', String(tarifa.id))
      .set('idModalidad', String(this.nuevoColado.idModalidad))
      .set('idGuarda', String(this.idGuarda));

    this.http.post(this.urlBase + '/evento/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevoColado = { idEstacion: null, idModalidad: null, numeroPersonas: 1, fecha: '' };
        this.mostrarExito('Colado registrado.');
        this.cargarEventos();
      },
      error: (err) => this.manejarError(err)
    });
  }

  registrarAgresion(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (this.nuevaAgresion.idEvento === null || !this.nuevaAgresion.gravedad ||
      !this.nuevaAgresion.fecha || !this.nuevaAgresion.descripcion.trim()) {
      this.mensajeError = 'Falta escoger el evento, la gravedad, la fecha o contar qué pasó.';
      return;
    }
    if (this.nuevaAgresion.fecha > this.fechaHoy) {
      this.mensajeError = 'La fecha no puede ser posterior a hoy.';
      return;
    }

    // La agresión no puede ser antes del colado en el que ocurrió
    const evento = this.registros.find(r => r.id === this.nuevaAgresion.idEvento);
    if (evento && this.nuevaAgresion.fecha < evento.fecha) {
      this.mensajeError = 'La agresión no puede ser anterior al colado.';
      return;
    }

    const parametros = new HttpParams()
      .set('fecha', this.nuevaAgresion.fecha)
      .set('gravedad', this.nuevaAgresion.gravedad)
      .set('descripcion', this.nuevaAgresion.descripcion.trim())
      .set('idEvento', String(this.nuevaAgresion.idEvento))
      .set('idGuarda', String(this.idGuarda));

    this.http.post(this.urlBase + '/agresion/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevaAgresion = { idEvento: null, gravedad: '', fecha: '', descripcion: '' };
        this.mostrarExito('Agresión registrada.');
        this.cargarAgresiones();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cerrarSesion(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
