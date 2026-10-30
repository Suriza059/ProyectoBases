package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.EstacionDTO;
import co.edu.unbosque.TransMilenio.entity.Estacion;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.EstacionRepository;
import co.edu.unbosque.TransMilenio.repository.TroncalRepository;

public class EstacionService implements CRUDOPERATION<EstacionDTO> {

	private EstacionRepository estacionRepository;
	private TroncalRepository troncalRepository;

	public EstacionService(EstacionRepository estacionRepository, TroncalRepository troncalRepository) {
		this.estacionRepository = estacionRepository;
		this.troncalRepository = troncalRepository;
	}

	private void validar(EstacionDTO data) {
		if (data.getIdTroncal() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idTroncal es obligatorio");
		if (!troncalRepository.existe(data.getIdTroncal()))
			LanzadorDeExcepciones.relacionNoExiste("La troncal no existe");
	}

	private Estacion aEntity(EstacionDTO dto) {
		Estacion entity = new Estacion();
		entity.setId(dto.getId());
		entity.setNombre(dto.getNombre());
		entity.setActiva(dto.getActiva());
		entity.setIdTroncal(dto.getIdTroncal());
		return entity;
	}

	private EstacionDTO aDTO(Estacion entity) {
		EstacionDTO dto = new EstacionDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		dto.setActiva(entity.getActiva());
		dto.setIdTroncal(entity.getIdTroncal());
		return dto;
	}

	@Override
	public void create(EstacionDTO data) {
		if (estacionRepository.buscarPorNombre(data.getNombre()) != null)
			LanzadorDeExcepciones.registroDuplicado("Ya existe una estacion con ese nombre");
		validar(data);
		Estacion entity = aEntity(data);
		entity.setId(null);
		estacionRepository.guardar(entity);
	}

	@Override
	public List<EstacionDTO> getAll() {
		List<EstacionDTO> dtoList = new ArrayList<>();
		for (Estacion e : estacionRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!estacionRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La estacion");
		estacionRepository.eliminar(id);
	}

	@Override
	public long count() {
		return estacionRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return estacionRepository.existe(id);
	}

	@Override
	public void updateById(Long id, EstacionDTO data) {
		if (!estacionRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La estacion");
		validar(data);
		Estacion otro = estacionRepository.buscarPorNombre(data.getNombre());
		if (otro != null && !otro.getId().equals(id))
			LanzadorDeExcepciones.registroDuplicado("Ya existe una estacion con ese nombre");
		Estacion entity = aEntity(data);
		entity.setId(id);
		estacionRepository.guardar(entity);
	}
}
