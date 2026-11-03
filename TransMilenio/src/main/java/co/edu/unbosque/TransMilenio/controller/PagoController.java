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

/**
 * Controlador REST del pago. Ruta base: /pago. Recibe cada dato como parametro, arma el DTO y llama a
 * PagoService. Cualquier rol con sesion puede usar estas rutas.
 *
 * <p>Las excepciones del sistema se convierten en respuestas HTTP: 404 si el registro no existe y 400 para
 * el resto de errores.
 */
@RestController
@RequestMapping("/pago")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class PagoController {

	/**
	 * Servicio del pago que usa esta clase.
	 */
	private PagoService pagoService;

	/**
	 * Crea el controlador.
	 *
	 * @param pagoService el servicio del pago
	 */
	public PagoController(PagoService pagoService) {
		this.pagoService = pagoService;
	}

	/**
	 * POST /pago/crear. Crea un pago nuevo.
	 *
	 * @param valor el valor pagado
	 * @param numeroRecibo el numero del recibo de pago
	 * @param fecha la fecha del pago
	 * @param idComparendo el identificador del comparendo que se paga (llave foranea)
	 * @return 201 con un mensaje si se creo, o 400 con el mensaje del error
	 */
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

	/**
	 * GET /pago/todos. Lista todos los registros.
	 *
	 * @return 200 con la lista de registros
	 */
	@GetMapping("/todos")
	public ResponseEntity<List<PagoDTO>> todos() {
		return new ResponseEntity<>(pagoService.getAll(), HttpStatus.OK);
	}

	/**
	 * PUT /pago/actualizar/{id}. Actualiza un pago existente.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param valor el valor pagado
	 * @param numeroRecibo el numero del recibo de pago
	 * @param fecha la fecha del pago
	 * @param idComparendo el identificador del comparendo que se paga (llave foranea)
	 * @return 200 con un mensaje si se actualizo, 404 si no existe, o 400 con el mensaje del error
	 */
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

	/**
	 * DELETE /pago/eliminar/{id}. Elimina un pago.
	 *
	 * @param id el identificador del registro a eliminar
	 * @return 200 con un mensaje si se elimino, 404 si no existe, o 400 con el mensaje del error
	 */
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
