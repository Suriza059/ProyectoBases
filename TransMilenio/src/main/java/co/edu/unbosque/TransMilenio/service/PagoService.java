package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.PagoDTO;
import co.edu.unbosque.TransMilenio.entity.Pago;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.ComparendoRepository;
import co.edu.unbosque.TransMilenio.repository.PagoRepository;

public class PagoService implements CRUDOPERATION<PagoDTO> {

	private PagoRepository pagoRepository;
	private ComparendoRepository comparendoRepository;

	public PagoService(PagoRepository pagoRepository, ComparendoRepository comparendoRepository) {
		this.pagoRepository = pagoRepository;
		this.comparendoRepository = comparendoRepository;
	}

	private void validar(PagoDTO data) {
		if (data.getIdComparendo() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idComparendo es obligatorio");
		if (!comparendoRepository.existe(data.getIdComparendo()))
			LanzadorDeExcepciones.relacionNoExiste("El comparendo no existe");
	}

	private Pago aEntity(PagoDTO dto) {
		Pago entity = new Pago();
		entity.setId(dto.getId());
		entity.setValor(dto.getValor());
		entity.setNumeroRecibo(dto.getNumeroRecibo());
		entity.setFecha(dto.getFecha());
		entity.setIdComparendo(dto.getIdComparendo());
		return entity;
	}

	private PagoDTO aDTO(Pago entity) {
		PagoDTO dto = new PagoDTO();
		dto.setId(entity.getId());
		dto.setValor(entity.getValor());
		dto.setNumeroRecibo(entity.getNumeroRecibo());
		dto.setFecha(entity.getFecha());
		dto.setIdComparendo(entity.getIdComparendo());
		return dto;
	}

	@Override
	public void create(PagoDTO data) {
		validar(data);
		Pago entity = aEntity(data);
		entity.setId(null);
		pagoRepository.guardar(entity);
	}

	@Override
	public List<PagoDTO> getAll() {
		List<PagoDTO> dtoList = new ArrayList<>();
		for (Pago e : pagoRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!pagoRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El pago");
		pagoRepository.eliminar(id);
	}

	@Override
	public long count() {
		return pagoRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return pagoRepository.existe(id);
	}

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
