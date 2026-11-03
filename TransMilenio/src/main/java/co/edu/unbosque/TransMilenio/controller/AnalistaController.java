package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.AnalistaDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.AnalistaService;

/**
 * Controlador REST del analista. Ruta base: /analista. Recibe cada dato como parametro, arma el DTO y llama
 * a AnalistaService. Solo el Administrador puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/analista")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class AnalistaController {

	/**
	 * Servicio del analista que usa esta clase.
	 */
	private AnalistaService analistaService;

	/**
	 * Crea el controlador.
	 *
	 * @param analistaService el servicio del analista
	 */
	public AnalistaController(AnalistaService analistaService) {
		this.analistaService = analistaService;
	}

	/**
	 * POST /analista/crear. Crea un analista nuevo.
	 *
	 * @param idUsuario el identificador del usuario (llave primaria y foranea)
	 * @param areaAnalisis el area que analiza el analista
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "idUsuario", required = false) Long idUsuario,
			@RequestParam(name = "areaAnalisis", required = false) String areaAnalisis) {
		AnalistaDTO dto = new AnalistaDTO();
		dto.setIdUsuario(idUsuario);
		dto.setAreaAnalisis(areaAnalisis);
		try {
			analistaService.create(dto);
			return new ResponseEntity<>("El analista creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /analista/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<AnalistaDTO>> todos() {
		return new ResponseEntity<>(analistaService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /analista/actualizar/{id}. Actualiza un analista existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param areaAnalisis el area que analiza el analista
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "areaAnalisis", required = false) String areaAnalisis) {
		AnalistaDTO dto = new AnalistaDTO();
		dto.setAreaAnalisis(areaAnalisis);
		try {
			analistaService.updateById(id, dto);
			return new ResponseEntity<>("El analista actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /analista/eliminar/{id}. Elimina un analista.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			analistaService.deleteById(id);
			return new ResponseEntity<>("El analista eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
