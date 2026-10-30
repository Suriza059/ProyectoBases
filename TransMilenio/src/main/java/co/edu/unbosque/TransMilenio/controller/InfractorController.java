package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.InfractorDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.InfractorService;

@RestController
@RequestMapping("/infractor")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class InfractorController {

	private InfractorService infractorService;

	public InfractorController(InfractorService infractorService) {
		this.infractorService = infractorService;
	}

	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "tipoDocumento", required = false) String tipoDocumento,
			@RequestParam(name = "numeroDocumento", required = false) String numeroDocumento,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "apellido", required = false) String apellido) {
		InfractorDTO dto = new InfractorDTO();
		dto.setTipoDocumento(tipoDocumento);
		dto.setNumeroDocumento(numeroDocumento);
		dto.setNombre(nombre);
		dto.setApellido(apellido);
		try {
			infractorService.create(dto);
			return new ResponseEntity<>("El infractor creado", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/todos")
	public ResponseEntity<List<InfractorDTO>> todos() {
		return new ResponseEntity<>(infractorService.getAll(), HttpStatus.OK);
	}

	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "tipoDocumento", required = false) String tipoDocumento,
			@RequestParam(name = "numeroDocumento", required = false) String numeroDocumento,
			@RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "apellido", required = false) String apellido) {
		InfractorDTO dto = new InfractorDTO();
		dto.setTipoDocumento(tipoDocumento);
		dto.setNumeroDocumento(numeroDocumento);
		dto.setNombre(nombre);
		dto.setApellido(apellido);
		try {
			infractorService.updateById(id, dto);
			return new ResponseEntity<>("El infractor actualizado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			infractorService.deleteById(id);
			return new ResponseEntity<>("El infractor eliminado", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
