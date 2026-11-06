package co.edu.unbosque.TransMilenio.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import co.edu.unbosque.TransMilenio.entity.Estacion;
import co.edu.unbosque.TransMilenio.entity.Evento;
import co.edu.unbosque.TransMilenio.entity.Tarifa;
import co.edu.unbosque.TransMilenio.entity.Troncal;
import co.edu.unbosque.TransMilenio.repository.EstacionRepository;
import co.edu.unbosque.TransMilenio.repository.EventoRepository;
import co.edu.unbosque.TransMilenio.repository.TarifaRepository;
import co.edu.unbosque.TransMilenio.repository.TroncalRepository;

/**
 * Logica de las estadisticas de la pagina de inicio. No guarda datos: lee los eventos y los cruza con la
 * estacion, la troncal y la tarifa de cada uno para calcular los numeros que muestra la pagina. Todos los
 * calculos suman el campo numeroPersonas del evento ("colados"); si un evento no tiene ese dato, cuenta
 * como 0.
 *
 * <p>Calculos disponibles:
 * <ul>
 * <li>totalColados: suma de numeroPersonas de todos los eventos</li>
 * <li>porTroncal: porcentaje de colados de cada troncal sobre el total</li>
 * <li>resumen: colados de hoy, perdida estimada y estacion critica</li>
 * <li>porHora: colados agrupados por la hora del evento (0 a 23), para las horas pico</li>
 * </ul>
 */
public class EstadisticaService {

	/**
	 * Texto que se devuelve como estacion critica cuando no hay eventos (una raya larga).
	 */
	private static final String SIN_ESTACION = "\u2014";

	/**
	 * Repositorio del evento que usa esta clase.
	 */
	private EventoRepository eventoRepository;
	/**
	 * Repositorio de la estacion que usa esta clase.
	 */
	private EstacionRepository estacionRepository;
	/**
	 * Repositorio de la troncal que usa esta clase.
	 */
	private TroncalRepository troncalRepository;
	/**
	 * Repositorio de la tarifa que usa esta clase.
	 */
	private TarifaRepository tarifaRepository;

	/**
	 * Crea el servicio de estadisticas.
	 *
	 * @param eventoRepository el repositorio del evento
	 * @param estacionRepository el repositorio de la estacion
	 * @param troncalRepository el repositorio de la troncal
	 * @param tarifaRepository el repositorio de la tarifa
	 */
	public EstadisticaService(EventoRepository eventoRepository, EstacionRepository estacionRepository, TroncalRepository troncalRepository, TarifaRepository tarifaRepository) {
		this.eventoRepository = eventoRepository;
		this.estacionRepository = estacionRepository;
		this.troncalRepository = troncalRepository;
		this.tarifaRepository = tarifaRepository;
	}

	/**
	 * Obtiene las personas de un evento, tratando el dato vacio como 0.
	 *
	 * @param evento el evento
	 * @return la cantidad de personas del evento, o 0 si no tiene el dato
	 */
	private long personas(Evento evento) {
		return evento.getNumeroPersonas() == null ? 0 : evento.getNumeroPersonas();
	}

	/**
	 * Calcula el total de colados: la suma de numeroPersonas de todos los eventos.
	 *
	 * @return el total de colados (0 si no hay eventos)
	 */
	public long totalColados() {
		long total = 0;
		for (Evento evento : eventoRepository.listar()) {
			total += personas(evento);
		}
		return total;
	}

	/**
	 * Calcula el porcentaje de colados de cada troncal. Por cada evento busca su estacion y, de ella, su
	 * troncal; suma las personas por troncal y divide entre el total de colados por 100, redondeado a
	 * entero. Solo aparecen las troncales que tienen eventos. Si un evento apunta a una estacion o troncal
	 * que ya no existe, no se cuenta en ninguna troncal (pero si en el total). Como cada porcentaje se
	 * redondea, la suma puede dar 99 o 101.
	 *
	 * @return una lista de filas con "nombre" (la troncal) y "porcentaje" (entero); vacia si no hay
	 *     colados
	 */
	public List<Map<String, Object>> porTroncal() {
		List<Map<String, Object>> filas = new ArrayList<>();
		long total = totalColados();
		if (total == 0)
			return filas;

		Map<Long, Long> personasPorTroncal = new LinkedHashMap<>();
		for (Evento evento : eventoRepository.listar()) {
			Estacion estacion = evento.getIdEstacion() == null ? null : estacionRepository.buscarPorId(evento.getIdEstacion());
			if (estacion == null || estacion.getIdTroncal() == null)
				continue;
			personasPorTroncal.merge(estacion.getIdTroncal(), personas(evento), Long::sum);
		}

		for (Map.Entry<Long, Long> entrada : personasPorTroncal.entrySet()) {
			Troncal troncal = troncalRepository.buscarPorId(entrada.getKey());
			if (troncal == null)
				continue;
			Map<String, Object> fila = new LinkedHashMap<>();
			fila.put("nombre", troncal.getNombre());
			fila.put("porcentaje", Math.round(entrada.getValue() * 100.0 / total));
			filas.add(fila);
		}
		return filas;
	}

	/**
	 * Calcula el resumen de la pagina de inicio:
	 * <ul>
	 * <li>coladosHoy: suma de numeroPersonas de los eventos con fecha de hoy</li>
	 * <li>perdidaEstimada: suma de numeroPersonas por el valor de la tarifa de cada evento</li>
	 * <li>estacionCritica: nombre de la estacion con mas personas entre todos los eventos (una raya larga
	 * si no hay eventos). Si hay empate gana la primera que aparece</li>
	 * </ul>
	 *
	 * @return un mapa con coladosHoy, perdidaEstimada y estacionCritica
	 */
	public Map<String, Object> resumen() {
		LocalDate hoy = LocalDate.now();
		long coladosHoy = 0;
		long perdidaEstimada = 0;
		Map<Long, Long> personasPorEstacion = new LinkedHashMap<>();

		for (Evento evento : eventoRepository.listar()) {
			long personas = personas(evento);
			if (hoy.equals(evento.getFecha()))
				coladosHoy += personas;
			if (evento.getIdTarifa() != null) {
				Tarifa tarifa = tarifaRepository.buscarPorId(evento.getIdTarifa());
				if (tarifa != null && tarifa.getValor() != null)
					perdidaEstimada += personas * tarifa.getValor();
			}
			if (evento.getIdEstacion() != null)
				personasPorEstacion.merge(evento.getIdEstacion(), personas, Long::sum);
		}

		String estacionCritica = SIN_ESTACION;
		long maximo = -1;
		for (Map.Entry<Long, Long> entrada : personasPorEstacion.entrySet()) {
			Estacion estacion = estacionRepository.buscarPorId(entrada.getKey());
			if (estacion != null && entrada.getValue() > maximo) {
				maximo = entrada.getValue();
				estacionCritica = estacion.getNombre();
			}
		}

		Map<String, Object> resumen = new LinkedHashMap<>();
		resumen.put("coladosHoy", coladosHoy);
		resumen.put("perdidaEstimada", perdidaEstimada);
		resumen.put("estacionCritica", estacionCritica);
		return resumen;
	}

	/**
	 * Calcula los colados por hora del dia (horas pico). Suma numeroPersonas agrupando por la hora del
	 * evento. Siempre devuelve las 24 horas (0 a 23), en orden, con 0 en las que no tienen eventos, para
	 * que la grafica no tenga huecos. Los eventos sin hora no se cuentan.
	 *
	 * @return una lista de 24 filas con "hora" (0 a 23) y "colados"
	 */
	public List<Map<String, Object>> porHora() {
		long[] coladosPorHora = new long[24];
		for (Evento evento : eventoRepository.listar()) {
			if (evento.getHora() == null)
				continue;
			coladosPorHora[evento.getHora().getHour()] += personas(evento);
		}

		List<Map<String, Object>> filas = new ArrayList<>();
		for (int hora = 0; hora < 24; hora++) {
			Map<String, Object> fila = new LinkedHashMap<>();
			fila.put("hora", hora);
			fila.put("colados", coladosPorHora[hora]);
			filas.add(fila);
		}
		return filas;
	}
	
	public List<Map<String, Object>> troncalesConEstaciones() {
		List<Map<String, Object>> filas = new ArrayList<>();
		for (Troncal troncal : troncalRepository.listar()) {
			List<String> nombres = new ArrayList<>();
			for (Estacion estacion : estacionRepository.listar()) {
				if (estacion.getIdTroncal().equals(troncal.getId()) && "Si".equals(estacion.getActiva())) {
					nombres.add(estacion.getNombre());
				}
			}
			Map<String, Object> fila = new LinkedHashMap<>();
			fila.put("nombre", troncal.getNombre());
			fila.put("estaciones", nombres);
			filas.add(fila);
		}
		return filas;
	}
		
}
