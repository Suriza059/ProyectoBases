package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.GuardaDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.GuardaService;

@RestController
@RequestMapping("/guarda")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class GuardaController {

	private GuardaService guardaService;

	public GuardaController(GuardaService guardaService) {
		this.guardaService = guardaService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody GuardaDTO dto) {
		try {
			guardaService.create(dto);
			return new ResponseEntity<>("El guarda creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<GuardaDTO>> todos() {
		return new ResponseEntity<>(guardaService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody GuardaDTO dto) {
		try {
			guardaService.updateById(id, dto);
			return new ResponseEntity<>("El guarda actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

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
