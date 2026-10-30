package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.UsuarioGuardaDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.UsuarioGuardaService;

@RestController
@RequestMapping("/usuarioGuarda")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class UsuarioGuardaController {

	private UsuarioGuardaService usuarioGuardaService;

	public UsuarioGuardaController(UsuarioGuardaService usuarioGuardaService) {
		this.usuarioGuardaService = usuarioGuardaService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody UsuarioGuardaDTO dto) {
		try {
			usuarioGuardaService.create(dto);
			return new ResponseEntity<>("La activacion de guarda creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<UsuarioGuardaDTO>> todos() {
		return new ResponseEntity<>(usuarioGuardaService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody UsuarioGuardaDTO dto) {
		try {
			usuarioGuardaService.updateById(id, dto);
			return new ResponseEntity<>("La activacion de guarda actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			usuarioGuardaService.deleteById(id);
			return new ResponseEntity<>("La activacion de guarda eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
