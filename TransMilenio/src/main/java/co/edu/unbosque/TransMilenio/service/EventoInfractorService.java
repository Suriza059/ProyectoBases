package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.EventoInfractorDTO;
import co.edu.unbosque.TransMilenio.entity.EventoInfractor;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.EventoInfractorRepository;
import co.edu.unbosque.TransMilenio.repository.EventoRepository;
import co.edu.unbosque.TransMilenio.repository.InfractorRepository;

public class EventoInfractorService implements CRUDOPERATION<EventoInfractorDTO> {

	private EventoInfractorRepository eventoInfractorRepository;
	private EventoRepository eventoRepository;
	private InfractorRepository infractorRepository;

	public EventoInfractorService(EventoInfractorRepository eventoInfractorRepository, EventoRepository eventoRepository, InfractorRepository infractorRepository) {
		this.eventoInfractorRepository = eventoInfractorRepository;
		this.eventoRepository = eventoRepository;
		this.infractorRepository = infractorRepository;
	}

	private void validar(EventoInfractorDTO data) {
		if (data.getIdEvento() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEvento es obligatorio");
		if (!eventoRepository.existe(data.getIdEvento()))
			LanzadorDeExcepciones.relacionNoExiste("El evento no existe");
		if (data.getIdInfractor() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idInfractor es obligatorio");
		if (!infractorRepository.existe(data.getIdInfractor()))
			LanzadorDeExcepciones.relacionNoExiste("El infractor no existe");
	}

	private EventoInfractor aEntity(EventoInfractorDTO dto) {
		EventoInfractor entity = new EventoInfractor();
		entity.setId(dto.getId());
		entity.setIdEvento(dto.getIdEvento());
		entity.setIdInfractor(dto.getIdInfractor());
		return entity;
	}

	private EventoInfractorDTO aDTO(EventoInfractor entity) {
		EventoInfractorDTO dto = new EventoInfractorDTO();
		dto.setId(entity.getId());
		dto.setIdEvento(entity.getIdEvento());
		dto.setIdInfractor(entity.getIdInfractor());
		return dto;
	}

	@Override
	public void create(EventoInfractorDTO data) {
		if (eventoInfractorRepository.buscarPorIdEventoYIdInfractor(data.getIdEvento(), data.getIdInfractor()) != null)
			LanzadorDeExcepciones.registroDuplicado("Esa pareja evento-infractor ya existe");
		validar(data);
		EventoInfractor entity = aEntity(data);
		entity.setId(null);
		eventoInfractorRepository.guardar(entity);
	}

	@Override
	public List<EventoInfractorDTO> getAll() {
		List<EventoInfractorDTO> dtoList = new ArrayList<>();
		for (EventoInfractor e : eventoInfractorRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!eventoInfractorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La pareja evento-infractor");
		eventoInfractorRepository.eliminar(id);
	}

	@Override
	public long count() {
		return eventoInfractorRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return eventoInfractorRepository.existe(id);
	}

	@Override
	public void updateById(Long id, EventoInfractorDTO data) {
		if (!eventoInfractorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("La pareja evento-infractor");
		validar(data);
		EventoInfractor otro = eventoInfractorRepository.buscarPorIdEventoYIdInfractor(data.getIdEvento(), data.getIdInfractor());
		if (otro != null && !otro.getId().equals(id))
			LanzadorDeExcepciones.registroDuplicado("Esa pareja evento-infractor ya existe");
		EventoInfractor entity = aEntity(data);
		entity.setId(id);
		eventoInfractorRepository.guardar(entity);
	}
}
