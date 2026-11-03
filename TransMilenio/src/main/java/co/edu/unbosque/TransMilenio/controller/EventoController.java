package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.EventoDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.EventoService;

/**
 * Controlador REST del evento. Ruta base: /evento. Recibe cada dato como parametro, arma el DTO y llama a
 * EventoService. Cualquier rol con sesion puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/evento")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class EventoController {

	/**
	 * Servicio del evento que usa esta clase.
	 */
	private EventoService eventoService;

	/**
	 * Crea el controlador.
	 *
	 * @param eventoService el servicio del evento
	 */
	public EventoController(EventoService eventoService) {
		this.eventoService = eventoService;
	}

	/**
	 * POST /evento/crear. Crea un evento nuevo.
	 *
	 * @param fecha la fecha en que ocurrio el evento
	 * @param numeroPersonas la cantidad de personas involucradas en el evento
	 * @param idEstacion el identificador de la estacion donde ocurrio el evento (llave foranea)
	 * @param idTarifa el identificador de la tarifa vigente cuando ocurrio el evento, dato historico (llave
	 *     foranea)
	 * @param idModalidad el identificador de la modalidad del evento (llave foranea)
	 * @param idGuarda el identificador del guarda asociado al evento (llave foranea)
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "numeroPersonas", required = false) Integer numeroPersonas,
			@RequestParam(name = "idEstacion", required = false) Long idEstacion,
			@RequestParam(name = "idTarifa", required = false) Long idTarifa,
			@RequestParam(name = "idModalidad", required = false) Long idModalidad,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		EventoDTO dto = new EventoDTO();
		dto.setFecha(fecha);
		dto.setNumeroPersonas(numeroPersonas);
		dto.setIdEstacion(idEstacion);
		dto.setIdTarifa(idTarifa);
		dto.setIdModalidad(idModalidad);
		dto.setIdGuarda(idGuarda);
		try {
			eventoService.create(dto);
			return new ResponseEntity<>("El evento creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /evento/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<EventoDTO>> todos() {
		return new ResponseEntity<>(eventoService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /evento/actualizar/{id}. Actualiza un evento existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param fecha la fecha en que ocurrio el evento
	 * @param numeroPersonas la cantidad de personas involucradas en el evento
	 * @param idEstacion el identificador de la estacion donde ocurrio el evento (llave foranea)
	 * @param idTarifa el identificador de la tarifa vigente cuando ocurrio el evento, dato historico (llave
	 *     foranea)
	 * @param idModalidad el identificador de la modalidad del evento (llave foranea)
	 * @param idGuarda el identificador del guarda asociado al evento (llave foranea)
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "numeroPersonas", required = false) Integer numeroPersonas,
			@RequestParam(name = "idEstacion", required = false) Long idEstacion,
			@RequestParam(name = "idTarifa", required = false) Long idTarifa,
			@RequestParam(name = "idModalidad", required = false) Long idModalidad,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		EventoDTO dto = new EventoDTO();
		dto.setFecha(fecha);
		dto.setNumeroPersonas(numeroPersonas);
		dto.setIdEstacion(idEstacion);
		dto.setIdTarifa(idTarifa);
		dto.setIdModalidad(idModalidad);
		dto.setIdGuarda(idGuarda);
		try {
			eventoService.updateById(id, dto);
			return new ResponseEntity<>("El evento actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /evento/eliminar/{id}. Elimina un evento.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			eventoService.deleteById(id);
			return new ResponseEntity<>("El evento eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
