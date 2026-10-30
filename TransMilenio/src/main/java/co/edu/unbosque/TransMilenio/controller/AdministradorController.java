package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.AdministradorDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.AdministradorService;

@RestController
@RequestMapping("/administrador")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class AdministradorController {

	private AdministradorService administradorService;

	public AdministradorController(AdministradorService administradorService) {
		this.administradorService = administradorService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody AdministradorDTO dto) {
		try {
			administradorService.create(dto);
			return new ResponseEntity<>("El administrador creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<AdministradorDTO>> todos() {
		return new ResponseEntity<>(administradorService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody AdministradorDTO dto) {
		try {
			administradorService.updateById(id, dto);
			return new ResponseEntity<>("El administrador actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

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
