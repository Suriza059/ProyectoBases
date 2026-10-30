package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.EventoInfractorDTO;
import co.edu.unbosque.TransMilenio.entity.EventoInfractor;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.EventoInfractorRepository;
import co.edu.unbosque.TransMilenio.repository.EventoRepository;
import co.edu.unbosque.TransMilenio.repository.InfractorRepository;

public class EventoInfractorService {

	private EventoInfractorRepository eventoInfractorRepository;
	private EventoRepository eventoRepository;
	private InfractorRepository infractorRepository;

	public EventoInfractorService(EventoInfractorRepository eventoInfractorRepository,
			EventoRepository eventoRepository, InfractorRepository infractorRepository) {
		this.eventoInfractorRepository = eventoInfractorRepository;
		this.eventoRepository = eventoRepository;
		this.infractorRepository = infractorRepository;
	}

	public void create(EventoInfractorDTO data) {
		if (data.getIdEvento() == null || data.getIdInfractor() == null)
			LanzadorDeExcepciones.datoInvalido("El evento y el infractor son obligatorios");
		if (!eventoRepository.existe(data.getIdEvento()))
			LanzadorDeExcepciones.relacionNoExiste("El evento no existe");
		if (!infractorRepository.existe(data.getIdInfractor()))
			LanzadorDeExcepciones.relacionNoExiste("El infractor no existe");
		if (eventoInfractorRepository.existe(data.getIdEvento(), data.getIdInfractor()))
			LanzadorDeExcepciones.registroDuplicado("Esa pareja evento-infractor ya existe");
		EventoInfractor entity = new EventoInfractor();
		entity.setIdEvento(data.getIdEvento());
		entity.setIdInfractor(data.getIdInfractor());
		eventoInfractorRepository.guardar(entity);
	}

	public List<EventoInfractorDTO> getAll() {
		List<EventoInfractorDTO> dtoList = new ArrayList<>();
		for (EventoInfractor e : eventoInfractorRepository.listar()) {
			EventoInfractorDTO dto = new EventoInfractorDTO();
			dto.setIdEvento(e.getIdEvento());
			dto.setIdInfractor(e.getIdInfractor());
			dtoList.add(dto);
		}
		return dtoList;
	}

	public void deleteById(Long idEvento, Long idInfractor) {
		if (!eventoInfractorRepository.existe(idEvento, idInfractor))
			LanzadorDeExcepciones.registroNoExiste("La pareja evento-infractor");
		eventoInfractorRepository.eliminar(idEvento, idInfractor);
	}

	public long count() {
		return eventoInfractorRepository.contar();
	}

	public boolean exist(Long idEvento, Long idInfractor) {
		return eventoInfractorRepository.existe(idEvento, idInfractor);
	}
}
