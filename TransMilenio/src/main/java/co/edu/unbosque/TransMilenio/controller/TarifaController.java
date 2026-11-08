package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.format.annotation.DateTimeFormat;
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
	public ResponseEntity<String> crear(
			@RequestParam(name = "fechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam(name = "fechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
			@RequestParam(name = "valor", required = false) Long valor) {
		TarifaDTO dto = new TarifaDTO();
		dto.setFechaInicio(fechaInicio);
		dto.setFechaFin(fechaFin);
		dto.setValor(valor);
		try {
			tarifaService.create(dto);
			return new ResponseEntity<>("La tarifa creada", HttpStatus.CREATED);
		} catch (DuplicateKeyException e) {
			return new ResponseEntity<>("Ya existe una tarifa con esos datos.", HttpStatus.CONFLICT);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Los datos de la tarifa no son válidos: el valor debe ser mayor que cero y la fecha final no puede ser antes de la inicial.", HttpStatus.BAD_REQUEST);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<TarifaDTO>> todos() {
		return new ResponseEntity<>(tarifaService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "fechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam(name = "fechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
			@RequestParam(name = "valor", required = false) Long valor) {
		TarifaDTO dto = new TarifaDTO();
		dto.setFechaInicio(fechaInicio);
		dto.setFechaFin(fechaFin);
		dto.setValor(valor);
		try {
			tarifaService.updateById(id, dto);
			return new ResponseEntity<>("La tarifa actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (DuplicateKeyException e) {
			return new ResponseEntity<>("Ya existe una tarifa con esos datos.", HttpStatus.CONFLICT);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("Los datos de la tarifa no son válidos: el valor debe ser mayor que cero y la fecha final no puede ser antes de la inicial.", HttpStatus.BAD_REQUEST);
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
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<>("No se puede eliminar la tarifa porque ya hay colados registrados con ella.", HttpStatus.CONFLICT);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}