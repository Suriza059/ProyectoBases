package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.EventoInfractorDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.EventoInfractorService;

@RestController
@RequestMapping("/eventoInfractor")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class EventoInfractorController {

	private EventoInfractorService eventoInfractorService;

	public EventoInfractorController(EventoInfractorService eventoInfractorService) {
		this.eventoInfractorService = eventoInfractorService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "idEvento", required = false) Long idEvento,
			@RequestParam(name = "idInfractor", required = false) Long idInfractor) {
		EventoInfractorDTO dto = new EventoInfractorDTO();
		dto.setIdEvento(idEvento);
		dto.setIdInfractor(idInfractor);
		try {
			eventoInfractorService.create(dto);
			return new ResponseEntity<>("La pareja evento-infractor creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<EventoInfractorDTO>> todos() {
		return new ResponseEntity<>(eventoInfractorService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "idEvento", required = false) Long idEvento,
			@RequestParam(name = "idInfractor", required = false) Long idInfractor) {
		EventoInfractorDTO dto = new EventoInfractorDTO();
		dto.setIdEvento(idEvento);
		dto.setIdInfractor(idInfractor);
		try {
			eventoInfractorService.updateById(id, dto);
			return new ResponseEntity<>("La pareja evento-infractor actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			eventoInfractorService.deleteById(id);
			return new ResponseEntity<>("La pareja evento-infractor eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
