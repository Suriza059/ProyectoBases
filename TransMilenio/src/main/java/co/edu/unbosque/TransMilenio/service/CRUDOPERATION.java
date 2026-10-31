package co.edu.unbosque.TransMilenio.service;

import java.util.List;

public interface CRUDOPERATION<T> {

	public void create(T data);

	public List<T> getAll();

	public void deleteById(Long id);

	public long count();

	public boolean exist(Long id);

	public void updateById(Long id, T data);
}
