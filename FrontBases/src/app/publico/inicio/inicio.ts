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

interface TroncalEstaciones {
  nombre: string;
  estaciones: string[];
}

interface Hora {
  hora: number;
  colados: number;
}

@Component({
  selector: 'app-inicio',
  standalone: false,
  templateUrl: './inicio.html',
  styleUrl: './inicio.css',
})
export class Inicio implements OnInit {
  nombreSistema = 'SIREC';

  indicadores = [
    { titulo: 'Colados hoy', valor: '—', detalle: 'personas registradas hoy' },
    { titulo: 'Pérdida estimada', valor: '—', detalle: 'personas por la tarifa de cada día' },
    { titulo: 'Estación con más colados', valor: '—', detalle: 'en todos los registros' },
  ];

  troncales: Troncal[] = [];

  troncalesEstaciones: TroncalEstaciones[] = [];

  horas: Hora[] = [];
  maximoHora = 0;
  horaPico: number | null = null;

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
    this.cargarTroncalesEstaciones();
    this.cargarHoras();

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

  cargarTroncalesEstaciones(): void {
    this.http.get<TroncalEstaciones[]>('http://localhost:8080/estadistica/troncales-estaciones').subscribe({
      next: (lista) => {
        this.troncalesEstaciones = lista;
        this.cambios.detectChanges();
      },
      error: () => {
        this.troncalesEstaciones = [];
        this.cambios.detectChanges();
      }
    });
  }

  cargarHoras(): void {
    this.http.get<Hora[]>('http://localhost:8080/estadistica/horas').subscribe({
      next: (lista) => {
        this.horas = lista;
        this.maximoHora = 0;
        this.horaPico = null;
        for (const h of lista) {
          if (h.colados > this.maximoHora) {
            this.maximoHora = h.colados;
            this.horaPico = h.hora;
          }
        }
        this.cambios.detectChanges();
      },
      error: () => {
        this.horas = [];
        this.maximoHora = 0;
        this.horaPico = null;
        this.cambios.detectChanges();
      }
    });
  }

  alturaBarra(colados: number): number {
    if (this.maximoHora === 0) {
      return 0;
    }
    return colados / this.maximoHora;
  }

  textoHora(hora: number): string {
    if (hora === 0) {
      return '12:00 a. m.';
    }
    if (hora < 12) {
      return hora + ':00 a. m.';
    }
    if (hora === 12) {
      return '12:00 p. m.';
    }
    return (hora - 12) + ':00 p. m.';
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
