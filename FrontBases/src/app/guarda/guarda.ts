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

  urlBase: string = 'http://localhost:8080';

  idGuarda: number = 1;

  seccionActiva: string = 'registrar';

  entradaLista: boolean = false;

  fechaHoy: string = '';

  mensajeExito: string = '';
  mensajeError: string = '';

  gravedades: string[] = ['Leve', 'Moderada', 'Grave'];

  tiposDocumento: string[] = ['CC', 'CE', 'TI', 'PAS'];

  estaciones: any[] = [];
  modalidades: any[] = [];
  tarifas: any[] = [];
  registros: any[] = [];
  agresiones: any[] = [];
  infractores: any[] = [];
  parejas: any[] = [];

  nuevoColado = {
    idEstacion: null as number | null,
    idModalidad: null as number | null,
    numeroPersonas: 1,
    fecha: '',
    hora: ''
  };

  nuevaAgresion = {
    idEvento: null as number | null,
    gravedad: '',
    fecha: '',
    descripcion: ''
  };

  nuevoInfractor = {
    idEvento: null as number | null,
    tipoDocumento: '',
    numeroDocumento: '',
    nombre: '',
    apellido: ''
  };

  constructor(
    private router: Router,
    private http: HttpClient,
    private cambios: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
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
    this.cargarInfractores();
    this.cargarParejas();

    setTimeout(() => {
      this.entradaLista = true;
      this.cambios.markForCheck();
    }, 100);
  }

  obtenerFechaHoy(): string {
    const hoy = new Date();
    const anio = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, '0');
    const dia = String(hoy.getDate()).padStart(2, '0');
    return anio + '-' + mes + '-' + dia;
  }

  private encabezados(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({ Authorization: 'Bearer ' + token });
  }

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

  cantidadIdentificados(idEvento: number): number {
    return this.parejas.filter(p => p.idEvento === idEvento).length;
  }

  infractoresDelEvento(idEvento: number | null): any[] {
    const lista: any[] = [];
    for (const pareja of this.parejas) {
      if (pareja.idEvento === idEvento) {
        const infractor = this.infractores.find(i => i.id === pareja.idInfractor);
        if (infractor) {
          lista.push(infractor);
        }
      }
    }
    return lista;
  }

  eventoEscogido(): any {
    return this.registros.find(r => r.id === this.nuevoInfractor.idEvento);
  }

  nombreValido(texto: string): boolean {
    return /^[A-Za-zÁÉÍÓÚáéíóúÑñÜü]+( [A-Za-zÁÉÍÓÚáéíóúÑñÜü]+)*$/.test(texto) && texto.length >= 2 && texto.length <= 40;
  }

  documentoValido(tipo: string, numero: string): boolean {
    if (tipo === 'CC' || tipo === 'TI') {
      return /^[0-9]{6,10}$/.test(numero);
    }
    return /^[A-Za-z0-9]{5,15}$/.test(numero);
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

  cargarInfractores(): void {
    this.http.get<any[]>(this.urlBase + '/infractor/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.infractores = lista;
        this.cambios.markForCheck();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarParejas(): void {
    this.http.get<any[]>(this.urlBase + '/eventoInfractor/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.parejas = lista;
        this.cambios.markForCheck();
      },
      error: (err) => this.manejarError(err)
    });
  }

  registrarColado(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (this.nuevoColado.idEstacion === null || this.nuevoColado.idModalidad === null ||
      !this.nuevoColado.fecha || !this.nuevoColado.hora) {
      this.mensajeError = 'Falta llenar la estación, la modalidad, la fecha o la hora.';
      return;
    }
    if (this.nuevoColado.fecha > this.fechaHoy) {
      this.mensajeError = 'La fecha no puede ser posterior a hoy.';
      return;
    }
    if (!Number.isInteger(this.nuevoColado.numeroPersonas) || this.nuevoColado.numeroPersonas < 1) {
      this.mensajeError = 'El número de personas debe ser un número entero, mínimo 1.';
      return;
    }

    const tarifa = this.tarifas.find(t =>
      this.nuevoColado.fecha >= t.fechaInicio && (!t.fechaFin || this.nuevoColado.fecha <= t.fechaFin)
    );
    if (!tarifa) {
      this.mensajeError = 'No hay una tarifa vigente para esa fecha.';
      return;
    }

    const parametros = new HttpParams()
      .set('fecha', this.nuevoColado.fecha)
      .set('hora', this.nuevoColado.hora)
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
        this.nuevoColado = { idEstacion: null, idModalidad: null, numeroPersonas: 1, fecha: '', hora: '' };
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

  registrarInfractor(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    const tipo = this.nuevoInfractor.tipoDocumento;
    const numero = this.nuevoInfractor.numeroDocumento.trim();
    const nombre = this.nuevoInfractor.nombre.trim();
    const apellido = this.nuevoInfractor.apellido.trim();

    if (this.nuevoInfractor.idEvento === null || !tipo || !numero || !nombre || !apellido) {
      this.mensajeError = 'Falta escoger el colado o llenar el documento, el nombre o el apellido.';
      return;
    }
    if (!this.documentoValido(tipo, numero)) {
      this.mensajeError = 'El número de documento no es válido. CC y TI: solo números (6 a 10). CE y PAS: letras y números (5 a 15), sin espacios.';
      return;
    }
    if (!this.nombreValido(nombre) || !this.nombreValido(apellido)) {
      this.mensajeError = 'El nombre y el apellido solo pueden tener letras (de 2 a 40), sin números ni símbolos.';
      return;
    }

    const evento = this.eventoEscogido();
    if (evento && this.cantidadIdentificados(evento.id) >= evento.numeroPersonas) {
      this.mensajeError = 'Ya identificaste a todas las personas de ese colado (' + evento.numeroPersonas + ').';
      return;
    }

    const existente = this.infractores.find(i => i.tipoDocumento === tipo && i.numeroDocumento === numero);
    if (existente) {
      const repetido = this.parejas.find(p => p.idEvento === this.nuevoInfractor.idEvento && p.idInfractor === existente.id);
      if (repetido) {
        this.mensajeError = 'Esa persona ya está registrada en este colado.';
        return;
      }
      this.asociarInfractor(existente.id, 'La persona ya estaba registrada. Se agregó a este colado.');
      return;
    }

    const parametros = new HttpParams()
      .set('tipoDocumento', tipo)
      .set('numeroDocumento', numero)
      .set('nombre', nombre)
      .set('apellido', apellido);

    this.http.post(this.urlBase + '/infractor/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.http.get<any[]>(this.urlBase + '/infractor/todos', { headers: this.encabezados() }).subscribe({
          next: (lista) => {
            this.infractores = lista;
            const nuevo = lista.find(i => i.tipoDocumento === tipo && i.numeroDocumento === numero);
            if (nuevo) {
              this.asociarInfractor(nuevo.id, 'Infractor registrado en el colado.');
            }
          },
          error: (err) => this.manejarError(err)
        });
      },
      error: (err) => this.manejarError(err)
    });
  }

  asociarInfractor(idInfractor: number, texto: string): void {
    const parametros = new HttpParams()
      .set('idEvento', String(this.nuevoInfractor.idEvento))
      .set('idInfractor', String(idInfractor));

    this.http.post(this.urlBase + '/eventoInfractor/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevoInfractor = {
          idEvento: this.nuevoInfractor.idEvento,
          tipoDocumento: '',
          numeroDocumento: '',
          nombre: '',
          apellido: ''
        };
        this.mostrarExito(texto);
        this.cargarParejas();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cerrarSesion(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
