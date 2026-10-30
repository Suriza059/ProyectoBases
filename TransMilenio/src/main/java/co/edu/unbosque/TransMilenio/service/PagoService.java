package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.PagoDTO;
import co.edu.unbosque.TransMilenio.entity.Pago;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.PagoRepository;

public class PagoService implements CRUDOPERATION<PagoDTO> {

	private PagoRepository pagoRepository;

	public PagoService(PagoRepository pagoRepository) {
		this.pagoRepository = pagoRepository;
	}

	private Pago aEntity(PagoDTO dto) {
		Pago entity = new Pago();
		entity.setId(dto.getId());
		entity.setNumeroRecibido(dto.getNumeroRecibido());
		entity.setFecha(dto.getFecha());
		entity.setValor(dto.getValor());
		return entity;
	}

	private PagoDTO aDTO(Pago entity) {
		PagoDTO dto = new PagoDTO();
		dto.setId(entity.getId());
		dto.setNumeroRecibido(entity.getNumeroRecibido());
		dto.setFecha(entity.getFecha());
		dto.setValor(entity.getValor());
		return dto;
	}

	@Override
	public void create(PagoDTO data) {
		if (pagoRepository.buscarPorNumeroRecibido(data.getNumeroRecibido()) != null)
			LanzadorDeExcepciones.registroDuplicado("Ya existe un pago con ese numero de recibo");
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
		Pago otro = pagoRepository.buscarPorNumeroRecibido(data.getNumeroRecibido());
		if (otro != null && !otro.getId().equals(id))
			LanzadorDeExcepciones.registroDuplicado("Ya existe un pago con ese numero de recibo");
		Pago entity = aEntity(data);
		entity.setId(id);
		pagoRepository.guardar(entity);
	}
}
