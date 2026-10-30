package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.PagoDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.PagoService;

@RestController
@RequestMapping("/pago")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class PagoController {

	private PagoService pagoService;

	public PagoController(PagoService pagoService) {
		this.pagoService = pagoService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "valor", required = false) Long valor,
			@RequestParam(name = "numeroRecibo", required = false) String numeroRecibo,
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "idComparendo", required = false) Long idComparendo) {
		PagoDTO dto = new PagoDTO();
		dto.setValor(valor);
		dto.setNumeroRecibo(numeroRecibo);
		dto.setFecha(fecha);
		dto.setIdComparendo(idComparendo);
		try {
			pagoService.create(dto);
			return new ResponseEntity<>("El pago creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<PagoDTO>> todos() {
		return new ResponseEntity<>(pagoService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "valor", required = false) Long valor,
			@RequestParam(name = "numeroRecibo", required = false) String numeroRecibo,
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
			@RequestParam(name = "idComparendo", required = false) Long idComparendo) {
		PagoDTO dto = new PagoDTO();
		dto.setValor(valor);
		dto.setNumeroRecibo(numeroRecibo);
		dto.setFecha(fecha);
		dto.setIdComparendo(idComparendo);
		try {
			pagoService.updateById(id, dto);
			return new ResponseEntity<>("El pago actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			pagoService.deleteById(id);
			return new ResponseEntity<>("El pago eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
