package co.edu.unbosque.TransMilenio.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.InfractorDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.InfractorService;

/**
 * Controlador REST del infractor. Ruta base: /infractor. Recibe cada dato como parametro, arma el DTO y
 * llama a InfractorService. Cualquier rol con sesion puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/infractor")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class InfractorController {

	/**
	 * Servicio del infractor que usa esta clase.
	 */
	private InfractorService infractorService;

	/**
	 * Crea el controlador.
	 *
	 * @param infractorService el servicio del infractor
	 */
	public InfractorController(InfractorService infractorService) {
		this.infractorService = infractorService;
	}

	/**
	 * POST /infractor/crear. Crea un infractor nuevo.
	 *
	 * @param tipoDocumento el tipo de documento del infractor (por ejemplo, CC)
	 * @param numeroDocumento el numero de documento del infractor
	 * @param nombre el nombre del infractor
	 * @param apellido el apellido del infractor
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
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

	/**
	 * GET /infractor/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<InfractorDTO>> todos() {
		return new ResponseEntity<>(infractorService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /infractor/actualizar/{id}. Actualiza un infractor existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param tipoDocumento el tipo de documento del infractor (por ejemplo, CC)
	 * @param numeroDocumento el numero de documento del infractor
	 * @param nombre el nombre del infractor
	 * @param apellido el apellido del infractor
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
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

	/**
	 * DELETE /infractor/eliminar/{id}. Elimina un infractor.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
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
