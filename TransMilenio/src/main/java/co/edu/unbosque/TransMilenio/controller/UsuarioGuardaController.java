package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.UsuarioGuardaDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.UsuarioGuardaService;

/**
 * Controlador REST de la activacion de guarda. Ruta base: /usuarioGuarda. Recibe cada dato como parametro,
 * arma el DTO y llama a UsuarioGuardaService. Solo el Administrador puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/usuarioGuarda")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class UsuarioGuardaController {

	/**
	 * Servicio de la activacion de guarda que usa esta clase.
	 */
	private UsuarioGuardaService usuarioGuardaService;

	/**
	 * Crea el controlador.
	 *
	 * @param usuarioGuardaService el servicio de la activacion de guarda
	 */
	public UsuarioGuardaController(UsuarioGuardaService usuarioGuardaService) {
		this.usuarioGuardaService = usuarioGuardaService;
	}

	/**
	 * POST /usuarioGuarda/crear. Crea una activacion de guarda nueva.
	 *
	 * @param idUsuario el identificador del usuario (llave primaria y foranea)
	 * @param fechaActivacion la fecha en que se activo el guarda para el usuario
	 * @param idGuarda el identificador del guarda activado (llave foranea)
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "idUsuario", required = false) Long idUsuario,
			@RequestParam(name = "fechaActivacion", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaActivacion,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		UsuarioGuardaDTO dto = new UsuarioGuardaDTO();
		dto.setIdUsuario(idUsuario);
		dto.setFechaActivacion(fechaActivacion);
		dto.setIdGuarda(idGuarda);
		try {
			usuarioGuardaService.create(dto);
			return new ResponseEntity<>("El guarda activado creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /usuarioGuarda/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<UsuarioGuardaDTO>> todos() {
		return new ResponseEntity<>(usuarioGuardaService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /usuarioGuarda/actualizar/{id}. Actualiza una activacion de guarda existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param fechaActivacion la fecha en que se activo el guarda para el usuario
	 * @param idGuarda el identificador del guarda activado (llave foranea)
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "fechaActivacion", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaActivacion,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		UsuarioGuardaDTO dto = new UsuarioGuardaDTO();
		dto.setFechaActivacion(fechaActivacion);
		dto.setIdGuarda(idGuarda);
		try {
			usuarioGuardaService.updateById(id, dto);
			return new ResponseEntity<>("El guarda activado actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /usuarioGuarda/eliminar/{id}. Elimina una activacion de guarda.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			usuarioGuardaService.deleteById(id);
			return new ResponseEntity<>("El guarda activado eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
