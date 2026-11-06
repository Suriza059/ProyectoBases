package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.AgresionDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.AgresionService;

/**
 * Controlador REST de la agresion. Ruta base: /agresion. Recibe cada dato como parametro, arma el DTO y
 * llama a AgresionService. Cualquier rol con sesion puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/agresion")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class AgresionController {

	/**
	 * Servicio de la agresion que usa esta clase.
	 */
	private AgresionService agresionService;

	/**
	 * Crea el controlador.
	 *
	 * @param agresionService el servicio de la agresion
	 */
	public AgresionController(AgresionService agresionService) {
		this.agresionService = agresionService;
	}

	/**
	 * POST /agresion/crear. Crea una agresion nueva.
	 *
	 * @param fecha la fecha de la agresion
	 * @param gravedad la gravedad de la agresion
	 * @param descripcion la descripcion de la agresion
	 * @param idEvento el identificador del evento en el que ocurrio la agresion (llave foranea)
	 * @param idGuarda el identificador del guarda involucrado en la agresion (llave foranea)
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
			@RequestParam(name = "gravedad", required = false) String gravedad,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "idEvento", required = false) Long idEvento,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		AgresionDTO dto = new AgresionDTO();
		dto.setFecha(fecha);
		dto.setGravedad(gravedad);
		dto.setDescripcion(descripcion);
		dto.setIdEvento(idEvento);
		dto.setIdGuarda(idGuarda);
		try {
			agresionService.create(dto);
			return new ResponseEntity<>("La agresion creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /agresion/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<AgresionDTO>> todos() {
		return new ResponseEntity<>(agresionService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /agresion/actualizar/{id}. Actualiza una agresion existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param fecha la fecha de la agresion
	 * @param gravedad la gravedad de la agresion
	 * @param descripcion la descripcion de la agresion
	 * @param idEvento el identificador del evento en el que ocurrio la agresion (llave foranea)
	 * @param idGuarda el identificador del guarda involucrado en la agresion (llave foranea)
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
			@RequestParam(name = "gravedad", required = false) String gravedad,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "idEvento", required = false) Long idEvento,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		AgresionDTO dto = new AgresionDTO();
		dto.setFecha(fecha);
		dto.setGravedad(gravedad);
		dto.setDescripcion(descripcion);
		dto.setIdEvento(idEvento);
		dto.setIdGuarda(idGuarda);
		try {
			agresionService.updateById(id, dto);
			return new ResponseEntity<>("La agresion actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /agresion/eliminar/{id}. Elimina una agresion.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			agresionService.deleteById(id);
			return new ResponseEntity<>("La agresion eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
