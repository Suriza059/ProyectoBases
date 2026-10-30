package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.TarifaDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.TarifaService;

@RestController
@RequestMapping("/tarifa")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class TarifaController {

	private TarifaService tarifaService;

	public TarifaController(TarifaService tarifaService) {
		this.tarifaService = tarifaService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(@RequestBody TarifaDTO dto) {
		try {
			tarifaService.create(dto);
			return new ResponseEntity<>("La tarifa creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<TarifaDTO>> todos() {
		return new ResponseEntity<>(tarifaService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(@PathVariable Long id, @RequestBody TarifaDTO dto) {
		try {
			tarifaService.updateById(id, dto);
			return new ResponseEntity<>("La tarifa actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			tarifaService.deleteById(id);
			return new ResponseEntity<>("La tarifa eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
