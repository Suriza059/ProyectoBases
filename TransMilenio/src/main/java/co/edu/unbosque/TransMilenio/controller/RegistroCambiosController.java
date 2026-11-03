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

/**
 * Controlador REST del registro de cambios. Ruta base: /registroCambios. Recibe cada dato como parametro,
 * arma el DTO y llama a RegistroCambiosService. Solo el Administrador puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/registroCambios")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class RegistroCambiosController {

	/**
	 * Servicio del registro de cambios que usa esta clase.
	 */
	private RegistroCambiosService registroCambiosService;

	/**
	 * Crea el controlador.
	 *
	 * @param registroCambiosService el servicio del registro de cambios
	 */
	public RegistroCambiosController(RegistroCambiosService registroCambiosService) {
		this.registroCambiosService = registroCambiosService;
	}

	/**
	 * POST /registroCambios/crear. Crea un registro de cambios nuevo.
	 *
	 * @param fecha la fecha y hora en que se hizo el cambio
	 * @param campoModificado el nombre del campo que se modifico
	 * @param valorAnterior el valor que tenia el campo antes del cambio
	 * @param valorNuevo el valor que tiene el campo despues del cambio
	 * @param idUsuario el identificador del usuario que hizo el cambio (llave foranea)
	 * @param idComparendo el identificador del comparendo modificado (llave foranea)
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
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

	/**
	 * GET /registroCambios/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<RegistroCambiosDTO>> todos() {
		return new ResponseEntity<>(registroCambiosService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /registroCambios/actualizar/{id}. Actualiza un registro de cambios existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param fecha la fecha y hora en que se hizo el cambio
	 * @param campoModificado el nombre del campo que se modifico
	 * @param valorAnterior el valor que tenia el campo antes del cambio
	 * @param valorNuevo el valor que tiene el campo despues del cambio
	 * @param idUsuario el identificador del usuario que hizo el cambio (llave foranea)
	 * @param idComparendo el identificador del comparendo modificado (llave foranea)
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
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

	/**
	 * DELETE /registroCambios/eliminar/{id}. Elimina un registro de cambios.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
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
