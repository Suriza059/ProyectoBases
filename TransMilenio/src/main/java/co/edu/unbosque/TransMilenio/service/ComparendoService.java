package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.ComparendoDTO;
import co.edu.unbosque.TransMilenio.entity.Comparendo;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.ComparendoRepository;
import co.edu.unbosque.TransMilenio.repository.EventoInfractorRepository;
import co.edu.unbosque.TransMilenio.repository.PagoRepository;

public class ComparendoService implements CRUDOPERATION<ComparendoDTO> {

	private ComparendoRepository comparendoRepository;
	private PagoRepository pagoRepository;
	private EventoInfractorRepository eventoInfractorRepository;

	public ComparendoService(ComparendoRepository comparendoRepository, PagoRepository pagoRepository, EventoInfractorRepository eventoInfractorRepository) {
		this.comparendoRepository = comparendoRepository;
		this.pagoRepository = pagoRepository;
		this.eventoInfractorRepository = eventoInfractorRepository;
	}

	private void validar(ComparendoDTO data) {
		if (data.getIdEvento() == null || data.getIdInfractor() == null)
			LanzadorDeExcepciones.datoInvalido("El evento y el infractor son obligatorios");
		if (!eventoInfractorRepository.existe(data.getIdEvento(), data.getIdInfractor()))
			LanzadorDeExcepciones.relacionNoExiste("La pareja evento-infractor no existe");
		if (data.getIdPago() != null && !pagoRepository.existe(data.getIdPago()))
			LanzadorDeExcepciones.relacionNoExiste("El pago no existe");
	}

	private Comparendo aEntity(ComparendoDTO dto) {
		Comparendo entity = new Comparendo();
		entity.setId(dto.getId());
		entity.setFecha(dto.getFecha());
		entity.setValor(dto.getValor());
		entity.setEstado(dto.getEstado());
		entity.setIdEvento(dto.getIdEvento());
		entity.setIdInfractor(dto.getIdInfractor());
		entity.setIdPago(dto.getIdPago());
		return entity;
	}

	private ComparendoDTO aDTO(Comparendo entity) {
		ComparendoDTO dto = new ComparendoDTO();
		dto.setId(entity.getId());
		dto.setFecha(entity.getFecha());
		dto.setValor(entity.getValor());
		dto.setEstado(entity.getEstado());
		dto.setIdEvento(entity.getIdEvento());
		dto.setIdInfractor(entity.getIdInfractor());
		dto.setIdPago(entity.getIdPago());
		return dto;
	}

	@Override
	public void create(ComparendoDTO data) {
		validar(data);
		Comparendo entity = aEntity(data);
		entity.setId(null);
		entity.setEstado("pendiente");
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
