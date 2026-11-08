package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.TroncalDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.TroncalService;

@RestController
@RequestMapping("/troncal")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class TroncalController {

	private TroncalService troncalService;

	public TroncalController(TroncalService troncalService) {
		this.troncalService = troncalService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "nombre", required = false) String nombre) {
		TroncalDTO dto = new TroncalDTO();
		dto.setNombre(nombre);
		try {
			troncalService.create(dto);
			return new ResponseEntity<>("La troncal creada", HttpStatus.CREATED);
		} catch (DuplicateKeyException e) {
			return new ResponseEntity<>("Ya existe una troncal con ese nombre.", HttpStatus.CONFLICT);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Los datos de la troncal no son válidos.", HttpStatus.BAD_REQUEST);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<TroncalDTO>> todos() {
		return new ResponseEntity<>(troncalService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "nombre", required = false) String nombre) {
		TroncalDTO dto = new TroncalDTO();
		dto.setNombre(nombre);
		try {
			troncalService.updateById(id, dto);
			return new ResponseEntity<>("La troncal actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (DuplicateKeyException e) {
			return new ResponseEntity<>("Ya existe una troncal con ese nombre.", HttpStatus.CONFLICT);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Los datos de la troncal no son válidos.", HttpStatus.BAD_REQUEST);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			troncalService.deleteById(id);
			return new ResponseEntity<>("La troncal eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("No se puede eliminar la troncal porque tiene estaciones asociadas.", HttpStatus.CONFLICT);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}