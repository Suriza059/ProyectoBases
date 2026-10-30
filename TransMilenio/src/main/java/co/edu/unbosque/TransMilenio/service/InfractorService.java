package co.edu.unbosque.TransMilenio.service;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.dto.InfractorDTO;
import co.edu.unbosque.TransMilenio.entity.Infractor;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.InfractorRepository;

public class InfractorService implements CRUDOPERATION<InfractorDTO> {

	private InfractorRepository infractorRepository;

	public InfractorService(InfractorRepository infractorRepository) {
		this.infractorRepository = infractorRepository;
	}

	private Infractor aEntity(InfractorDTO dto) {
		Infractor entity = new Infractor();
		entity.setId(dto.getId());
		entity.setNumeroDocumento(dto.getNumeroDocumento());
		entity.setTipoDocumento(dto.getTipoDocumento());
		entity.setNombre(dto.getNombre());
		entity.setApellido(dto.getApellido());
		return entity;
	}

	private InfractorDTO aDTO(Infractor entity) {
		InfractorDTO dto = new InfractorDTO();
		dto.setId(entity.getId());
		dto.setNumeroDocumento(entity.getNumeroDocumento());
		dto.setTipoDocumento(entity.getTipoDocumento());
		dto.setNombre(entity.getNombre());
		dto.setApellido(entity.getApellido());
		return dto;
	}

	@Override
	public void create(InfractorDTO data) {
		if (infractorRepository.buscarPorNumeroDocumento(data.getNumeroDocumento()) != null)
			LanzadorDeExcepciones.registroDuplicado("Ya existe un infractor con ese documento");
		Infractor entity = aEntity(data);
		entity.setId(null);
		infractorRepository.guardar(entity);
	}

	@Override
	public List<InfractorDTO> getAll() {
		List<InfractorDTO> dtoList = new ArrayList<>();
		for (Infractor e : infractorRepository.listar()) {
			dtoList.add(aDTO(e));
		}
		return dtoList;
	}

	@Override
	public void deleteById(Long id) {
		if (!infractorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El infractor");
		infractorRepository.eliminar(id);
	}

	@Override
	public long count() {
		return infractorRepository.contar();
	}

	@Override
	public boolean exist(Long id) {
		return infractorRepository.existe(id);
	}

	@Override
	public void updateById(Long id, InfractorDTO data) {
		if (!infractorRepository.existe(id))
			LanzadorDeExcepciones.registroNoExiste("El infractor");
		Infractor otro = infractorRepository.buscarPorNumeroDocumento(data.getNumeroDocumento());
		if (otro != null && !otro.getId().equals(id))
			LanzadorDeExcepciones.registroDuplicado("Ya existe un infractor con ese documento");
		Infractor entity = aEntity(data);
		entity.setId(id);
		infractorRepository.guardar(entity);
	}
}
