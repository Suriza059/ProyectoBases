package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.AnalistaDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.AnalistaService;

@RestController
@RequestMapping("/analista")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class AnalistaController {

	private AnalistaService analistaService;

	public AnalistaController(AnalistaService analistaService) {
		this.analistaService = analistaService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "idUsuario", required = false) Long idUsuario,
			@RequestParam(name = "areaAnalisis", required = false) String areaAnalisis) {
		AnalistaDTO dto = new AnalistaDTO();
		dto.setIdUsuario(idUsuario);
		dto.setAreaAnalisis(areaAnalisis);
		try {
			analistaService.create(dto);
			return new ResponseEntity<>("El analista creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<AnalistaDTO>> todos() {
		return new ResponseEntity<>(analistaService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "areaAnalisis", required = false) String areaAnalisis) {
		AnalistaDTO dto = new AnalistaDTO();
		dto.setAreaAnalisis(areaAnalisis);
		try {
			analistaService.updateById(id, dto);
			return new ResponseEntity<>("El analista actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			analistaService.deleteById(id);
			return new ResponseEntity<>("El analista eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
