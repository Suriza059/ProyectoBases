import { ChangeDetectorRef, Component, HostListener, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface Troncal {
  nombre: string;
  porcentaje: number;
}

interface Resumen {
  coladosHoy: number;
  perdidaEstimada: number;
  estacionCritica: string;
}

@Component({
  selector: 'app-inicio',
  standalone: false,
  templateUrl: './inicio.html',
  styleUrl: './inicio.css',
})
export class Inicio implements OnInit {
  nombreSistema = 'Sistema de Monitoreo de Colados';

  indicadores = [
    { titulo: 'Colados hoy', valor: '—', detalle: 'personas registradas hoy' },
    { titulo: 'Pérdida estimada', valor: '—', detalle: 'personas por la tarifa de cada día' },
    { titulo: 'Estación con más colados', valor: '—', detalle: 'en todos los registros' },
  ];

  troncales: Troncal[] = [];

  totalColados = 0;

  mostrarTitulo = false;
  mostrarSubtitulo = false;
  mostrarIndicadores = false;
  mostrarTotal = false;
  mostrarTroncales = false;
  mostrarTroncalesEstaciones = false;
  mostrarMapa = false;
  mostrarHoras = false;
  mostrarRoles = false;

  encabezadoSolido = false;

  constructor(
    private http: HttpClient,
    private cambios: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.cargarResumen();
    this.cargarTroncales();
    this.cargarTotal();

    setTimeout(() => {
      this.mostrarTitulo = true;
    }, 200);

    setTimeout(() => {
      this.mostrarSubtitulo = true;
    }, 700);

    setTimeout(() => {
      this.mostrarIndicadores = true;
    }, 1200);
  }

  cargarResumen(): void {
    this.http.get<Resumen>('http://localhost:8080/estadistica/resumen').subscribe({
      next: (datos) => {
        this.indicadores[0].valor = String(datos.coladosHoy);
        this.indicadores[1].valor = '$ ' + datos.perdidaEstimada.toLocaleString('es-CO');
        this.indicadores[2].valor = datos.estacionCritica;
        this.cambios.detectChanges();
      },
      error: () => {
        this.cambios.detectChanges();
      }
    });
  }

  cargarTroncales(): void {
    this.http.get<Troncal[]>('http://localhost:8080/estadistica/troncales').subscribe({
      next: (lista) => {
        this.troncales = lista;
        this.cambios.detectChanges();
      },
      error: () => {
        this.troncales = [];
        this.cambios.detectChanges();
      }
    });
  }

  cargarTotal(): void {
    this.http.get<number>('http://localhost:8080/estadistica/total-colados').subscribe({
      next: (total) => {
        this.totalColados = total;
        this.cambios.detectChanges();
      },
      error: () => {
        this.totalColados = 0;
        this.cambios.detectChanges();
      }
    });
  }

  @HostListener('window:scroll')
  alHacerScroll(): void {
    const scroll = window.scrollY;

    this.encabezadoSolido = scroll > 60;

    if (scroll > 100) {
      this.mostrarTotal = true;
    }
    if (scroll > 150) {
      this.mostrarTroncales = true;
    }
    if (scroll > 450) {
      this.mostrarTroncalesEstaciones = true;
    }
    if (scroll > 800) {
      this.mostrarMapa = true;
    }
    if (scroll > 850) {
      this.mostrarHoras = true;
    }
    if (scroll > 1150) {
      this.mostrarRoles = true;
    }
  }
}
