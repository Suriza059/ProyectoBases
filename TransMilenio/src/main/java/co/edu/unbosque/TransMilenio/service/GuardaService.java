package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.GuardaDTO;
import co.edu.unbosque.TransMilenio.entity.Guarda;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.EstacionRepository;
import co.edu.unbosque.TransMilenio.repository.GuardaRepository;

public class GuardaService implements CRUDOPERATION<GuardaDTO> {

	private GuardaRepository guardaRepository;
	private EstacionRepository estacionRepository;

	public GuardaService(GuardaRepository guardaRepository, EstacionRepository estacionRepository) {
		this.guardaRepository = guardaRepository;
		this.estacionRepository = estacionRepository;
	}

	private void validar(GuardaDTO data) {
		if (data.getIdEstacion() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEstacion es obligatorio");
		if (!estacionRepository.existe(data.getIdEstacion()))
			LanzadorDeExcepciones.relacionNoExiste("La estacion no existe");
		if (data.getIdSupervisor() != null && !guardaRepository.existe(data.getIdSupervisor()))
			LanzadorDeExcepciones.relacionNoExiste("El supervisor no existe");
	}

	private Guarda aEntity(GuardaDTO dto) {
		Guarda entity = new Guarda();
		entity.setId(dto.getId());
		entity.setTurno(dto.getTurno());
		entity.setNombre(dto.getNombre());
		entity.setApellido(dto.getApellido());
		entity.setActivo(dto.getActivo());
		entity.setCargo(dto.getCargo());
		entity.setIdEstacion(dto.getIdEstacion());
		entity.setIdSupervisor(dto.getIdSupervisor());
		return entity;
	}

	private GuardaDTO aDTO(Guarda entity) {
		GuardaDTO dto = new GuardaDTO();
		dto.setId(entity.getId());
		dto.setTurno(entity.getTurno());
		dto.setNombre(entity.getNombre());
		dto.setApellido(entity.getApellido());
		dto.setActivo(entity.getActivo());
		dto.setCargo(entity.getCargo());
		dto.setIdEstacion(entity.getIdEstacion());
		dto.setIdSupervisor(entity.getIdSupervisor());
		return dto;
	}

	@Override
	public void create(GuardaDTO data) {
		validar(data);
		Guarda entity = aEntity(data);
		entity.setId(null);
		guardaRepository.guardar(entity);
	}

	@Override
	public List<GuardaDTO> getAll() {
		List<GuardaDTO> dtoList = new ArrayList<>();
		for (Guarda e : guardaRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!guardaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El guarda");
		guardaRepository.eliminar(id);
	}

	@Override
	public long count() {
		return guardaRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return guardaRepository.existe(id);
	}

	@Override
	public void updateById(Long id, GuardaDTO data) {
		if (!guardaRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El guarda");
		validar(data);
		Guarda entity = aEntity(data);
		entity.setId(id);
		guardaRepository.guardar(entity);
	}
}
