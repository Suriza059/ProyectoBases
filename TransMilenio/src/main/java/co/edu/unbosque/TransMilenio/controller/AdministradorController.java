package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.AdministradorDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.AdministradorService;

/**
 * Controlador REST del administrador. Ruta base: /administrador. Recibe cada dato como parametro, arma el
 * DTO y llama a AdministradorService. Solo el Administrador puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/administrador")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class AdministradorController {

	/**
	 * Servicio del administrador que usa esta clase.
	 */
	private AdministradorService administradorService;

	/**
	 * Crea el controlador.
	 *
	 * @param administradorService el servicio del administrador
	 */
	public AdministradorController(AdministradorService administradorService) {
		this.administradorService = administradorService;
	}

	/**
	 * POST /administrador/crear. Crea un administrador nuevo.
	 *
	 * @param idUsuario el identificador del usuario (llave primaria y foranea)
	 * @param areaGestion el area que gestiona el administrador
	 * @param fechaGestion la fecha de gestion del administrador
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "idUsuario", required = false) Long idUsuario,
			@RequestParam(name = "areaGestion", required = false) String areaGestion,
			@RequestParam(name = "fechaGestion", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaGestion) {
		AdministradorDTO dto = new AdministradorDTO();
		dto.setIdUsuario(idUsuario);
		dto.setAreaGestion(areaGestion);
		dto.setFechaGestion(fechaGestion);
		try {
			administradorService.create(dto);
			return new ResponseEntity<>("El administrador creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /administrador/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<AdministradorDTO>> todos() {
		return new ResponseEntity<>(administradorService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /administrador/actualizar/{id}. Actualiza un administrador existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param areaGestion el area que gestiona el administrador
	 * @param fechaGestion la fecha de gestion del administrador
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "areaGestion", required = false) String areaGestion,
			@RequestParam(name = "fechaGestion", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaGestion) {
		AdministradorDTO dto = new AdministradorDTO();
		dto.setAreaGestion(areaGestion);
		dto.setFechaGestion(fechaGestion);
		try {
			administradorService.updateById(id, dto);
			return new ResponseEntity<>("El administrador actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /administrador/eliminar/{id}. Elimina un administrador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			administradorService.deleteById(id);
			return new ResponseEntity<>("El administrador eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
