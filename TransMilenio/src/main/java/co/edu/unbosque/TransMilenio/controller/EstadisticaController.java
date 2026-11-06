package co.edu.unbosque.TransMilenio.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.service.EstadisticaService;

/**
 * Controlador REST de las estadisticas de la pagina de inicio. Ruta base: /estadistica. Todas las rutas son
 * GET y son publicas: WebConfig las deja fuera del JwtInterceptor porque la pagina de inicio no envia
 * token. Solo consultan datos, no modifican nada.
 */
@RestController
@RequestMapping("/estadistica")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class EstadisticaController {

	/**
	 * Servicio de estadisticas que usa esta clase.
	 */
	private EstadisticaService estadisticaService;

	/**
	 * Crea el controlador.
	 *
	 * @param estadisticaService el servicio de estadisticas
	 */
	public EstadisticaController(EstadisticaService estadisticaService) {
		this.estadisticaService = estadisticaService;
	}

	/**
	 * GET /estadistica/total-colados. Total de colados: suma de numeroPersonas de todos los eventos.
	 *
	 * @return 200 con un numero, por ejemplo 1240
	 */
	@GetMapping("/total-colados")
	public ResponseEntity<Long> totalColados() {
		return new ResponseEntity<>(estadisticaService.totalColados(), HttpStatus.OK);
	}

	/**
	 * GET /estadistica/troncales. Porcentaje de colados de cada troncal sobre el total, redondeado a
	 * entero.
	 *
	 * @return 200 con una lista como [{ "nombre": "Norte", "porcentaje": 42 }, ...]
	 */
	@GetMapping("/troncales")
	public ResponseEntity<List<Map<String, Object>>> troncales() {
		return new ResponseEntity<>(estadisticaService.porTroncal(), HttpStatus.OK);
	}

	/**
	 * GET /estadistica/resumen. Colados de hoy, perdida estimada y estacion critica.
	 *
	 * @return 200 con un objeto como { "coladosHoy": 12, "perdidaEstimada": 38400, "estacionCritica":
	 *     "Portal Norte" }
	 */
	@GetMapping("/resumen")
	public ResponseEntity<Map<String, Object>> resumen() {
		return new ResponseEntity<>(estadisticaService.resumen(), HttpStatus.OK);
	}

	/**
	 * GET /estadistica/horas. Colados por hora del dia (horas pico). Usa la hora de cada evento.
	 *
	 * @return 200 con una lista de 24 filas como [{ "hora": 7, "colados": 15 }, ...]
	 */
	@GetMapping("/horas")
	public ResponseEntity<List<Map<String, Object>>> horas() {
		return new ResponseEntity<>(estadisticaService.porHora(), HttpStatus.OK);
	}
	
	@GetMapping("/troncales-estaciones")
	public ResponseEntity<List<Map<String, Object>>> troncalesEstaciones() {
		return new ResponseEntity<>(estadisticaService.troncalesConEstaciones(), HttpStatus.OK);
	}
}
