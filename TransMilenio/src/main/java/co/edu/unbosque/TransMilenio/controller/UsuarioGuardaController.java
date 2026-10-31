package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.UsuarioGuardaDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.UsuarioGuardaService;

@RestController
@RequestMapping("/usuarioGuarda")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class UsuarioGuardaController {

	private UsuarioGuardaService usuarioGuardaService;

	public UsuarioGuardaController(UsuarioGuardaService usuarioGuardaService) {
		this.usuarioGuardaService = usuarioGuardaService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "idUsuario", required = false) Long idUsuario,
			@RequestParam(name = "fechaActivacion", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaActivacion,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		UsuarioGuardaDTO dto = new UsuarioGuardaDTO();
		dto.setIdUsuario(idUsuario);
		dto.setFechaActivacion(fechaActivacion);
		dto.setIdGuarda(idGuarda);
		try {
			usuarioGuardaService.create(dto);
			return new ResponseEntity<>("El guarda activado creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<UsuarioGuardaDTO>> todos() {
		return new ResponseEntity<>(usuarioGuardaService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "fechaActivacion", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaActivacion,
			@RequestParam(name = "idGuarda", required = false) Long idGuarda) {
		UsuarioGuardaDTO dto = new UsuarioGuardaDTO();
		dto.setFechaActivacion(fechaActivacion);
		dto.setIdGuarda(idGuarda);
		try {
			usuarioGuardaService.updateById(id, dto);
			return new ResponseEntity<>("El guarda activado actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			usuarioGuardaService.deleteById(id);
			return new ResponseEntity<>("El guarda activado eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
