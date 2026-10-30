import { Component, HostListener, OnInit } from '@angular/core';

interface Troncal {
  nombre: string;
  porcentaje: number;
}

@Component({
  selector: 'app-inicio',
  standalone: false,
  templateUrl: './inicio.html',
  styleUrl: './inicio.css',
})
export class Inicio implements OnInit {
  // DATOS DE EJEMPLO: más adelante vendrán del backend
  nombreSistema = 'Sistema de Monitoreo de Colados';

  indicadores = [
    { titulo: 'Colados hoy', valor: '128', detalle: '+5% vs ayer' },
    { titulo: 'Nivel de evasión', valor: '18%', detalle: '-2% vs ayer' },
    { titulo: 'Pérdida estimada', valor: '$ 1.250.000', detalle: 'acumulado de hoy' },
    { titulo: 'Estación crítica', valor: 'Estación de ejemplo', detalle: 'más colados hoy' },
  ];

  // CAMBIA ESTOS NOMBRES por las troncales que van a evaluar
  troncales: Troncal[] = [
    { nombre: 'Autonorte', porcentaje: 85 },
    { nombre: 'Caracas', porcentaje: 62 },
    { nombre: 'Calle 80', porcentaje: 45 },
    { nombre: 'Américas', porcentaje: 28 },
  ];

  // BOOLEANOS DE ANIMACIÓN: false = oculto, true = aparece con animación
  mostrarTitulo = false;
  mostrarSubtitulo = false;
  mostrarIndicadores = false;
  mostrarTroncales = false;
  mostrarMapa = false;
  mostrarHoras = false;

  // true cuando el usuario baja: el encabezado se pone rojo
  encabezadoSolido = false;

  // Al cargar la página, los bloques del inicio aparecen uno tras otro
  ngOnInit(): void {
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

  // Se ejecuta cada vez que el usuario hace scroll
  @HostListener('window:scroll')
  alHacerScroll(): void {
    const scroll = window.scrollY;

    this.encabezadoSolido = scroll > 60;

    // Cada sección aparece cuando ya bajaste lo suficiente para verla
    if (scroll > 100) {
      this.mostrarTroncales = true;
    }
    if (scroll > 350) {
      this.mostrarMapa = true;
    }
    if (scroll > 650) {
      this.mostrarHoras = true;
    }
  }
}
