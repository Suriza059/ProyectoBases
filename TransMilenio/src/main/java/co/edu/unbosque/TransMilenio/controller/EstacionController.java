package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.EstacionDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.EstacionService;

@RestController
@RequestMapping("/estacion")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class EstacionController {

	private EstacionService estacionService;

	public EstacionController(EstacionService estacionService) {
		this.estacionService = estacionService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody EstacionDTO dto) {
		try {
			estacionService.create(dto);
			return new ResponseEntity<>("La estacion creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<EstacionDTO>> todos() {
		return new ResponseEntity<>(estacionService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody EstacionDTO dto) {
		try {
			estacionService.updateById(id, dto);
			return new ResponseEntity<>("La estacion actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

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
