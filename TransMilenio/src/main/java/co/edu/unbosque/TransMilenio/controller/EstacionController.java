package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.EstacionDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.EstacionService;

/**
 * Controlador REST de la estacion. Ruta base: /estacion. Recibe cada dato como parametro, arma el DTO y
 * llama a EstacionService. Todos los roles con sesion pueden consultar (GET), pero solo el Administrador
 * puede crear, cambiar o borrar.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/estacion")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class EstacionController {

	/**
	 * Servicio de la estacion que usa esta clase.
	 */
	private EstacionService estacionService;

	/**
	 * Crea el controlador.
	 *
	 * @param estacionService el servicio de la estacion
	 */
	public EstacionController(EstacionService estacionService) {
		this.estacionService = estacionService;
	}

	/**
	 * POST /estacion/crear. Crea una estacion nueva.
	 *
	 * @param nombre el nombre de la estacion
	 * @param activa si la estacion esta activa (S o N)
	 * @param idTroncal el identificador de la troncal a la que pertenece la estacion (llave foranea)
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "activa", required = false) String activa,
			@RequestParam(name = "idTroncal", required = false) Long idTroncal) {
		EstacionDTO dto = new EstacionDTO();
		dto.setNombre(nombre);
		dto.setActiva(activa);
		dto.setIdTroncal(idTroncal);
		try {
			estacionService.create(dto);
			return new ResponseEntity<>("La estacion creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /estacion/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<EstacionDTO>> todos() {
		return new ResponseEntity<>(estacionService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /estacion/actualizar/{id}. Actualiza una estacion existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param nombre el nombre de la estacion
	 * @param activa si la estacion esta activa (S o N)
	 * @param idTroncal el identificador de la troncal a la que pertenece la estacion (llave foranea)
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "activa", required = false) String activa,
			@RequestParam(name = "idTroncal", required = false) Long idTroncal) {
		EstacionDTO dto = new EstacionDTO();
		dto.setNombre(nombre);
		dto.setActiva(activa);
		dto.setIdTroncal(idTroncal);
		try {
			estacionService.updateById(id, dto);
			return new ResponseEntity<>("La estacion actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /estacion/eliminar/{id}. Elimina una estacion.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			estacionService.deleteById(id);
			return new ResponseEntity<>("La estacion eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
