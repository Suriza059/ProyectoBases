package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.UsuarioDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.UsuarioService;

/**
 * Controlador REST del usuario. Ruta base: /usuario. Recibe cada dato como parametro, arma el DTO y llama a
 * UsuarioService. Solo el Administrador puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class UsuarioController {

	/**
	 * Servicio del usuario que usa esta clase.
	 */
	private UsuarioService usuarioService;

	/**
	 * Crea el controlador.
	 *
	 * @param usuarioService el servicio del usuario
	 */
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	/**
	 * POST /usuario/crear. Crea un usuario nuevo.
	 *
	 * @param rol el rol del usuario (Administrador, Analista o Guarda)
	 * @param nombreUsuario el nombre de usuario con el que inicia sesion (no se puede repetir)
	 * @param contrasena la contrasena del usuario (se guarda cifrada)
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "rol", required = false) String rol,
			@RequestParam(name = "nombreUsuario", required = false) String nombreUsuario,
			@RequestParam(name = "contrasena", required = false) String contrasena) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setRol(rol);
		dto.setNombreUsuario(nombreUsuario);
		dto.setContrasena(contrasena);
		try {
			usuarioService.create(dto);
			return new ResponseEntity<>("El usuario creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /usuario/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<UsuarioDTO>> todos() {
		return new ResponseEntity<>(usuarioService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /usuario/actualizar/{id}. Actualiza un usuario existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param rol el rol del usuario (Administrador, Analista o Guarda)
	 * @param nombreUsuario el nombre de usuario con el que inicia sesion (no se puede repetir)
	 * @param contrasena la contrasena del usuario (se guarda cifrada)
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "rol", required = false) String rol,
			@RequestParam(name = "nombreUsuario", required = false) String nombreUsuario,
			@RequestParam(name = "contrasena", required = false) String contrasena) {
		UsuarioDTO dto = new UsuarioDTO();
		dto.setRol(rol);
		dto.setNombreUsuario(nombreUsuario);
		dto.setContrasena(contrasena);
		try {
			usuarioService.updateById(id, dto);
			return new ResponseEntity<>("El usuario actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /usuario/eliminar/{id}. Elimina un usuario.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			usuarioService.deleteById(id);
			return new ResponseEntity<>("El usuario eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
