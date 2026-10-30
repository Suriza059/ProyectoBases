package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.AgresionDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.AgresionService;

@RestController
@RequestMapping("/agresion")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class AgresionController {

	private AgresionService agresionService;

	public AgresionController(AgresionService agresionService) {
		this.agresionService = agresionService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "gravedad", required = false) String gravedad,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "idEvento", required = false) Long idEvento,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		AgresionDTO dto = new AgresionDTO();
		dto.setFecha(fecha);
		dto.setGravedad(gravedad);
		dto.setDescripcion(descripcion);
		dto.setIdEvento(idEvento);
		dto.setIdGuarda(idGuarda);
		try {
			agresionService.create(dto);
			return new ResponseEntity<>("La agresion creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<AgresionDTO>> todos() {
		return new ResponseEntity<>(agresionService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "gravedad", required = false) String gravedad,
			@RequestParam(name = "descripcion", required = false) String descripcion,
			@RequestParam(name = "idEvento", required = false) Long idEvento,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		AgresionDTO dto = new AgresionDTO();
		dto.setFecha(fecha);
		dto.setGravedad(gravedad);
		dto.setDescripcion(descripcion);
		dto.setIdEvento(idEvento);
		dto.setIdGuarda(idGuarda);
		try {
			agresionService.updateById(id, dto);
			return new ResponseEntity<>("La agresion actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			agresionService.deleteById(id);
			return new ResponseEntity<>("La agresion eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
