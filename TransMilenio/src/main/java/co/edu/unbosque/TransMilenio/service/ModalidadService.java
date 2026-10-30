package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.ModalidadDTO;
import co.edu.unbosque.TransMilenio.entity.Modalidad;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.EventoRepository;
import co.edu.unbosque.TransMilenio.repository.ModalidadRepository;

public class ModalidadService implements CRUDOPERATION<ModalidadDTO> {

	private ModalidadRepository modalidadRepository;
	private EventoRepository eventoRepository;

	public ModalidadService(ModalidadRepository modalidadRepository, EventoRepository eventoRepository) {
		this.modalidadRepository = modalidadRepository;
		this.eventoRepository = eventoRepository;
	}

	private void validar(ModalidadDTO data) {
		if (data.getIdEvento() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEvento es obligatorio");
		if (!eventoRepository.existe(data.getIdEvento()))
			LanzadorDeExcepciones.relacionNoExiste("El evento no existe");
	}

	private Modalidad aEntity(ModalidadDTO dto) {
		Modalidad entity = new Modalidad();
		entity.setId(dto.getId());
		entity.setNombre(dto.getNombre());
		entity.setIdEvento(dto.getIdEvento());
		return entity;
	}

	private ModalidadDTO aDTO(Modalidad entity) {
		ModalidadDTO dto = new ModalidadDTO();
		dto.setId(entity.getId());
		dto.setNombre(entity.getNombre());
		dto.setIdEvento(entity.getIdEvento());
		return dto;
	}

	@Override
	public void create(ModalidadDTO data) {
		validar(data);
		Modalidad entity = aEntity(data);
		entity.setId(null);
		modalidadRepository.guardar(entity);
	}

	@Override
	public List<ModalidadDTO> getAll() {
		List<ModalidadDTO> dtoList = new ArrayList<>();
		for (Modalidad e : modalidadRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!modalidadRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La modalidad");
		modalidadRepository.eliminar(id);
	}

	@Override
	public long count() {
		return modalidadRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return modalidadRepository.existe(id);
	}

	@Override
	public void updateById(Long id, ModalidadDTO data) {
		if (!modalidadRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La modalidad");
		validar(data);
		Modalidad entity = aEntity(data);
		entity.setId(id);
		modalidadRepository.guardar(entity);
	}
}
