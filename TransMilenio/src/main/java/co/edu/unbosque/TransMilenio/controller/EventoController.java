package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.EventoDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.EventoService;

@RestController
@RequestMapping("/evento")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class EventoController {

	private EventoService eventoService;

	public EventoController(EventoService eventoService) {
		this.eventoService = eventoService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "numeroPersonas", required = false) Integer numeroPersonas,
			@RequestParam(name = "idEstacion", required = false) Long idEstacion,
			@RequestParam(name = "idTarifa", required = false) Long idTarifa,
			@RequestParam(name = "idModalidad", required = false) Long idModalidad,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		EventoDTO dto = new EventoDTO();
		dto.setFecha(fecha);
		dto.setNumeroPersonas(numeroPersonas);
		dto.setIdEstacion(idEstacion);
		dto.setIdTarifa(idTarifa);
		dto.setIdModalidad(idModalidad);
		dto.setIdGuarda(idGuarda);
		try {
			eventoService.create(dto);
			return new ResponseEntity<>("El evento creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<EventoDTO>> todos() {
		return new ResponseEntity<>(eventoService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "numeroPersonas", required = false) Integer numeroPersonas,
			@RequestParam(name = "idEstacion", required = false) Long idEstacion,
			@RequestParam(name = "idTarifa", required = false) Long idTarifa,
			@RequestParam(name = "idModalidad", required = false) Long idModalidad,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		EventoDTO dto = new EventoDTO();
		dto.setFecha(fecha);
		dto.setNumeroPersonas(numeroPersonas);
		dto.setIdEstacion(idEstacion);
		dto.setIdTarifa(idTarifa);
		dto.setIdModalidad(idModalidad);
		dto.setIdGuarda(idGuarda);
		try {
			eventoService.updateById(id, dto);
			return new ResponseEntity<>("El evento actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			eventoService.deleteById(id);
			return new ResponseEntity<>("El evento eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
