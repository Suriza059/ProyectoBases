package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.EventoDTO;
import co.edu.unbosque.TransMilenio.entity.Evento;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.EstacionRepository;
import co.edu.unbosque.TransMilenio.repository.EventoRepository;
import co.edu.unbosque.TransMilenio.repository.GuardaRepository;
import co.edu.unbosque.TransMilenio.repository.TarifaRepository;

public class EventoService implements CRUDOPERATION<EventoDTO> {

	private EventoRepository eventoRepository;
	private EstacionRepository estacionRepository;
	private GuardaRepository guardaRepository;
	private TarifaRepository tarifaRepository;

	public EventoService(EventoRepository eventoRepository, EstacionRepository estacionRepository, GuardaRepository guardaRepository, TarifaRepository tarifaRepository) {
		this.eventoRepository = eventoRepository;
		this.estacionRepository = estacionRepository;
		this.guardaRepository = guardaRepository;
		this.tarifaRepository = tarifaRepository;
	}

	private void validar(EventoDTO data) {
		if (data.getIdEstacion() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEstacion es obligatorio");
		if (!estacionRepository.existe(data.getIdEstacion()))
			LanzadorDeExcepciones.relacionNoExiste("La estacion no existe");
		if (data.getIdGuarda() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idGuarda es obligatorio");
		if (!guardaRepository.existe(data.getIdGuarda()))
			LanzadorDeExcepciones.relacionNoExiste("El guarda no existe");
		if (data.getIdTarifa() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idTarifa es obligatorio");
		if (!tarifaRepository.existe(data.getIdTarifa()))
			LanzadorDeExcepciones.relacionNoExiste("La tarifa no existe");
	}

	private Evento aEntity(EventoDTO dto) {
		Evento entity = new Evento();
		entity.setId(dto.getId());
		entity.setFecha(dto.getFecha());
		entity.setNumeroPersonas(dto.getNumeroPersonas());
		entity.setPerdida(dto.getPerdida());
		entity.setIdEstacion(dto.getIdEstacion());
		entity.setIdGuarda(dto.getIdGuarda());
		entity.setIdTarifa(dto.getIdTarifa());
		return entity;
	}

	private EventoDTO aDTO(Evento entity) {
		EventoDTO dto = new EventoDTO();
		dto.setId(entity.getId());
		dto.setFecha(entity.getFecha());
		dto.setNumeroPersonas(entity.getNumeroPersonas());
		dto.setPerdida(entity.getPerdida());
		dto.setIdEstacion(entity.getIdEstacion());
		dto.setIdGuarda(entity.getIdGuarda());
		dto.setIdTarifa(entity.getIdTarifa());
		return dto;
	}

	@Override
	public void create(EventoDTO data) {
		validar(data);
		Evento entity = aEntity(data);
		entity.setId(null);
		eventoRepository.guardar(entity);
	}

	@Override
	public List<EventoDTO> getAll() {
		List<EventoDTO> dtoList = new ArrayList<>();
		for (Evento e : eventoRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!eventoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El evento");
		eventoRepository.eliminar(id);
	}

	@Override
	public long count() {
		return eventoRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return eventoRepository.existe(id);
	}

	@Override
	public void updateById(Long id, EventoDTO data) {
		if (!eventoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El evento");
		validar(data);
		Evento entity = aEntity(data);
		entity.setId(id);
		eventoRepository.guardar(entity);
	}
}
