package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.ModalidadDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.ModalidadService;

/**
 * Controlador REST de la modalidad. Ruta base: /modalidad. Recibe cada dato como parametro, arma el DTO y
 * llama a ModalidadService. Todos los roles con sesion pueden consultar (GET), pero solo el Administrador
 * puede crear, cambiar o borrar.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/modalidad")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class ModalidadController {

	/**
	 * Servicio de la modalidad que usa esta clase.
	 */
	private ModalidadService modalidadService;

	/**
	 * Crea el controlador.
	 *
	 * @param modalidadService el servicio de la modalidad
	 */
	public ModalidadController(ModalidadService modalidadService) {
		this.modalidadService = modalidadService;
	}

	/**
	 * POST /modalidad/crear. Crea una modalidad nueva.
	 *
	 * @param nombre el nombre de la modalidad
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "nombre", required = false) String nombre) {
		ModalidadDTO dto = new ModalidadDTO();
		dto.setNombre(nombre);
		try {
			modalidadService.create(dto);
			return new ResponseEntity<>("La modalidad creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /modalidad/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<ModalidadDTO>> todos() {
		return new ResponseEntity<>(modalidadService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /modalidad/actualizar/{id}. Actualiza una modalidad existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param nombre el nombre de la modalidad
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "nombre", required = false) String nombre) {
		ModalidadDTO dto = new ModalidadDTO();
		dto.setNombre(nombre);
		try {
			modalidadService.updateById(id, dto);
			return new ResponseEntity<>("La modalidad actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /modalidad/eliminar/{id}. Elimina una modalidad.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			modalidadService.deleteById(id);
			return new ResponseEntity<>("La modalidad eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
