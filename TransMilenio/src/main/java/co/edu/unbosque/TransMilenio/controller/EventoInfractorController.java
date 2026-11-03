package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.EventoInfractorDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.EventoInfractorService;

/**
 * Controlador REST de la relacion evento-infractor. Ruta base: /eventoInfractor. Recibe cada dato como
 * parametro, arma el DTO y llama a EventoInfractorService. Cualquier rol con sesion puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/eventoInfractor")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class EventoInfractorController {

	/**
	 * Servicio de la relacion evento-infractor que usa esta clase.
	 */
	private EventoInfractorService eventoInfractorService;

	/**
	 * Crea el controlador.
	 *
	 * @param eventoInfractorService el servicio de la relacion evento-infractor
	 */
	public EventoInfractorController(EventoInfractorService eventoInfractorService) {
		this.eventoInfractorService = eventoInfractorService;
	}

	/**
	 * POST /eventoInfractor/crear. Crea una relacion evento-infractor nueva.
	 *
	 * @param idEvento el identificador del evento (llave foranea)
	 * @param idInfractor el identificador del infractor (llave foranea)
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "idEvento", required = false) Long idEvento,
			@RequestParam(name = "idInfractor", required = false) Long idInfractor) {
		EventoInfractorDTO dto = new EventoInfractorDTO();
		dto.setIdEvento(idEvento);
		dto.setIdInfractor(idInfractor);
		try {
			eventoInfractorService.create(dto);
			return new ResponseEntity<>("La pareja evento-infractor creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /eventoInfractor/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<EventoInfractorDTO>> todos() {
		return new ResponseEntity<>(eventoInfractorService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /eventoInfractor/actualizar/{id}. Actualiza una relacion evento-infractor existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param idEvento el identificador del evento (llave foranea)
	 * @param idInfractor el identificador del infractor (llave foranea)
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "idEvento", required = false) Long idEvento,
			@RequestParam(name = "idInfractor", required = false) Long idInfractor) {
		EventoInfractorDTO dto = new EventoInfractorDTO();
		dto.setIdEvento(idEvento);
		dto.setIdInfractor(idInfractor);
		try {
			eventoInfractorService.updateById(id, dto);
			return new ResponseEntity<>("La pareja evento-infractor actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /eventoInfractor/eliminar/{id}. Elimina una relacion evento-infractor.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			eventoInfractorService.deleteById(id);
			return new ResponseEntity<>("La pareja evento-infractor eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
