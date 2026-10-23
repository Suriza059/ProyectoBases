import { Component } from '@angular/core';

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
export class Inicio {
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
}
