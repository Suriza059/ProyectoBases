package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.ComparendoDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.ComparendoService;

@RestController
@RequestMapping("/comparendo")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class ComparendoController {

	private ComparendoService comparendoService;

	public ComparendoController(ComparendoService comparendoService) {
		this.comparendoService = comparendoService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "valor", required = false) Long valor,
			@RequestParam(name = "estado", required = false) String estado,
			@RequestParam(name = "idEventoInfractor", required = false) Long idEventoInfractor) {
		ComparendoDTO dto = new ComparendoDTO();
		dto.setFecha(fecha);
		dto.setValor(valor);
		dto.setEstado(estado);
		dto.setIdEventoInfractor(idEventoInfractor);
		try {
			comparendoService.create(dto);
			return new ResponseEntity<>("El comparendo creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<ComparendoDTO>> todos() {
		return new ResponseEntity<>(comparendoService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "valor", required = false) Long valor,
			@RequestParam(name = "estado", required = false) String estado,
			@RequestParam(name = "idEventoInfractor", required = false) Long idEventoInfractor) {
		ComparendoDTO dto = new ComparendoDTO();
		dto.setFecha(fecha);
		dto.setValor(valor);
		dto.setEstado(estado);
		dto.setIdEventoInfractor(idEventoInfractor);
		try {
			comparendoService.updateById(id, dto);
			return new ResponseEntity<>("El comparendo actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			comparendoService.deleteById(id);
			return new ResponseEntity<>("El comparendo eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
