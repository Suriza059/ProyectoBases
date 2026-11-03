package co.edu.unbosque.TransMilenio.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.dto.TarifaDTO;
import co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.TarifaService;

/**
 * Controlador REST de la tarifa. Ruta base: /tarifa. Recibe cada dato como parametro, arma el DTO y llama a
 * TarifaService. Todos los roles con sesion pueden consultar (GET), pero solo el Administrador puede crear,
 * cambiar o borrar.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/tarifa")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class TarifaController {

	/**
	 * Servicio de la tarifa que usa esta clase.
	 */
	private TarifaService tarifaService;

	/**
	 * Crea el controlador.
	 *
	 * @param tarifaService el servicio de la tarifa
	 */
	public TarifaController(TarifaService tarifaService) {
		this.tarifaService = tarifaService;
	}

	/**
	 * POST /tarifa/crear. Crea una tarifa nueva.
	 *
	 * @param fechaInicio la fecha desde la cual rige la tarifa
	 * @param fechaFin la fecha hasta la cual rige la tarifa
	 * @param valor el valor de la tarifa
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
	@PostMapping("/crear")
	public ResponseEntity<String> crear(
			@RequestParam(name = "fechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam(name = "fechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
			@RequestParam(name = "valor", required = false) Long valor) {
		TarifaDTO dto = new TarifaDTO();
		dto.setFechaInicio(fechaInicio);
		dto.setFechaFin(fechaFin);
		dto.setValor(valor);
		try {
			tarifaService.create(dto);
			return new ResponseEntity<>("La tarifa creada", HttpStatus.CREATED);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * GET /tarifa/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<TarifaDTO>> todos() {
		return new ResponseEntity<>(tarifaService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /tarifa/actualizar/{id}. Actualiza una tarifa existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param fechaInicio la fecha desde la cual rige la tarifa
	 * @param fechaFin la fecha hasta la cual rige la tarifa
	 * @param valor el valor de la tarifa
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
	@PutMapping("/actualizar/{id}")
	public ResponseEntity<String> actualizar(
			@PathVariable Long id,
			@RequestParam(name = "fechaInicio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
			@RequestParam(name = "fechaFin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
			@RequestParam(name = "valor", required = false) Long valor) {
		TarifaDTO dto = new TarifaDTO();
		dto.setFechaInicio(fechaInicio);
		dto.setFechaFin(fechaFin);
		dto.setValor(valor);
		try {
			tarifaService.updateById(id, dto);
			return new ResponseEntity<>("La tarifa actualizada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * DELETE /tarifa/eliminar/{id}. Elimina una tarifa.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<String> eliminar(@PathVariable Long id) {
		try {
			tarifaService.deleteById(id);
			return new ResponseEntity<>("La tarifa eliminada", HttpStatus.OK);
		} catch (RegistroNoExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
}
