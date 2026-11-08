package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.UsuarioDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class UsuarioController {

	private UsuarioService usuarioService;

	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

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
		} catch (DuplicateKeyException e) {
			return new ResponseEntity<>("Ya existe un usuario con ese nombre.", HttpStatus.CONFLICT);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Los datos del usuario no son válidos.", HttpStatus.BAD_REQUEST);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<UsuarioDTO>> todos() {
		return new ResponseEntity<>(usuarioService.getAll(), HttpStatus.OK);
	}

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
		} catch (DuplicateKeyException e) {
			return new ResponseEntity<>("Ya existe un usuario con ese nombre.", HttpStatus.CONFLICT);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Los datos del usuario no son válidos.", HttpStatus.BAD_REQUEST);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			usuarioService.deleteById(id);
			return new ResponseEntity<>("El usuario eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("No se puede eliminar el usuario porque tiene cambios de comparendos registrados a su nombre.", HttpStatus.CONFLICT);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}