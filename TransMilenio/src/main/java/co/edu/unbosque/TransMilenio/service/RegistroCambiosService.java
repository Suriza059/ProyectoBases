package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.RegistroCambiosDTO;
import co.edu.unbosque.TransMilenio.entity.RegistroCambios;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.ComparendoRepository;
import co.edu.unbosque.TransMilenio.repository.RegistroCambiosRepository;

public class RegistroCambiosService implements CRUDOPERATION<RegistroCambiosDTO> {

	private RegistroCambiosRepository registroCambiosRepository;
	private ComparendoRepository comparendoRepository;

	public RegistroCambiosService(RegistroCambiosRepository registroCambiosRepository, ComparendoRepository comparendoRepository) {
		this.registroCambiosRepository = registroCambiosRepository;
		this.comparendoRepository = comparendoRepository;
	}

	private void validar(RegistroCambiosDTO data) {
		if (data.getIdComparendo() == null)
			LanzadorDeExcepciones.datoInvalido("El campo idComparendo es obligatorio");
		if (!comparendoRepository.existe(data.getIdComparendo()))
			LanzadorDeExcepciones.relacionNoExiste("El comparendo no existe");
	}

	private RegistroCambios aEntity(RegistroCambiosDTO dto) {
		RegistroCambios entity = new RegistroCambios();
		entity.setId(dto.getId());
		entity.setFecha(dto.getFecha());
		entity.setValorAnterior(dto.getValorAnterior());
		entity.setValorNuevo(dto.getValorNuevo());
		entity.setCampoModificado(dto.getCampoModificado());
		entity.setIdComparendo(dto.getIdComparendo());
		return entity;
	}

	private RegistroCambiosDTO aDTO(RegistroCambios entity) {
		RegistroCambiosDTO dto = new RegistroCambiosDTO();
		dto.setId(entity.getId());
		dto.setFecha(entity.getFecha());
		dto.setValorAnterior(entity.getValorAnterior());
		dto.setValorNuevo(entity.getValorNuevo());
		dto.setCampoModificado(entity.getCampoModificado());
		dto.setIdComparendo(entity.getIdComparendo());
		return dto;
	}

	@Override
	public void create(RegistroCambiosDTO data) {
		validar(data);
		RegistroCambios entity = aEntity(data);
		entity.setId(null);
		registroCambiosRepository.guardar(entity);
	}

	@Override
	public List<RegistroCambiosDTO> getAll() {
		List<RegistroCambiosDTO> dtoList = new ArrayList<>();
		for (RegistroCambios e : registroCambiosRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!registroCambiosRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El registro de cambios");
		registroCambiosRepository.eliminar(id);
	}

	@Override
	public long count() {
		return registroCambiosRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return registroCambiosRepository.existe(id);
	}

	@Override
	public void updateById(Long id, RegistroCambiosDTO data) {
		if (!registroCambiosRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El registro de cambios");
		validar(data);
		RegistroCambios entity = aEntity(data);
		entity.setId(id);
		registroCambiosRepository.guardar(entity);
	}
}
