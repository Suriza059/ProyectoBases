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

  // Las rutas de comparendo y pago ya están comparadas con los controladores del back.
  // OJO: las de modalidad, usuario y registro de cambios siguen siendo suposiciones
  // (mismo patrón /entidad/todos y /entidad/crear). Si alguna cambia, solo se corrige aquí.
  rutas = {
    comparendos: '/comparendo/todos',
    actualizarComparendo: '/comparendo/actualizar',   // se le agrega /{id}
    pagos: '/pago/todos',
    crearPago: '/pago/crear',
    modalidades: '/modalidad/todos',
    crearModalidad: '/modalidad/crear',
    usuarios: '/usuario/todos',
    registroCambios: '/registrocambios/todos'
  };

  // Valor del estado al anular. Hay que confirmar con el back cómo lo escribe (minúscula o no).
  estadoAnulado: string = 'anulado';

  // Sección que se está viendo: 'comparendos', 'pagos', 'modalidades', 'usuarios' o 'cambios'
  seccionActiva: string = 'comparendos';

  // Animación de entrada (setTimeout + booleano)
  entradaLista: boolean = false;

  // Fecha de hoy en formato aaaa-mm-dd
  fechaHoy: string = '';

  // Mensajes para el administrador
  mensajeExito: string = '';
  mensajeError: string = '';

  // Filtro de la tabla de comparendos: 'todos', 'pendiente', 'pagado' o 'anulado'
  filtroEstado: string = 'todos';

  // Listas que vienen del back
  comparendos: any[] = [];
  pagos: any[] = [];
  modalidades: any[] = [];
  usuarios: any[] = [];
  registroCambios: any[] = [];

  // Formulario de registrar pago
  nuevoPago = {
    idComparendo: null as number | null,
    numeroRecibo: '',
    fecha: ''
  };

  // Formulario de nueva modalidad
  nuevaModalidad = {
    nombre: ''
  };

  constructor(
    private router: Router,
    private http: HttpClient,
    private deteccion: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    // Si no hay token guardado, no se puede estar aquí
    // TEMPORAL: cuando el login guarde el rol, también hay que revisar que sea administrador.
    if (!localStorage.getItem('token')) {
      this.router.navigate(['/login']);
      return;
    }

    this.fechaHoy = this.obtenerFechaHoy();

    this.cargarComparendos();
    this.cargarPagos();
    this.cargarModalidades();
    this.cargarUsuarios();
    this.cargarRegistroCambios();

    setTimeout(() => {
      this.entradaLista = true;
      this.deteccion.markForCheck();
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

  // El estado del comparendo en minúscula, por si el back lo escribe distinto
  private estadoDe(comparendo: any): string {
    return String(comparendo.estado).toLowerCase();
  }

  contarEstado(estado: string): number {
    return this.comparendos.filter(c => this.estadoDe(c) === estado).length;
  }

  comparendosFiltrados(): any[] {
    if (this.filtroEstado === 'todos') {
      return this.comparendos;
    }
    return this.comparendos.filter(c => this.estadoDe(c) === this.filtroEstado);
  }

  // Los comparendos que todavía se pueden pagar
  comparendosPendientes(): any[] {
    return this.comparendos.filter(c => this.estadoDe(c) === 'pendiente');
  }

  esPendiente(comparendo: any): boolean {
    return this.estadoDe(comparendo) === 'pendiente';
  }

  // Clase de color para la etiqueta del estado
  claseEstado(estado: string): string {
    const texto = String(estado).toLowerCase();
    if (texto === 'pendiente' || texto === 'pagado' || texto === 'anulado') {
      return 'insignia-' + texto;
    }
    return '';
  }

  // El pago es por el valor completo del comparendo, así que se muestra y se envía ese valor
  valorSeleccionado(): number | null {
    const comparendo = this.comparendos.find(c => c.id === this.nuevoPago.idComparendo);
    return comparendo ? comparendo.valor : null;
  }

  mostrarExito(texto: string): void {
    this.mensajeExito = texto;
    this.deteccion.markForCheck();
    setTimeout(() => {
      this.mensajeExito = '';
      this.deteccion.markForCheck();
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
    } else if (err.status === 403) {
      this.mensajeError = 'Tu usuario no tiene permiso para hacer esto.';
    } else if (typeof err.error === 'string' && err.error !== '') {
      this.mensajeError = err.error;
    } else {
      this.mensajeError = 'Ocurrió un error. Intenta de nuevo.';
    }
    this.deteccion.markForCheck();
  }

  cambiarSeccion(nombre: string): void {
    this.seccionActiva = nombre;
    this.mensajeExito = '';
    this.mensajeError = '';
  }

  // ---------- Cargar datos del back ----------

  // Pide una lista al back y se la entrega a quien la necesite
  private cargarLista(ruta: string, alTraer: (lista: any[]) => void): void {
    this.http.get<any[]>(this.urlBase + ruta, { headers: this.encabezados() }).subscribe({
      next: (lista) => {
        alTraer(lista);
        this.deteccion.markForCheck();
      },
      error: (err) => this.manejarError(err)
    });
  }

  // En todas las listas el más nuevo va primero
  cargarComparendos(): void {
    this.cargarLista(this.rutas.comparendos, (lista) => {
      this.comparendos = lista.sort((a, b) => b.id - a.id);
    });
  }

  cargarPagos(): void {
    this.cargarLista(this.rutas.pagos, (lista) => {
      this.pagos = lista.sort((a, b) => b.id - a.id);
    });
  }

  cargarModalidades(): void {
    this.cargarLista(this.rutas.modalidades, (lista) => {
      this.modalidades = lista.sort((a, b) => b.id - a.id);
    });
  }

  cargarUsuarios(): void {
    this.cargarLista(this.rutas.usuarios, (lista) => {
      this.usuarios = lista.sort((a, b) => a.id - b.id);
    });
  }

  cargarRegistroCambios(): void {
    this.cargarLista(this.rutas.registroCambios, (lista) => {
      this.registroCambios = lista.sort((a, b) => b.id - a.id);
    });
  }

  // ---------- Guardar en el back ----------

  registrarPago(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    const recibo = this.nuevoPago.numeroRecibo.trim();

    if (this.nuevoPago.idComparendo === null || !recibo || !this.nuevoPago.fecha) {
      this.mensajeError = 'Falta escoger el comparendo, el número de recibo o la fecha.';
      return;
    }
    if (this.nuevoPago.fecha > this.fechaHoy) {
      this.mensajeError = 'La fecha del pago no puede ser posterior a hoy.';
      return;
    }

    const comparendo = this.comparendos.find(c => c.id === this.nuevoPago.idComparendo);
    if (!comparendo) {
      this.mensajeError = 'No se encontró ese comparendo.';
      return;
    }

    // El pago no puede ser antes del comparendo (solo se comparan los 10 primeros caracteres: aaaa-mm-dd)
    if (this.nuevoPago.fecha < String(comparendo.fecha).substring(0, 10)) {
      this.mensajeError = 'El pago no puede ser anterior al comparendo.';
      return;
    }

    // El número de recibo no se puede repetir
    const repetido = this.pagos.some(p => String(p.numeroRecibo).toLowerCase() === recibo.toLowerCase());
    if (repetido) {
      this.mensajeError = 'Ya existe un pago con ese número de recibo.';
      return;
    }

    const parametros = new HttpParams()
      .set('fecha', this.nuevoPago.fecha)
      .set('valor', String(comparendo.valor))
      .set('numeroRecibo', recibo)
      .set('idComparendo', String(comparendo.id));

    this.http.post(this.urlBase + this.rutas.crearPago, null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevoPago = { idComparendo: null, numeroRecibo: '', fecha: '' };
        this.mostrarExito('Pago registrado.');
        // El comparendo pasa a pagado y queda un cambio en el registro
        this.cargarPagos();
        this.cargarComparendos();
        this.cargarRegistroCambios();
      },
      error: (err) => this.manejarError(err)
    });
  }

  anularComparendo(comparendo: any): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    if (!this.esPendiente(comparendo)) {
      this.mensajeError = 'Solo se pueden anular los comparendos pendientes.';
      return;
    }
    if (!window.confirm('¿Anular el comparendo #' + comparendo.id + '? Esto no se puede deshacer.')) {
      return;
    }

    // El back no tiene una ruta para anular: se usa actualizar, que recibe los cuatro datos
    // del comparendo. Se envían todos para que no se borren los que no cambian.
    const parametros = new HttpParams()
      .set('fecha', String(comparendo.fecha).substring(0, 10))
      .set('valor', String(comparendo.valor))
      .set('estado', this.estadoAnulado)
      .set('idEventoInfractor', String(comparendo.idEventoInfractor));

    this.http.put(this.urlBase + this.rutas.actualizarComparendo + '/' + comparendo.id, null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.mostrarExito('Comparendo anulado.');
        this.cargarComparendos();
        this.cargarRegistroCambios();
      },
      error: (err) => this.manejarError(err)
    });
  }

  crearModalidad(): void {
    this.mensajeExito = '';
    this.mensajeError = '';

    const nombre = this.nuevaModalidad.nombre.trim();

    if (!nombre) {
      this.mensajeError = 'Escribe el nombre de la modalidad.';
      return;
    }
    const repetida = this.modalidades.some(m => String(m.nombre).toLowerCase() === nombre.toLowerCase());
    if (repetida) {
      this.mensajeError = 'Esa modalidad ya existe.';
      return;
    }

    const parametros = new HttpParams().set('nombre', nombre);

    this.http.post(this.urlBase + this.rutas.crearModalidad, null, {
      headers: this.encabezados(),
      params: parametros,
      responseType: 'text'
    }).subscribe({
      next: () => {
        this.nuevaModalidad = { nombre: '' };
        this.mostrarExito('Modalidad agregada.');
        this.cargarModalidades();
      },
      error: (err) => this.manejarError(err)
    });
  }

  cerrarSesion(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
