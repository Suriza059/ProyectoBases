package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.TroncalDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.TroncalService;

/**
 * Controlador REST de la troncal. Ruta base: /troncal. Recibe cada dato como parametro, arma el DTO y llama
 * a TroncalService. Todos los roles con sesion pueden consultar (GET), pero solo el Administrador puede
 * crear, cambiar o borrar.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/troncal")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class TroncalController {

	/**
	 * Servicio de la troncal que usa esta clase.
	 */
	private TroncalService troncalService;

	/**
	 * Crea el controlador.
	 *
	 * @param troncalService el servicio de la troncal
	 */
	public TroncalController(TroncalService troncalService) {
		this.troncalService = troncalService;
	}

	/**
	 * POST /troncal/crear. Crea una troncal nueva.
	 *
	 * @param nombre el nombre de la troncal
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "nombre", required = false) String nombre) {
		TroncalDTO dto = new TroncalDTO();
		dto.setNombre(nombre);
		try {
			troncalService.create(dto);
			return new ResponseEntity<>("La troncal creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /troncal/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<TroncalDTO>> todos() {
		return new ResponseEntity<>(troncalService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /troncal/actualizar/{id}. Actualiza una troncal existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param nombre el nombre de la troncal
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "nombre", required = false) String nombre) {
		TroncalDTO dto = new TroncalDTO();
		dto.setNombre(nombre);
		try {
			troncalService.updateById(id, dto);
			return new ResponseEntity<>("La troncal actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /troncal/eliminar/{id}. Elimina una troncal.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			troncalService.deleteById(id);
			return new ResponseEntity<>("La troncal eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
