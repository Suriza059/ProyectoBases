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

/**
 * Controlador REST del comparendo. Ruta base: /comparendo. Recibe cada dato como parametro, arma el DTO y
 * llama a ComparendoService. Cualquier rol con sesion puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/comparendo")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class ComparendoController {

	/**
	 * Servicio del comparendo que usa esta clase.
	 */
	private ComparendoService comparendoService;

	/**
	 * Crea el controlador.
	 *
	 * @param comparendoService el servicio del comparendo
	 */
	public ComparendoController(ComparendoService comparendoService) {
		this.comparendoService = comparendoService;
	}

	/**
	 * POST /comparendo/crear. Crea un comparendo nuevo.
	 *
	 * @param fecha la fecha del comparendo
	 * @param valor el valor del comparendo
	 * @param estado el estado del comparendo (por ejemplo, Pendiente o Pagado)
	 * @param idEventoInfractor el identificador de la relacion evento-infractor a la que pertenece el
	 *     comparendo (llave foranea)
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
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

	/**
	 * GET /comparendo/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<ComparendoDTO>> todos() {
		return new ResponseEntity<>(comparendoService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /comparendo/actualizar/{id}. Actualiza un comparendo existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param fecha la fecha del comparendo
	 * @param valor el valor del comparendo
	 * @param estado el estado del comparendo (por ejemplo, Pendiente o Pagado)
	 * @param idEventoInfractor el identificador de la relacion evento-infractor a la que pertenece el
	 *     comparendo (llave foranea)
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
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

	/**
	 * DELETE /comparendo/eliminar/{id}. Elimina un comparendo.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
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
