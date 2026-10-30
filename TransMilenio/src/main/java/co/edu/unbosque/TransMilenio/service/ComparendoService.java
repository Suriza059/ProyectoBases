package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.ComparendoDTO;
import co.edu.unbosque.TransMilenio.entity.Comparendo;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.ComparendoRepository;
import co.edu.unbosque.TransMilenio.repository.EventoInfractorRepository;

public class ComparendoService implements CRUDOPERATION<ComparendoDTO> {

	private ComparendoRepository comparendoRepository;
	private EventoInfractorRepository eventoInfractorRepository;

	public ComparendoService(ComparendoRepository comparendoRepository, EventoInfractorRepository eventoInfractorRepository) {
		this.comparendoRepository = comparendoRepository;
		this.eventoInfractorRepository = eventoInfractorRepository;
	}

	private void validar(ComparendoDTO data) {
		if (data.getIdEventoInfractor() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idEventoInfractor es obligatorio");
		if (!eventoInfractorRepository.existe(data.getIdEventoInfractor()))
			LanzadorDeExcepciones.relacionNoExiste("La pareja evento-infractor no existe");
	}

	private Comparendo aEntity(ComparendoDTO dto) {
		Comparendo entity = new Comparendo();
		entity.setId(dto.getId());
		entity.setFecha(dto.getFecha());
		entity.setValor(dto.getValor());
		entity.setEstado(dto.getEstado());
		entity.setIdEventoInfractor(dto.getIdEventoInfractor());
		return entity;
	}

	private ComparendoDTO aDTO(Comparendo entity) {
		ComparendoDTO dto = new ComparendoDTO();
		dto.setId(entity.getId());
		dto.setFecha(entity.getFecha());
		dto.setValor(entity.getValor());
		dto.setEstado(entity.getEstado());
		dto.setIdEventoInfractor(entity.getIdEventoInfractor());
		return dto;
	}

	@Override
	public void create(ComparendoDTO data) {
		validar(data);
		Comparendo entity = aEntity(data);
		entity.setId(null);
		entity.setEstado("Pendiente");
		comparendoRepository.guardar(entity);
	}

	@Override
	public List<ComparendoDTO> getAll() {
		List<ComparendoDTO> dtoList = new ArrayList<>();
		for (Comparendo e : comparendoRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!comparendoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El comparendo");
		comparendoRepository.eliminar(id);
	}

	@Override
	public long count() {
		return comparendoRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return comparendoRepository.existe(id);
	}

	@Override
	public void updateById(Long id, ComparendoDTO data) {
		if (!comparendoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El comparendo");
		validar(data);
		Comparendo entity = aEntity(data);
		entity.setId(id);
		comparendoRepository.guardar(entity);
	}
}
