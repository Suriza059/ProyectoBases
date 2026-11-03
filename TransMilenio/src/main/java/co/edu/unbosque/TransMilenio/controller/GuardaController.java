package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.GuardaDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.GuardaService;

/**
 * Controlador REST del guarda. Ruta base: /guarda. Recibe cada dato como parametro, arma el DTO y llama a
 * GuardaService. Todos los roles con sesion pueden consultar (GET), pero solo el Administrador puede crear,
 * cambiar o borrar.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/guarda")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class GuardaController {

	/**
	 * Servicio del guarda que usa esta clase.
	 */
	private GuardaService guardaService;

	/**
	 * Crea el controlador.
	 *
	 * @param guardaService el servicio del guarda
	 */
	public GuardaController(GuardaService guardaService) {
		this.guardaService = guardaService;
	}

	/**
	 * POST /guarda/crear. Crea un guarda nuevo.
	 *
	 * @param turno el turno de trabajo del guarda
	 * @param nombre el nombre del guarda
	 * @param apellido el apellido del guarda
	 * @param activo si el guarda esta activo (S o N)
	 * @param cargo el cargo del guarda (por ejemplo, Supervisor)
	 * @param idEstacion el identificador de la estacion donde trabaja el guarda (llave foranea)
	 * @param idSupervisor el identificador del guarda que supervisa a este guarda, puede ser nulo (llave
	 *     foranea)
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "turno", required = false) String turno,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "apellido", required = false) String apellido,
			@RequestParam(name = "activo", required = false) String activo,
			@RequestParam(name = "cargo", required = false) String cargo,
			@RequestParam(name = "idEstacion", required = false) Long idEstacion,
			@RequestParam(name = "idSupervisor", required = false) Long idSupervisor) {
		GuardaDTO dto = new GuardaDTO();
		dto.setTurno(turno);
		dto.setNombre(nombre);
		dto.setApellido(apellido);
		dto.setActivo(activo);
		dto.setCargo(cargo);
		dto.setIdEstacion(idEstacion);
		dto.setIdSupervisor(idSupervisor);
		try {
			guardaService.create(dto);
			return new ResponseEntity<>("El guarda creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /guarda/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<GuardaDTO>> todos() {
		return new ResponseEntity<>(guardaService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /guarda/actualizar/{id}. Actualiza un guarda existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param turno el turno de trabajo del guarda
	 * @param nombre el nombre del guarda
	 * @param apellido el apellido del guarda
	 * @param activo si el guarda esta activo (S o N)
	 * @param cargo el cargo del guarda (por ejemplo, Supervisor)
	 * @param idEstacion el identificador de la estacion donde trabaja el guarda (llave foranea)
	 * @param idSupervisor el identificador del guarda que supervisa a este guarda, puede ser nulo (llave
	 *     foranea)
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "turno", required = false) String turno,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "apellido", required = false) String apellido,
			@RequestParam(name = "activo", required = false) String activo,
			@RequestParam(name = "cargo", required = false) String cargo,
			@RequestParam(name = "idEstacion", required = false) Long idEstacion,
			@RequestParam(name = "idSupervisor", required = false) Long idSupervisor) {
		GuardaDTO dto = new GuardaDTO();
		dto.setTurno(turno);
		dto.setNombre(nombre);
		dto.setApellido(apellido);
		dto.setActivo(activo);
		dto.setCargo(cargo);
		dto.setIdEstacion(idEstacion);
		dto.setIdSupervisor(idSupervisor);
		try {
			guardaService.updateById(id, dto);
			return new ResponseEntity<>("El guarda actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /guarda/eliminar/{id}. Elimina un guarda.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			guardaService.deleteById(id);
			return new ResponseEntity<>("El guarda eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
