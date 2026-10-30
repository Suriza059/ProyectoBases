import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

export interface Estacion {
  id: number;
  nombre: string;
  activa: boolean;
}

export interface Modalidad {
  id: number;
  nombre: string;
}

// Lo que el front envía al registrar. La tarifa y el guarda los asigna el back.
export interface DatosEvento {
  fecha: string;
  numeroPersonas: number;
  idEstacion: number;
  idModalidad: number;
}

// Lo que se muestra en la lista de eventos registrados
export interface EventoRegistrado {
  id: number;
  fecha: string;
  numeroPersonas: number;
  estacion: string;
  modalidad: string;
}

@Injectable({
  providedIn: 'root',
})
export class EventoService {
  // SIMULACIÓN: cuando exista el backend, estas listas vienen de la base de datos.
  // CAMBIA estos nombres por los de su base de datos.
  private estaciones: Estacion[] = [
    { id: 1, nombre: 'Portal Norte', activa: true },
    { id: 2, nombre: 'Calle 100', activa: true },
    { id: 3, nombre: 'Av. Jiménez', activa: true },
    { id: 4, nombre: 'Estación cerrada de ejemplo', activa: false },
  ];

  private modalidades: Modalidad[] = [
    { id: 1, nombre: 'Saltar el torniquete' },
    { id: 2, nombre: 'Pasar por debajo del torniquete' },
    { id: 3, nombre: 'Entrar por la puerta de salida' },
  ];

  private eventos: EventoRegistrado[] = [];
  private siguienteId = 1;

  // Solo devuelve las estaciones activas (columna Activa de la tabla Estacion)
  listarEstaciones(): Observable<Estacion[]> {
    const activas: Estacion[] = [];
    for (const e of this.estaciones) {
      if (e.activa) {
        activas.push(e);
      }
    }
    return of(activas);
  }

  listarModalidades(): Observable<Modalidad[]> {
    return of(this.modalidades);
  }

  listarEventos(): Observable<EventoRegistrado[]> {
    return of(this.eventos.slice());
  }

  // Simula guardar el evento en el back
  registrarEvento(datos: DatosEvento): Observable<EventoRegistrado> {
    let nombreEstacion = '';
    for (const e of this.estaciones) {
      if (e.id === datos.idEstacion) {
        nombreEstacion = e.nombre;
      }
    }

    let nombreModalidad = '';
    for (const m of this.modalidades) {
      if (m.id === datos.idModalidad) {
        nombreModalidad = m.nombre;
      }
    }

    const nuevo: EventoRegistrado = {
      id: this.siguienteId,
      fecha: datos.fecha,
      numeroPersonas: datos.numeroPersonas,
      estacion: nombreEstacion,
      modalidad: nombreModalidad,
    };

    this.siguienteId = this.siguienteId + 1;
    this.eventos.unshift(nuevo); // unshift: lo pone de primero en la lista

    return of(nuevo);
  }

  // MÁS ADELANTE, con el backend, cada método cambia por una llamada HTTP:
  //   constructor(private http: HttpClient) {}
  //   listarEstaciones() { return this.http.get<Estacion[]>('URL-DEL-BACK/estaciones'); }
}
