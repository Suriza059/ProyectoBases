import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-analista',
  standalone: false,
  styleUrl: './analista.css',
  templateUrl: './analista.html',
})
export class Analista implements OnInit {

  // Dirección del back
  urlBase: string = 'http://localhost:8080';

  // Sección que se está viendo
  seccionActual: string = 'troncales';

  // Mensaje de error para el analista
  mensajeError: string = '';

  // Datos que vienen del back
  troncales: any[] = [];
  eventos: any[] = [];
  estaciones: any[] = [];
  modalidades: any[] = [];

  // false = las barras están vacías, true = se llenan con animación
  barrasListas: boolean = false;

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

    this.cargarRanking();
    this.cargarEstaciones();
    this.cargarModalidades();
    this.cargarEventos();
  }

  // ---------- Ayudas ----------

  // Encabezado con el token para cada llamada al back
  private encabezados(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({ Authorization: 'Bearer ' + token });
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
    } else if (err.status === 403) {
      this.mensajeError = 'No tienes permiso para ver esta información.';
    } else if (typeof err.error === 'string' && err.error !== '') {
      this.mensajeError = err.error;
    } else {
      this.mensajeError = 'Ocurrió un error. Intenta de nuevo.';
    }
    this.cambios.detectChanges();
  }

  cambiarSeccion(clave: string): void {
    this.seccionActual = clave;
    this.mensajeError = '';

    // Al volver al ranking, las barras se llenan otra vez
    if (clave === 'troncales') {
      this.animarBarras();
    }
  }

  // Vacía las barras y las llena con un pequeño retraso, para que se vea la animación
  animarBarras(): void {
    this.barrasListas = false;
    this.cambios.detectChanges();
    setTimeout(() => {
      this.barrasListas = true;
      this.cambios.detectChanges();
    }, 250);
  }

  // Convierte una fecha aaaa-mm-dd en dd/mm/aaaa para mostrarla
  formatearFecha(fecha: string): string {
    if (!fecha) {
      return '';
    }
    const partes = fecha.substring(0, 10).split('-');
    if (partes.length !== 3) {
      return fecha;
    }
    return partes[2] + '/' + partes[1] + '/' + partes[0];
  }

  // Devuelve el nombre de la estación a partir de su id
  nombreEstacion(id: number): string {
    const estacion = this.estaciones.find(e => e.id === id);
    return estacion ? estacion.nombre : 'Estación ' + id;
  }

  // Devuelve el nombre de la modalidad a partir de su id
  nombreModalidad(id: number): string {
    const modalidad = this.modalidades.find(m => m.id === id);
    return modalidad ? modalidad.nombre : 'Modalidad ' + id;
  }

  // ---------- Troncales (ranking) ----------

  cargarRanking(): void {
    this.mensajeError = '';
    this.http.get<any[]>(this.urlBase + '/estadistica/troncales', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        // De la más afectada a la menos afectada
        this.troncales = lista.sort((a, b) => b.porcentaje - a.porcentaje);
        this.cambios.detectChanges();
        this.animarBarras();
      },
      error: (err) => this.manejarError(err)
    });
  }

  // ---------- Colados registrados ----------

  cargarEstaciones(): void {
    this.http.get<any[]>(this.urlBase + '/estacion/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.estaciones = lista;
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarModalidades(): void {
    this.http.get<any[]>(this.urlBase + '/modalidad/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.modalidades = lista;
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarEventos(): void {
    // OJO: confirma con Esteban la ruta y los nombres de los campos
    // (aquí se usa id, fecha, numeroPersonas, idEstacion e idModalidad)
    this.http.get<any[]>(this.urlBase + '/evento/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        // Del más reciente al más antiguo
        this.eventos = lista.sort((a, b) => b.id - a.id);
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cerrarSesion(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
