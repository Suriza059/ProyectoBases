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

  // Dirección del back
  urlBase: string = 'http://localhost:8080';

  // Sección que se está viendo
  seccionActual: string = 'troncales';

  secciones = [
    { clave: 'troncales', nombre: 'Troncales' },
    { clave: 'estaciones', nombre: 'Estaciones' },
    { clave: 'modalidades', nombre: 'Modalidades' },
    { clave: 'tarifas', nombre: 'Tarifas' },
    { clave: 'guardas', nombre: 'Guardas' },
    { clave: 'usuarios', nombre: 'Usuarios' },
    { clave: 'cambios', nombre: 'Registro de cambios' }
  ];

  // Mensajes para el administrador
  mensajeExito: string = '';
  mensajeError: string = '';

  // Listas que vienen del back
  troncales: any[] = [];
  estaciones: any[] = [];
  modalidades: any[] = [];
  tarifas: any[] = [];
  guardas: any[] = [];
  usuarios: any[] = [];
  registroCambios: any[] = [];

  // ---------- Troncales ----------

  // Formulario de crear troncal
  nuevaTroncal = {
    nombre: ''
  };

  // Para editar: id de la troncal que se está editando (null = ninguna) y su nuevo nombre
  troncalEditandoId: number | null = null;
  nombreEditado: string = '';

  // ---------- Estaciones ----------

  // Valores de "activa" (el back escribe "Si" o "No")
  opcionesActiva: string[] = ['Si', 'No'];

  // Formulario de crear estación
  nuevaEstacion = {
    nombre: '',
    activa: 'Si',
    idTroncal: null as number | null
  };

  // Para editar: id de la estación que se está editando (null = ninguna) y sus nuevos datos
  estacionEditandoId: number | null = null;
  estacionEditada = {
    nombre: '',
    activa: 'Si',
    idTroncal: null as number | null
  };

  // ---------- Modalidades ----------

  // Formulario de crear modalidad
  nuevaModalidad = {
    nombre: ''
  };

  // Para editar: id de la modalidad que se está editando (null = ninguna) y su nuevo nombre
  modalidadEditandoId: number | null = null;
  nombreModalidadEditado: string = '';
  // ---------- Tarifas ----------

  // Formulario de crear tarifa
  nuevaTarifa = {
    fechaInicio: '',
    fechaFin: '',
    valor: null as number | null
  };

  // Para editar: id de la tarifa que se está editando (null = ninguna) y sus nuevos datos
  tarifaEditandoId: number | null = null;
  tarifaEditada = {
    fechaInicio: '',
    fechaFin: '',
    valor: null as number | null
  };
  // ---------- Guardas ----------

  // Opciones de las listas desplegables.
  // OJO: confirma con Esteban que estos sean los valores que acepta el back
  turnos: string[] = ['Mañana', 'Tarde', 'Noche'];
  cargos: string[] = ['Guarda', 'Supervisor'];

  // Formulario de crear guarda (el supervisor es opcional)
  nuevaGuarda = {
    nombre: '',
    apellido: '',
    turno: '',
    cargo: '',
    activo: 'Si',
    idEstacion: null as number | null,
    idSupervisor: null as number | null
  };

  // Para editar: id del guarda que se está editando (null = ninguno) y sus nuevos datos
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

  // ---------- Usuarios ----------

  // OJO: confirma con Esteban que estos sean los textos que acepta el back
  // ("Administrador" tiene que ir exactamente así)
  roles: string[] = ['Administrador', 'Guarda', 'Analista'];

  // Formulario de crear usuario
  nuevoUsuario = {
    rol: '',
    nombreUsuario: '',
    contrasena: ''
  };

  // Para editar: id del usuario que se está editando (null = ninguno) y sus nuevos datos
  usuarioEditandoId: number | null = null;
  usuarioEditado = {
    rol: '',
    nombreUsuario: '',
    contrasena: ''
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

    this.cargarTroncales();
    this.cargarEstaciones();
    this.cargarModalidades();
    this.cargarTarifas();
    this.cargarGuardas();
    this.cargarUsuarios();
    this.cargarRegistroCambios();
  }

  // ---------- Ayudas ----------

  // Encabezado con el token para cada llamada al back
  private encabezados(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({ Authorization: 'Bearer ' + token });
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

  }

  // Devuelve el nombre de la troncal a partir de su id
  nombreTroncal(id: number): string {
    const troncal = this.troncales.find(t => t.id === id);
    return troncal ? troncal.nombre : 'Troncal ' + id;
  }

  // Dice si una estación está activa (el back escribe "Si" o "si")
  estaActiva(estacion: any): boolean {
    return String(estacion.activa).toLowerCase() === 'si';
  }

  // ---------- Troncales ----------

  cargarTroncales(): void {
    this.http.get<any[]>(this.urlBase + '/troncal/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        // Se ordenan por id para que no cambien de lugar al editar
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

  // Al darle "Editar", la fila cambia a un campo con el nombre actual
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

    // Pregunta antes de borrar, por si le dio clic sin querer
    if (!confirm('¿Seguro que quieres eliminar la troncal "' + troncal.nombre + '"?')) {
      return;
    }

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
  }

  // ---------- Estaciones ----------

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

  // Al darle "Editar", la fila cambia a campos con los datos actuales
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

    if (!confirm('¿Seguro que quieres eliminar la estación "' + estacion.nombre + '"?')) {
      return;
    }

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
  }

  // ---------- Modalidades ----------

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

  // Al darle "Editar", la fila cambia a un campo con el nombre actual
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

    if (!confirm('¿Seguro que quieres eliminar la modalidad "' + modalidad.nombre + '"?')) {
      return;
    }

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
  // ---------- Tarifas ----------

  cargarTarifas(): void {
    this.http.get<any[]>(this.urlBase + '/tarifa/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        this.tarifas = lista.sort((a, b) => a.id - b.id);
        this.cambios.detectChanges();
      },
      error: (err) => this.manejarError(err)
    });
  }

  // Revisa si las fechas se cruzan con otra tarifa que ya existe.
  // idIgnorar sirve al editar, para no comparar la tarifa consigo misma.
  hayCruceDeFechas(inicio: string, fin: string, idIgnorar: number | null): boolean {
    for (const tarifa of this.tarifas) {
      if (tarifa.id === idIgnorar) {
        continue;
      }
      // Dos rangos se cruzan si cada uno empieza antes de que el otro termine
      if (inicio <= tarifa.fechaFin && fin >= tarifa.fechaInicio) {
        return true;
      }
    }
    return false;
  }

  crearTarifa(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.nuevaTarifa.fechaInicio || !this.nuevaTarifa.fechaFin || this.nuevaTarifa.valor === null) {
      this.mensajeError = 'Falta llenar la fecha de inicio, la fecha final o el valor.';
      return;
    }
    if (this.nuevaTarifa.fechaFin < this.nuevaTarifa.fechaInicio) {
      this.mensajeError = 'La fecha final no puede ser anterior a la fecha de inicio.';
      return;
    }
    if (!Number.isInteger(this.nuevaTarifa.valor) || this.nuevaTarifa.valor < 1) {
      this.mensajeError = 'El valor debe ser un número entero mayor que cero.';
      return;
    }
    if (this.hayCruceDeFechas(this.nuevaTarifa.fechaInicio, this.nuevaTarifa.fechaFin, null)) {
      this.mensajeError = 'Esas fechas se cruzan con otra tarifa que ya existe.';
      return;
    }

    const parametros = new HttpParams()
      .set('fechaInicio', this.nuevaTarifa.fechaInicio)
      .set('fechaFin', this.nuevaTarifa.fechaFin)
      .set('valor', String(this.nuevaTarifa.valor));

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

  // Al darle "Editar", la fila cambia a campos con los datos actuales
  empezarEdicionTarifa(tarifa: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';
    this.tarifaEditandoId = tarifa.id;
    this.tarifaEditada = {
      fechaInicio: tarifa.fechaInicio,
      fechaFin: tarifa.fechaFin,
      valor: tarifa.valor
    };
  }

  cancelarEdicionTarifa(): void {
    this.tarifaEditandoId = null;
  }

  guardarEdicionTarifa(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.tarifaEditada.fechaInicio || !this.tarifaEditada.fechaFin || this.tarifaEditada.valor === null) {
      this.mensajeError = 'Falta llenar la fecha de inicio, la fecha final o el valor.';
      return;
    }
    if (this.tarifaEditada.fechaFin < this.tarifaEditada.fechaInicio) {
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

    const parametros = new HttpParams()
      .set('fechaInicio', this.tarifaEditada.fechaInicio)
      .set('fechaFin', this.tarifaEditada.fechaFin)
      .set('valor', String(this.tarifaEditada.valor));

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

    if (!confirm('¿Seguro que quieres eliminar esta tarifa?')) {
      return;
    }

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
  }

  // ---------- Guardas ----------

  // Devuelve el nombre de la estación a partir de su id
  nombreEstacion(id: number): string {
    const estacion = this.estaciones.find(e => e.id === id);
    return estacion ? estacion.nombre : 'Estación ' + id;
  }

  // Devuelve nombre y apellido del guarda a partir de su id
  nombreGuarda(id: number): string {
    const guarda = this.guardas.find(g => g.id === id);
    return guarda ? guarda.nombre + ' ' + guarda.apellido : 'Guarda ' + id;
  }

  // Dice si un guarda está activo (el back escribe "Si" o "si")
  guardaEstaActivo(guarda: any): boolean {
    return String(guarda.activo).toLowerCase() === 'si';
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

    // El supervisor es opcional: solo se manda si se escogió uno
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

  // Al darle "Editar", la fila cambia a campos con los datos actuales
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

    if (!confirm('¿Seguro que quieres eliminar al guarda "' + guarda.nombre + ' ' + guarda.apellido + '"?')) {
      return;
    }

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
  }

  // ---------- Usuarios ----------

  // Devuelve el nombre de usuario a partir de su id
  nombreDeUsuario(id: number): string {
    const usuario = this.usuarios.find(u => u.id === id);
    return usuario ? usuario.nombreUsuario : 'Usuario ' + id;
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

  crearUsuario(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.nuevoUsuario.nombreUsuario.trim() || !this.nuevoUsuario.contrasena.trim() || !this.nuevoUsuario.rol) {
      this.mensajeError = 'Falta escribir el nombre de usuario, la contraseña o escoger el rol.';
      return;
    }

    const parametros = new HttpParams()
        .set('rol', this.nuevoUsuario.rol)
        .set('nombreUsuario', this.nuevoUsuario.nombreUsuario.trim())
        .set('contrasena', this.nuevoUsuario.contrasena);

    this.http.post(this.urlBase + '/usuario/crear', null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevoUsuario = { rol: '', nombreUsuario: '', contrasena: '' };
        this.mostrarExito('Usuario creado.');
        this.cargarUsuarios();
      },
      error: (err) => this.manejarError(err)
    });
  }

  // Al darle "Editar", la fila cambia a campos con los datos actuales
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

    if (!this.usuarioEditado.nombreUsuario.trim() || !this.usuarioEditado.rol) {
      this.mensajeError = 'Falta el nombre de usuario o el rol.';
      return;
    }

    let parametros = new HttpParams()
        .set('rol', this.usuarioEditado.rol)
        .set('nombreUsuario', this.usuarioEditado.nombreUsuario.trim());

    // La contraseña solo se manda si escribió una nueva
    if (this.usuarioEditado.contrasena.trim()) {
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

    if (!confirm('¿Seguro que quieres eliminar al usuario "' + usuario.nombreUsuario + '"?')) {
      return;
    }

    this.http.delete(this.urlBase + '/usuario/eliminar/' + usuario.id, {
      headers: this.encabezados(),
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.mostrarExito('Usuario eliminado.');
        this.cargarUsuarios();
      },
      error: (err) => this.manejarError(err)
    });
  }

  // ---------- Registro de cambios ----------

  // Convierte una fecha con hora (aaaa-mm-ddThh:mm) en dd/mm/aaaa hh:mm
  formatearFechaHora(fecha: string): string {
    if (!fecha) {
      return '';
    }
    const hora = fecha.length >= 16 ? fecha.substring(11, 16) : '';
    return (this.formatearFecha(fecha) + ' ' + hora).trim();
  }

  cargarRegistroCambios(): void {
    // OJO: confirma en el Swagger que esta sea la ruta
    this.http.get<any[]>(this.urlBase + '/registrocambios/todos', { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        // Del más nuevo al más viejo
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
