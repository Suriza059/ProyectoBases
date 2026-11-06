import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  standalone: false,
  styleUrl: './admin.css',
  templateUrl: './admin.html',
})
export class Admin implements OnInit {

  urlBase: string = 'http://localhost:8080';

  seccionActual: string = 'troncales';

  secciones = [
    { clave: 'troncales', nombre: 'Troncales' },
    { clave: 'estaciones', nombre: 'Estaciones' },
    { clave: 'modalidades', nombre: 'Modalidades' },
    { clave: 'tarifas', nombre: 'Tarifas' },
    { clave: 'guardas', nombre: 'Guardas' },
    { clave: 'usuarios', nombre: 'Usuarios' },
    { clave: 'comparendos', nombre: 'Comparendos y pagos' },
    { clave: 'cambios', nombre: 'Registro de cambios' }
  ];

  mensajeExito: string = '';
  mensajeError: string = '';

  fechaHoy: string = '';

  troncales: any[] = [];
  estaciones: any[] = [];
  modalidades: any[] = [];
  tarifas: any[] = [];
  guardas: any[] = [];
  usuarios: any[] = [];
  registroCambios: any[] = [];
  eventos: any[] = [];
  infractores: any[] = [];
  parejas: any[] = [];
  comparendos: any[] = [];
  pagos: any[] = [];
  usuariosGuarda: any[] = [];

  confirmacion = {
    visible: false,
    texto: '',
    accion: null as (() => void) | null
  };

  nuevaTroncal = {
    nombre: ''
  };

  troncalEditandoId: number | null = null;
  nombreEditado: string = '';

  opcionesActiva: string[] = ['Si', 'No'];

  nuevaEstacion = {
    nombre: '',
    activa: 'Si',
    idTroncal: null as number | null
  };

  estacionEditandoId: number | null = null;
  estacionEditada = {
    nombre: '',
    activa: 'Si',
    idTroncal: null as number | null
  };

  nuevaModalidad = {
    nombre: ''
  };

  modalidadEditandoId: number | null = null;
  nombreModalidadEditado: string = '';

  nuevaTarifa = {
    fechaInicio: '',
    fechaFin: '',
    valor: null as number | null
  };

  tarifaEditandoId: number | null = null;
  tarifaEditada = {
    fechaInicio: '',
    fechaFin: '',
    valor: null as number | null
  };

  turnos: string[] = ['Mañana', 'Tarde', 'Noche'];
  cargos: string[] = ['Guarda', 'Supervisor'];

  nuevaGuarda = {
    nombre: '',
    apellido: '',
    turno: '',
    cargo: '',
    activo: 'Si',
    idEstacion: null as number | null,
    idSupervisor: null as number | null
  };

  guardaEditandoId: number | null = null;
  guardaEditada = {
    nombre: '',
    apellido: '',
    turno: '',
    cargo: '',
    activo: 'Si',
    idEstacion: null as number | null,
    idSupervisor: null as number | null
  };

  roles: string[] = ['Administrador', 'Guarda', 'Analista'];

  nuevoUsuario = {
    rol: '',
    nombreUsuario: '',
    contrasena: '',
    idGuarda: null as number | null
  };

  usuarioEditandoId: number | null = null;
  usuarioEditado = {
    rol: '',
    nombreUsuario: '',
    contrasena: ''
  };

  nuevoComparendo = {
    idPareja: null as number | null,
    fecha: '',
    valor: null as number | null
  };

  comparendoPagandoId: number | null = null;
  nuevoPago = {
    numeroRecibo: '',
    fecha: ''
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

    this.cargarTroncales();
    this.cargarEstaciones();
    this.cargarModalidades();
    this.cargarTarifas();
    this.cargarGuardas();
    this.cargarUsuarios();
    this.cargarRegistroCambios();
    this.cargarEventos();
    this.cargarInfractores();
    this.cargarParejas();
    this.cargarComparendos();
    this.cargarPagos();
    this.cargarUsuariosGuarda();
  }

  preguntar(texto: string, accion: () => void): void {
    this.confirmacion = { visible: true, texto: texto, accion: accion };
    this.cambios.detectChanges();
  }

  aceptarConfirmacion(): void {
    const accion = this.confirmacion.accion;
    this.confirmacion = { visible: false, texto: '', accion: null };
    if (accion) {
      accion();
    }
  }

  cancelarConfirmacion(): void {
    this.confirmacion = { visible: false, texto: '', accion: null };
  }

  private encabezados(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({ Authorization: 'Bearer ' + token });
  }

  obtenerFechaHoy(): string {
    const hoy = new Date();
    const anio = hoy.getFullYear();
    const mes = String(hoy.getMonth() + 1).padStart(2, '0');
    const dia = String(hoy.getDate()).padStart(2, '0');
    return anio + '-' + mes + '-' + dia;
  }

  mostrarExito(texto: string): void {
    this.mensajeExito = texto;
    this.cambios.detectChanges();
    setTimeout(() => {
      this.mensajeExito = '';
      this.cambios.detectChanges();
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
    this.cambios.detectChanges();
  }

  cambiarSeccion(clave: string): void {
    this.seccionActual = clave;
    this.mensajeExito = '';
    this.mensajeError = '';
    this.troncalEditandoId = null;
    this.estacionEditandoId = null;
    this.modalidadEditandoId = null;
    this.tarifaEditandoId = null;
    this.guardaEditandoId = null;
    this.usuarioEditandoId = null;
    this.comparendoPagandoId = null;
  }

  nombreTroncal(id: number): string {
    const troncal = this.troncales.find(t => t.id === id);
    return troncal ? troncal.nombre : 'Troncal ' + id;
  }

  estaActiva(estacion: any): boolean {
    return String(estacion.activa).toLowerCase() === 'si';
  }

  cargarTroncales(): void {
    this.http.get<any[]>(this.urlBase + '/troncal/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.troncales = lista.sort((a, b) => a.id - b.id);
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  crearTroncal(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.nuevaTroncal.nombre.trim()) {
      this.mensajeError = 'Falta escribir el nombre de la troncal.';
      return;
    }

    const parametros = new HttpParams()
      .set('nombre', this.nuevaTroncal.nombre.trim());

    this.http.post(this.urlBase + '/troncal/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevaTroncal = { nombre: '' };
        this.mostrarExito('Troncal creada.');
        this.cargarTroncales();
      },
      error: (err) => this.manejarError(err)
    });
  }

  empezarEdicion(troncal: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';
    this.troncalEditandoId = troncal.id;
    this.nombreEditado = troncal.nombre;
  }

  cancelarEdicion(): void {
    this.troncalEditandoId = null;
    this.nombreEditado = '';
  }

  guardarEdicion(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.nombreEditado.trim()) {
      this.mensajeError = 'El nombre de la troncal no puede quedar vacío.';
      return;
    }

    const parametros = new HttpParams()
      .set('nombre', this.nombreEditado.trim());

    this.http.put(this.urlBase + '/troncal/actualizar/' + this.troncalEditandoId, null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.troncalEditandoId = null;
        this.nombreEditado = '';
        this.mostrarExito('Troncal actualizada.');
        this.cargarTroncales();
      },
      error: (err) => this.manejarError(err)
    });
  }

  eliminarTroncal(troncal: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    this.preguntar('¿Seguro que quieres eliminar la troncal "' + troncal.nombre + '"?', () => {
      this.http.delete(this.urlBase + '/troncal/eliminar/' + troncal.id, {
        headers: this.encabezados(),
        responseType: 'text'
      }).subscribe({
        next: () => {
          this.mostrarExito('Troncal eliminada.');
          this.cargarTroncales();
        },
        error: (err) => this.manejarError(err)
      });
    });
  }

  cargarEstaciones(): void {
    this.http.get<any[]>(this.urlBase + '/estacion/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.estaciones = lista.sort((a, b) => a.id - b.id);
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  crearEstacion(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.nuevaEstacion.nombre.trim() || this.nuevaEstacion.idTroncal === null) {
      this.mensajeError = 'Falta escribir el nombre o escoger la troncal.';
      return;
    }

    const parametros = new HttpParams()
      .set('nombre', this.nuevaEstacion.nombre.trim())
      .set('activa', this.nuevaEstacion.activa)
      .set('idTroncal', String(this.nuevaEstacion.idTroncal));

    this.http.post(this.urlBase + '/estacion/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevaEstacion = { nombre: '', activa: 'Si', idTroncal: null };
        this.mostrarExito('Estación creada.');
        this.cargarEstaciones();
      },
      error: (err) => this.manejarError(err)
    });
  }

  empezarEdicionEstacion(estacion: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';
    this.estacionEditandoId = estacion.id;
    this.estacionEditada = {
      nombre: estacion.nombre,
      activa: this.estaActiva(estacion) ? 'Si' : 'No',
      idTroncal: estacion.idTroncal
    };
  }

  cancelarEdicionEstacion(): void {
    this.estacionEditandoId = null;
  }

  guardarEdicionEstacion(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.estacionEditada.nombre.trim() || this.estacionEditada.idTroncal === null) {
      this.mensajeError = 'Falta el nombre o la troncal de la estación.';
      return;
    }

    const parametros = new HttpParams()
      .set('nombre', this.estacionEditada.nombre.trim())
      .set('activa', this.estacionEditada.activa)
      .set('idTroncal', String(this.estacionEditada.idTroncal));

    this.http.put(this.urlBase + '/estacion/actualizar/' + this.estacionEditandoId, null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.estacionEditandoId = null;
        this.mostrarExito('Estación actualizada.');
        this.cargarEstaciones();
      },
      error: (err) => this.manejarError(err)
    });
  }

  eliminarEstacion(estacion: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    this.preguntar('¿Seguro que quieres eliminar la estación "' + estacion.nombre + '"?', () => {
      this.http.delete(this.urlBase + '/estacion/eliminar/' + estacion.id, {
        headers: this.encabezados(),
        responseType: 'text'
      }).subscribe({
        next: () => {
          this.mostrarExito('Estación eliminada.');
          this.cargarEstaciones();
        },
        error: (err) => this.manejarError(err)
      });
    });
  }

  cargarModalidades(): void {
    this.http.get<any[]>(this.urlBase + '/modalidad/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.modalidades = lista.sort((a, b) => a.id - b.id);
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  crearModalidad(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.nuevaModalidad.nombre.trim()) {
      this.mensajeError = 'Falta escribir el nombre de la modalidad.';
      return;
    }

    const parametros = new HttpParams()
      .set('nombre', this.nuevaModalidad.nombre.trim());

    this.http.post(this.urlBase + '/modalidad/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevaModalidad = { nombre: '' };
        this.mostrarExito('Modalidad creada.');
        this.cargarModalidades();
      },
      error: (err) => this.manejarError(err)
    });
  }

  empezarEdicionModalidad(modalidad: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';
    this.modalidadEditandoId = modalidad.id;
    this.nombreModalidadEditado = modalidad.nombre;
  }

  cancelarEdicionModalidad(): void {
    this.modalidadEditandoId = null;
    this.nombreModalidadEditado = '';
  }

  guardarEdicionModalidad(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.nombreModalidadEditado.trim()) {
      this.mensajeError = 'El nombre de la modalidad no puede quedar vacío.';
      return;
    }

    const parametros = new HttpParams()
      .set('nombre', this.nombreModalidadEditado.trim());

    this.http.put(this.urlBase + '/modalidad/actualizar/' + this.modalidadEditandoId, null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.modalidadEditandoId = null;
        this.nombreModalidadEditado = '';
        this.mostrarExito('Modalidad actualizada.');
        this.cargarModalidades();
      },
      error: (err) => this.manejarError(err)
    });
  }

  eliminarModalidad(modalidad: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    this.preguntar('¿Seguro que quieres eliminar la modalidad "' + modalidad.nombre + '"?', () => {
      this.http.delete(this.urlBase + '/modalidad/eliminar/' + modalidad.id, {
        headers: this.encabezados(),
        responseType: 'text'
      }).subscribe({
        next: () => {
          this.mostrarExito('Modalidad eliminada.');
          this.cargarModalidades();
        },
        error: (err) => this.manejarError(err)
      });
    });
  }

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

  cargarTarifas(): void {
    this.http.get<any[]>(this.urlBase + '/tarifa/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.tarifas = lista.sort((a, b) => a.id - b.id);
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  hayCruceDeFechas(inicio: string, fin: string, idIgnorar: number | null): boolean {
    const finNuevo = fin ? fin : '9999-12-31';
    for (const tarifa of this.tarifas) {
      if (tarifa.id === idIgnorar) {
        continue;
      }
      const finTarifa = tarifa.fechaFin ? tarifa.fechaFin : '9999-12-31';
      if (inicio <= finTarifa && finNuevo >= tarifa.fechaInicio) {
        return true;
      }
    }
    return false;
  }

  crearTarifa(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.nuevaTarifa.fechaInicio || this.nuevaTarifa.valor === null) {
      this.mensajeError = 'Falta llenar la fecha de inicio o el valor.';
      return;
    }
    if (this.nuevaTarifa.fechaFin && this.nuevaTarifa.fechaFin < this.nuevaTarifa.fechaInicio) {
      this.mensajeError = 'La fecha final no puede ser anterior a la fecha de inicio.';
      return;
    }
    if (!Number.isInteger(this.nuevaTarifa.valor) || this.nuevaTarifa.valor < 1) {
      this.mensajeError = 'El valor debe ser un número entero mayor que cero.';
      return;
    }
    if (this.hayCruceDeFechas(this.nuevaTarifa.fechaInicio, this.nuevaTarifa.fechaFin, null)) {
      this.mensajeError = 'Esas fechas se cruzan con otra tarifa. Si hay una tarifa vigente sin fecha final, primero ponle fecha final.';
      return;
    }

    let parametros = new HttpParams()
      .set('fechaInicio', this.nuevaTarifa.fechaInicio)
      .set('valor', String(this.nuevaTarifa.valor));

    if (this.nuevaTarifa.fechaFin) {
      parametros = parametros.set('fechaFin', this.nuevaTarifa.fechaFin);
    }

    this.http.post(this.urlBase + '/tarifa/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevaTarifa = { fechaInicio: '', fechaFin: '', valor: null };
        this.mostrarExito('Tarifa creada.');
        this.cargarTarifas();
      },
      error: (err) => this.manejarError(err)
    });
  }

  empezarEdicionTarifa(tarifa: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';
    this.tarifaEditandoId = tarifa.id;
    this.tarifaEditada = {
      fechaInicio: tarifa.fechaInicio,
      fechaFin: tarifa.fechaFin ? tarifa.fechaFin : '',
      valor: tarifa.valor
    };
  }

  cancelarEdicionTarifa(): void {
    this.tarifaEditandoId = null;
  }

  guardarEdicionTarifa(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.tarifaEditada.fechaInicio || this.tarifaEditada.valor === null) {
      this.mensajeError = 'Falta llenar la fecha de inicio o el valor.';
      return;
    }
    if (this.tarifaEditada.fechaFin && this.tarifaEditada.fechaFin < this.tarifaEditada.fechaInicio) {
      this.mensajeError = 'La fecha final no puede ser anterior a la fecha de inicio.';
      return;
    }
    if (!Number.isInteger(this.tarifaEditada.valor) || this.tarifaEditada.valor < 1) {
      this.mensajeError = 'El valor debe ser un número entero mayor que cero.';
      return;
    }
    if (this.hayCruceDeFechas(this.tarifaEditada.fechaInicio, this.tarifaEditada.fechaFin, this.tarifaEditandoId)) {
      this.mensajeError = 'Esas fechas se cruzan con otra tarifa que ya existe.';
      return;
    }

    let parametros = new HttpParams()
      .set('fechaInicio', this.tarifaEditada.fechaInicio)
      .set('valor', String(this.tarifaEditada.valor));

    if (this.tarifaEditada.fechaFin) {
      parametros = parametros.set('fechaFin', this.tarifaEditada.fechaFin);
    }

    this.http.put(this.urlBase + '/tarifa/actualizar/' + this.tarifaEditandoId, null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.tarifaEditandoId = null;
        this.mostrarExito('Tarifa actualizada.');
        this.cargarTarifas();
      },
      error: (err) => this.manejarError(err)
    });
  }

  eliminarTarifa(tarifa: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    this.preguntar('¿Seguro que quieres eliminar esta tarifa?', () => {
      this.http.delete(this.urlBase + '/tarifa/eliminar/' + tarifa.id, {
        headers: this.encabezados(),
        responseType: 'text'
      }).subscribe({
        next: () => {
          this.mostrarExito('Tarifa eliminada.');
          this.cargarTarifas();
        },
        error: (err) => this.manejarError(err)
      });
    });
  }

  nombreEstacion(id: number): string {
    const estacion = this.estaciones.find(e => e.id === id);
    return estacion ? estacion.nombre : 'Estación ' + id;
  }

  nombreGuarda(id: number): string {
    const guarda = this.guardas.find(g => g.id === id);
    return guarda ? guarda.nombre + ' ' + guarda.apellido : 'Guarda ' + id;
  }

  guardaEstaActivo(guarda: any): boolean {
    return String(guarda.activo).toLowerCase() === 'si';
  }

  supervisores(idIgnorar: number | null): any[] {
    return this.guardas.filter(g => g.cargo === 'Supervisor' && g.id !== idIgnorar);
  }

  cargarGuardas(): void {
    this.http.get<any[]>(this.urlBase + '/guarda/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.guardas = lista.sort((a, b) => a.id - b.id);
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  crearGuarda(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.nuevaGuarda.nombre.trim() || !this.nuevaGuarda.apellido.trim() ||
      !this.nuevaGuarda.turno || !this.nuevaGuarda.cargo || this.nuevaGuarda.idEstacion === null) {
      this.mensajeError = 'Falta llenar el nombre, el apellido, el turno, el cargo o la estación.';
      return;
    }

    let parametros = new HttpParams()
      .set('nombre', this.nuevaGuarda.nombre.trim())
      .set('apellido', this.nuevaGuarda.apellido.trim())
      .set('turno', this.nuevaGuarda.turno)
      .set('activo', this.nuevaGuarda.activo)
      .set('cargo', this.nuevaGuarda.cargo)
      .set('idEstacion', String(this.nuevaGuarda.idEstacion));

    if (this.nuevaGuarda.idSupervisor !== null) {
      parametros = parametros.set('idSupervisor', String(this.nuevaGuarda.idSupervisor));
    }

    this.http.post(this.urlBase + '/guarda/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevaGuarda = { nombre: '', apellido: '', turno: '', cargo: '', activo: 'Si', idEstacion: null, idSupervisor: null };
        this.mostrarExito('Guarda creado.');
        this.cargarGuardas();
      },
      error: (err) => this.manejarError(err)
    });
  }

  empezarEdicionGuarda(guarda: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';
    this.guardaEditandoId = guarda.id;
    this.guardaEditada = {
      nombre: guarda.nombre,
      apellido: guarda.apellido,
      turno: guarda.turno,
      cargo: guarda.cargo,
      activo: this.guardaEstaActivo(guarda) ? 'Si' : 'No',
      idEstacion: guarda.idEstacion,
      idSupervisor: guarda.idSupervisor ? guarda.idSupervisor : null
    };
  }

  cancelarEdicionGuarda(): void {
    this.guardaEditandoId = null;
  }

  guardarEdicionGuarda(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.guardaEditada.nombre.trim() || !this.guardaEditada.apellido.trim() ||
      !this.guardaEditada.turno || !this.guardaEditada.cargo || this.guardaEditada.idEstacion === null) {
      this.mensajeError = 'Falta llenar el nombre, el apellido, el turno, el cargo o la estación.';
      return;
    }
    if (this.guardaEditada.idSupervisor === this.guardaEditandoId) {
      this.mensajeError = 'Un guarda no puede ser su propio supervisor.';
      return;
    }

    let parametros = new HttpParams()
      .set('nombre', this.guardaEditada.nombre.trim())
      .set('apellido', this.guardaEditada.apellido.trim())
      .set('turno', this.guardaEditada.turno)
      .set('activo', this.guardaEditada.activo)
      .set('cargo', this.guardaEditada.cargo)
      .set('idEstacion', String(this.guardaEditada.idEstacion));

    if (this.guardaEditada.idSupervisor !== null) {
      parametros = parametros.set('idSupervisor', String(this.guardaEditada.idSupervisor));
    }

    this.http.put(this.urlBase + '/guarda/actualizar/' + this.guardaEditandoId, null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.guardaEditandoId = null;
        this.mostrarExito('Guarda actualizado.');
        this.cargarGuardas();
      },
      error: (err) => this.manejarError(err)
    });
  }

  eliminarGuarda(guarda: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    this.preguntar('¿Seguro que quieres eliminar al guarda "' + guarda.nombre + ' ' + guarda.apellido + '"?', () => {
      this.http.delete(this.urlBase + '/guarda/eliminar/' + guarda.id, {
        headers: this.encabezados(),
        responseType: 'text'
      }).subscribe({
        next: () => {
          this.mostrarExito('Guarda eliminado.');
          this.cargarGuardas();
        },
        error: (err) => this.manejarError(err)
      });
    });
  }

  nombreDeUsuario(id: number): string {
    const usuario = this.usuarios.find(u => u.id === id);
    return usuario ? usuario.nombreUsuario : 'Usuario ' + id;
  }

  nombreUsuarioValido(nombre: string): boolean {
    return /^[a-zA-Z][a-zA-Z0-9._]{3,19}$/.test(nombre);
  }

  contrasenaValida(contrasena: string): boolean {
    return /^\S{6,30}$/.test(contrasena);
  }

  cargarUsuarios(): void {
    this.http.get<any[]>(this.urlBase + '/usuario/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.usuarios = lista.sort((a, b) => a.id - b.id);
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarUsuariosGuarda(): void {
    this.http.get<any[]>(this.urlBase + '/usuarioGuarda/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.usuariosGuarda = lista;
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  guardaDeUsuario(idUsuario: number): any {
    const union = this.usuariosGuarda.find(u => u.idUsuario === idUsuario);
    if (!union) {
      return null;
    }
    return this.guardas.find(g => g.id === union.idGuarda);
  }

  guardasSinUsuario(): any[] {
    return this.guardas.filter(g => this.guardaEstaActivo(g) && !this.usuariosGuarda.find(u => u.idGuarda === g.id));
  }

  asociarGuarda(nombreUsuario: string, idGuarda: number): void {
    this.http.get<any[]>(this.urlBase + '/usuario/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.usuarios = lista.sort((a, b) => a.id - b.id);
        const usuario = lista.find(u => u.nombreUsuario === nombreUsuario);
        if (!usuario) {
          return;
        }

        const parametros = new HttpParams()
          .set('idUsuario', String(usuario.id))
          .set('fechaActivacion', this.obtenerFechaHoy())
          .set('idGuarda', String(idGuarda));

        this.http.post(this.urlBase + '/usuarioGuarda/crear', null, {
          headers: this.encabezados(),
          params: parametros,
          responseType: 'text'
        }).subscribe({
          next: () => {
            this.mostrarExito('Usuario creado y asociado a su guarda.');
            this.cargarUsuariosGuarda();
          },
          error: (err) => this.manejarError(err)
        });
      },
      error: (err) => this.manejarError(err)
    });
  }

  crearUsuario(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (this.nuevoUsuario.nombreUsuario === '' || this.nuevoUsuario.contrasena === '' || !this.nuevoUsuario.rol) {
      this.mensajeError = 'Falta escribir el nombre de usuario, la contraseña o escoger el rol.';
      return;
    }
    if (this.nuevoUsuario.rol === 'Guarda' && this.nuevoUsuario.idGuarda === null) {
      this.mensajeError = 'Escoge a qué guarda le pertenece esta cuenta.';
      return;
    }
    if (!this.nombreUsuarioValido(this.nuevoUsuario.nombreUsuario)) {
      this.mensajeError = 'El nombre de usuario debe tener entre 4 y 20 caracteres, empezar con una letra y usar solo letras, números, punto o guion bajo, sin espacios.';
      return;
    }
    if (!this.contrasenaValida(this.nuevoUsuario.contrasena)) {
      this.mensajeError = 'La contraseña debe tener entre 6 y 30 caracteres y no puede tener espacios.';
      return;
    }

    const parametros = new HttpParams()
      .set('rol', this.nuevoUsuario.rol)
      .set('nombreUsuario', this.nuevoUsuario.nombreUsuario)
      .set('contrasena', this.nuevoUsuario.contrasena);

    this.http.post(this.urlBase + '/usuario/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        const nombre = this.nuevoUsuario.nombreUsuario;
        const idGuarda = this.nuevoUsuario.idGuarda;
        const esGuarda = this.nuevoUsuario.rol === 'Guarda';
        this.nuevoUsuario = { rol: '', nombreUsuario: '', contrasena: '', idGuarda: null };
        if (esGuarda && idGuarda !== null) {
          this.asociarGuarda(nombre, idGuarda);
        } else {
          this.mostrarExito('Usuario creado.');
          this.cargarUsuarios();
        }
      },
      error: (err) => this.manejarError(err)
    });
  }

  empezarEdicionUsuario(usuario: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';
    this.usuarioEditandoId = usuario.id;
    this.usuarioEditado = {
      rol: usuario.rol,
      nombreUsuario: usuario.nombreUsuario,
      contrasena: ''
    };
  }

  cancelarEdicionUsuario(): void {
    this.usuarioEditandoId = null;
  }

  guardarEdicionUsuario(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (this.usuarioEditado.nombreUsuario === '' || !this.usuarioEditado.rol) {
      this.mensajeError = 'Falta el nombre de usuario o el rol.';
      return;
    }
    if (!this.nombreUsuarioValido(this.usuarioEditado.nombreUsuario)) {
      this.mensajeError = 'El nombre de usuario debe tener entre 4 y 20 caracteres, empezar con una letra y usar solo letras, números, punto o guion bajo, sin espacios.';
      return;
    }
    if (this.usuarioEditado.contrasena !== '' && !this.contrasenaValida(this.usuarioEditado.contrasena)) {
      this.mensajeError = 'La contraseña debe tener entre 6 y 30 caracteres y no puede tener espacios.';
      return;
    }
    if (this.usuarioEditandoId !== null && this.guardaDeUsuario(this.usuarioEditandoId) && this.usuarioEditado.rol !== 'Guarda') {
      this.mensajeError = 'Esta cuenta está asociada a un guarda, por eso su rol debe seguir siendo Guarda.';
      return;
    }

    let parametros = new HttpParams()
      .set('rol', this.usuarioEditado.rol)
      .set('nombreUsuario', this.usuarioEditado.nombreUsuario);

    if (this.usuarioEditado.contrasena !== '') {
      parametros = parametros.set('contrasena', this.usuarioEditado.contrasena);
    }

    this.http.put(this.urlBase + '/usuario/actualizar/' + this.usuarioEditandoId, null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.usuarioEditandoId = null;
        this.mostrarExito('Usuario actualizado.');
        this.cargarUsuarios();
      },
      error: (err) => this.manejarError(err)
    });
  }

  eliminarUsuario(usuario: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    this.preguntar('¿Seguro que quieres eliminar al usuario "' + usuario.nombreUsuario + '"?', () => {
      if (this.guardaDeUsuario(usuario.id)) {
        this.http.delete(this.urlBase + '/usuarioGuarda/eliminar/' + usuario.id, {
          headers: this.encabezados(),
          responseType: 'text'
        }).subscribe({
          next: () => this.borrarUsuario(usuario),
          error: (err) => this.manejarError(err)
        });
      } else {
        this.borrarUsuario(usuario);
      }
    });
  }

  borrarUsuario(usuario: any): void {
    this.http.delete(this.urlBase + '/usuario/eliminar/' + usuario.id, {
      headers: this.encabezados(),
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.mostrarExito('Usuario eliminado.');
        this.cargarUsuarios();
        this.cargarUsuariosGuarda();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarEventos(): void {
    this.http.get<any[]>(this.urlBase + '/evento/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.eventos = lista;
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarInfractores(): void {
    this.http.get<any[]>(this.urlBase + '/infractor/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.infractores = lista;
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarParejas(): void {
    this.http.get<any[]>(this.urlBase + '/eventoInfractor/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.parejas = lista.sort((a, b) => b.id - a.id);
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarComparendos(): void {
    this.http.get<any[]>(this.urlBase + '/comparendo/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.comparendos = lista.sort((a, b) => b.id - a.id);
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cargarPagos(): void {
    this.http.get<any[]>(this.urlBase + '/pago/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.pagos = lista;
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  textoPareja(idPareja: number): string {
    const pareja = this.parejas.find(p => p.id === idPareja);
    if (!pareja) {
      return 'Registro ' + idPareja;
    }
    const evento = this.eventos.find(e => e.id === pareja.idEvento);
    const infractor = this.infractores.find(i => i.id === pareja.idInfractor);
    let texto = '';
    if (infractor) {
      texto = infractor.nombre + ' ' + infractor.apellido + ' (' + infractor.tipoDocumento + ' ' + infractor.numeroDocumento + ')';
    } else {
      texto = 'Infractor ' + pareja.idInfractor;
    }
    if (evento) {
      texto = texto + ' · Colado #' + evento.id + ' en ' + this.nombreEstacion(evento.idEstacion) + ' el ' + this.formatearFecha(evento.fecha);
    }
    return texto;
  }

  parejasSinComparendo(): any[] {
    return this.parejas.filter(p => !this.comparendos.find(c => c.idEventoInfractor === p.id));
  }

  pagoDeComparendo(idComparendo: number): any {
    return this.pagos.find(p => p.idComparendo === idComparendo);
  }

  crearComparendo(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (this.nuevoComparendo.idPareja === null || !this.nuevoComparendo.fecha || this.nuevoComparendo.valor === null) {
      this.mensajeError = 'Falta escoger el infractor, la fecha o el valor.';
      return;
    }
    if (!Number.isInteger(this.nuevoComparendo.valor) || this.nuevoComparendo.valor < 1) {
      this.mensajeError = 'El valor debe ser un número entero mayor que cero.';
      return;
    }
    if (this.nuevoComparendo.fecha > this.fechaHoy) {
      this.mensajeError = 'La fecha no puede ser posterior a hoy.';
      return;
    }

    const pareja = this.parejas.find(p => p.id === this.nuevoComparendo.idPareja);
    const evento = pareja ? this.eventos.find(e => e.id === pareja.idEvento) : null;
    if (evento && this.nuevoComparendo.fecha < evento.fecha.substring(0, 10)) {
      this.mensajeError = 'El comparendo no puede ser de antes del colado.';
      return;
    }

    const parametros = new HttpParams()
      .set('fecha', this.nuevoComparendo.fecha)
      .set('valor', String(this.nuevoComparendo.valor))
      .set('estado', 'Pendiente')
      .set('idEventoInfractor', String(this.nuevoComparendo.idPareja));

    this.http.post(this.urlBase + '/comparendo/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevoComparendo = { idPareja: null, fecha: '', valor: null };
        this.mostrarExito('Comparendo creado en estado Pendiente.');
        this.cargarComparendos();
      },
      error: (err) => this.manejarError(err)
    });
  }

  empezarPago(comparendo: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';
    this.comparendoPagandoId = comparendo.id;
    this.nuevoPago = { numeroRecibo: '', fecha: '' };
  }

  cancelarPago(): void {
    this.comparendoPagandoId = null;
  }

  registrarPago(comparendo: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    const recibo = this.nuevoPago.numeroRecibo.trim();

    if (!recibo || !this.nuevoPago.fecha) {
      this.mensajeError = 'Falta escribir el número de recibo o la fecha del pago.';
      return;
    }
    if (!/^[A-Za-z0-9-]{3,20}$/.test(recibo)) {
      this.mensajeError = 'El número de recibo solo puede tener letras, números o guiones (de 3 a 20), sin espacios.';
      return;
    }
    if (this.pagos.find(p => p.numeroRecibo === recibo)) {
      this.mensajeError = 'Ya existe un pago con ese número de recibo.';
      return;
    }
    if (this.nuevoPago.fecha < comparendo.fecha.substring(0, 10)) {
      this.mensajeError = 'El pago no puede ser de antes del comparendo.';
      return;
    }
    if (this.nuevoPago.fecha > this.fechaHoy) {
      this.mensajeError = 'La fecha del pago no puede ser posterior a hoy.';
      return;
    }
    if (comparendo.estado !== 'Pendiente' || this.pagoDeComparendo(comparendo.id)) {
      this.mensajeError = 'Solo se puede pagar un comparendo pendiente y sin pago.';
      return;
    }

    const parametros = new HttpParams()
      .set('valor', String(comparendo.valor))
      .set('numeroRecibo', recibo)
      .set('fecha', this.nuevoPago.fecha)
      .set('idComparendo', String(comparendo.id));

    this.http.post(this.urlBase + '/pago/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.comparendoPagandoId = null;
        this.cambiarEstadoComparendo(comparendo, 'Pagado', 'Pago registrado. El comparendo quedó Pagado.');
        this.cargarPagos();
      },
      error: (err) => this.manejarError(err)
    });
  }

  anularComparendo(comparendo: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (comparendo.estado !== 'Pendiente') {
      this.mensajeError = 'Solo se puede anular un comparendo pendiente.';
      return;
    }
    this.preguntar('¿Seguro que quieres anular el comparendo ' + comparendo.id + '? Después no se puede cambiar.', () => {
      this.cambiarEstadoComparendo(comparendo, 'Anulado', 'Comparendo anulado.');
    });
  }

  cambiarEstadoComparendo(comparendo: any, estadoNuevo: string, texto: string): void {
    const parametros = new HttpParams()
      .set('fecha', comparendo.fecha.substring(0, 10))
      .set('valor', String(comparendo.valor))
      .set('estado', estadoNuevo)
      .set('idEventoInfractor', String(comparendo.idEventoInfractor));

    this.http.put(this.urlBase + '/comparendo/actualizar/' + comparendo.id, null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.guardarCambio(comparendo.id, 'estado', comparendo.estado, estadoNuevo);
        this.mostrarExito(texto);
        this.cargarComparendos();
      },
      error: (err) => this.manejarError(err)
    });
  }

  guardarCambio(idComparendo: number, campo: string, anterior: string, nuevo: string): void {
    const idUsuario = localStorage.getItem('idUsuario');
    if (!idUsuario) {
      return;
    }

    const ahora = new Date();
    const hora = String(ahora.getHours()).padStart(2, '0') + ':' + String(ahora.getMinutes()).padStart(2, '0') + ':' + String(ahora.getSeconds()).padStart(2, '0');

    const parametros = new HttpParams()
      .set('fecha', this.obtenerFechaHoy() + 'T' + hora)
      .set('campoModificado', campo)
      .set('valorAnterior', anterior)
      .set('valorNuevo', nuevo)
      .set('idUsuario', idUsuario)
      .set('idComparendo', String(idComparendo));

    this.http.post(this.urlBase + '/registroCambios/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => this.cargarRegistroCambios(),
      error: (err) => this.manejarError(err)
    });
  }

  formatearFechaHora(fecha: string): string {
    if (!fecha) {
      return '';
    }
    const hora = fecha.length >= 16 ? fecha.substring(11, 16) : '';
    return (this.formatearFecha(fecha) + ' ' + hora).trim();
  }

  cargarRegistroCambios(): void {
    this.http.get<any[]>(this.urlBase + '/registroCambios/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.registroCambios = lista.sort((a, b) => b.id - a.id);
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
