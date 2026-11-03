package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.PagoDTO;
import co.edu.unbosque.TransMilenio.entity.Pago;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.ComparendoRepository;
import co.edu.unbosque.TransMilenio.repository.PagoRepository;

/**
 * Logica de negocio del pago: crear, consultar, actualizar y eliminar. Convierte entre PagoDTO y la entidad
 * Pago, valida los datos con LanzadorDeExcepciones y guarda mediante PagoRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Relacion: El comparendo no existe</li>
 * <li>Dato obligatorio: El campo idComparendo es obligatorio</li>
 * </ul>
 */
public class PagoService implements CRUDOPERATION<PagoDTO> {

	/**
	 * Repositorio del pago que usa esta clase.
	 */
	private PagoRepository pagoRepository;
	/**
	 * Repositorio del comparendo que usa esta clase.
	 */
	private ComparendoRepository comparendoRepository;

	/**
	 * Crea el servicio del pago.
	 *
	 * @param pagoRepository el repositorio del pago
	 * @param comparendoRepository el repositorio del comparendo
	 */
	public PagoService(PagoRepository pagoRepository, ComparendoRepository comparendoRepository) {
		this.pagoRepository = pagoRepository;
		this.comparendoRepository = comparendoRepository;
	}

	/**
	 * Comprueba que las llaves foraneas obligatorias esten presentes y que apunten a registros que existen.
	 *
	 * @param data los datos a validar
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	private void validar(PagoDTO data) {
		if (data.getIdComparendo() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idComparendo es obligatorio");
		if (!comparendoRepository.existe(data.getIdComparendo()))
			LanzadorDeExcepciones.relacionNoExiste("El comparendo no existe");
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Pago aEntity(PagoDTO dto) {
		Pago entity = new Pago();
		entity.setId(dto.getId());
		entity.setValor(dto.getValor());
		entity.setNumeroRecibo(dto.getNumeroRecibo());
		entity.setFecha(dto.getFecha());
		entity.setIdComparendo(dto.getIdComparendo());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private PagoDTO aDTO(Pago entity) {
		PagoDTO dto = new PagoDTO();
		dto.setId(entity.getId());
		dto.setValor(entity.getValor());
		dto.setNumeroRecibo(entity.getNumeroRecibo());
		dto.setFecha(entity.getFecha());
		dto.setIdComparendo(entity.getIdComparendo());
		return dto;
	}

	/**
	 * Crea un pago nuevo. Valida los datos y el sistema le asigna el id.
	 *
	 * @param data los datos del pago a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(PagoDTO data) {
		validar(data);
		Pago entity = aEntity(data);
		entity.setId(null);
		pagoRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<PagoDTO> getAll() {
		List<PagoDTO> dtoList = new ArrayList<>();
		for (Pago e : pagoRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina un pago por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!pagoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El pago");
		pagoRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return pagoRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return pagoRepository.existe(id);
	}

	/**
	 * Actualiza un pago existente. Valida los datos igual que al crear.
	 *
	 * @param id el identificador del registro a actualizar
	 * @param data los datos nuevos
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void updateById(Long id, PagoDTO data) {
		if (!pagoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El pago");
		validar(data);
		Pago entity = aEntity(data);
		entity.setId(id);
		pagoRepository.guardar(entity);
	}
}
