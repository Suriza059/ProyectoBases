package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.RegistroCambiosDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.RegistroCambiosService;

@RestController
@RequestMapping("/registroCambios")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class RegistroCambiosController {

	private RegistroCambiosService registroCambiosService;

	public RegistroCambiosController(RegistroCambiosService registroCambiosService) {
		this.registroCambiosService = registroCambiosService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
			@RequestParam(name = "campoModificado", required = false) String campoModificado,
			@RequestParam(name = "valorAnterior", required = false) String valorAnterior,
			@RequestParam(name = "valorNuevo", required = false) String valorNuevo,
			@RequestParam(name = "idUsuario", required = false) Long idUsuario,
			@RequestParam(name = "idComparendo", required = false) Long idComparendo) {
		RegistroCambiosDTO dto = new RegistroCambiosDTO();
		dto.setFecha(fecha);
		dto.setCampoModificado(campoModificado);
		dto.setValorAnterior(valorAnterior);
		dto.setValorNuevo(valorNuevo);
		dto.setIdUsuario(idUsuario);
		dto.setIdComparendo(idComparendo);
		try {
			registroCambiosService.create(dto);
			return new ResponseEntity<>("El registro de cambios creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<RegistroCambiosDTO>> todos() {
		return new ResponseEntity<>(registroCambiosService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fecha,
			@RequestParam(name = "campoModificado", required = false) String campoModificado,
			@RequestParam(name = "valorAnterior", required = false) String valorAnterior,
			@RequestParam(name = "valorNuevo", required = false) String valorNuevo,
			@RequestParam(name = "idUsuario", required = false) Long idUsuario,
			@RequestParam(name = "idComparendo", required = false) Long idComparendo) {
		RegistroCambiosDTO dto = new RegistroCambiosDTO();
		dto.setFecha(fecha);
		dto.setCampoModificado(campoModificado);
		dto.setValorAnterior(valorAnterior);
		dto.setValorNuevo(valorNuevo);
		dto.setIdUsuario(idUsuario);
		dto.setIdComparendo(idComparendo);
		try {
			registroCambiosService.updateById(id, dto);
			return new ResponseEntity<>("El registro de cambios actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			registroCambiosService.deleteById(id);
			return new ResponseEntity<>("El registro de cambios eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
