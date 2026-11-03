package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.AnalistaDTO;
import co.edu.unbosque.TransMilenio.entity.Analista;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.AnalistaRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;

/**
 * Logica de negocio del analista: crear, consultar, actualizar y eliminar. Convierte entre AnalistaDTO y la
 * entidad Analista, valida los datos con LanzadorDeExcepciones y guarda mediante AnalistaRepository.
 *
 * <p>Reglas de validacion:
 * <ul>
 * <li>Unicidad: Ese usuario ya es analista</li>
 * <li>Relacion: El usuario no existe</li>
 * <li>Dato obligatorio: El campo idUsuario es obligatorio</li>
 * </ul>
 */
public class AnalistaService implements CRUDOPERATION<AnalistaDTO> {

	/**
	 * Repositorio del analista que usa esta clase.
	 */
	private AnalistaRepository analistaRepository;
	/**
	 * Repositorio del usuario que usa esta clase.
	 */
	private UsuarioRepository usuarioRepository;

	/**
	 * Crea el servicio del analista.
	 *
	 * @param analistaRepository el repositorio del analista
	 * @param usuarioRepository el repositorio del usuario
	 */
	public AnalistaService(AnalistaRepository analistaRepository, UsuarioRepository usuarioRepository) {
		this.analistaRepository = analistaRepository;
		this.usuarioRepository = usuarioRepository;
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
	private void validar(AnalistaDTO data) {
		if (data.getIdUsuario() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idUsuario es obligatorio");
		if (!usuarioRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.relacionNoExiste("El usuario no existe");
	}

	/**
	 * Convierte el DTO en entidad.
	 *
	 * @param dto el DTO con los datos
	 * @return la entidad con los mismos datos
	 */
	private Analista aEntity(AnalistaDTO dto) {
		Analista entity = new Analista();
		entity.setIdUsuario(dto.getIdUsuario());
		entity.setAreaAnalisis(dto.getAreaAnalisis());
		return entity;
	}

	/**
	 * Convierte la entidad en DTO.
	 *
	 * @param entity la entidad a convertir
	 * @return el DTO con los mismos datos
	 */
	private AnalistaDTO aDTO(Analista entity) {
		AnalistaDTO dto = new AnalistaDTO();
		dto.setIdUsuario(entity.getIdUsuario());
		dto.setAreaAnalisis(entity.getAreaAnalisis());
		return dto;
	}

	/**
	 * Crea un analista nuevo para un usuario. Valida los datos y que ese usuario no tenga ya el registro.
	 *
	 * @param data los datos del analista a crear
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroDuplicadoException si ya existe un registro
	 *     con los mismos datos unicos
	 * @throws co.edu.unbosque.TransMilenio.exception.DatoInvalidoException si falta un dato obligatorio o
	 *     un dato no es valido
	 * @throws co.edu.unbosque.TransMilenio.exception.RelacionNoExisteException si una llave foranea apunta
	 *     a un registro que no existe
	 */
	@Override
	public void create(AnalistaDTO data) {
		validar(data);
		if (analistaRepository.existe(data.getIdUsuario()))
			LanzadorDeExcepciones.registroDuplicado("Ese usuario ya es analista");
		Analista entity = aEntity(data);
		analistaRepository.guardar(entity);
	}

	/**
	 * Obtiene todos los registros como DTO.
	 *
	 * @return la lista de DTO, vacia si no hay registros
	 */
	@Override
	public List<AnalistaDTO> getAll() {
		List<AnalistaDTO> dtoList = new ArrayList<>();
		for (Analista e : analistaRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	/**
	 * Elimina un analista por su identificador.
	 *
	 * @param id el identificador del registro a eliminar
	 * @throws co.edu.unbosque.TransMilenio.exception.RegistroNoExisteException si el registro no existe
	 */
	@Override
	public void deleteById(Long id) {
		if (!analistaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El analista");
		analistaRepository.eliminar(id);
	}

	/**
	 * Cuenta los registros.
	 *
	 * @return la cantidad de registros
	 */
	@Override
	public long count() {
		return analistaRepository.contar();
	}

	/**
	 * Indica si existe un registro con ese identificador.
	 *
	 * @param id el identificador a comprobar
	 * @return true si existe, false si no
	 */
	@Override
	public boolean exist(Long id) {
		return analistaRepository.existe(id);
	}

	/**
	 * Actualiza un analista existente. Valida los datos igual que al crear.
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
	public void updateById(Long id, AnalistaDTO data) {
		if (!analistaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El analista");
		data.setIdUsuario(id);
		validar(data);
		Analista entity = aEntity(data);
		entity.setIdUsuario(id);
		analistaRepository.guardar(entity);
	}
}
