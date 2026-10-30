package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Infractor;

public class InfractorRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Infractor> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Infractor guardar(Infractor infractor) {
		if (infractor.getId() == null) {
			infractor.setId(siguienteId);
			siguienteId++;
			lista.add(infractor);
			return infractor;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(infractor.getId())) {
				lista.set(i, infractor);
				return infractor;
			}
		}
		lista.add(infractor);
		return infractor;
	}

	public Infractor buscarPorId(Long id) {
		for (Infractor infractor : lista) {
			if (infractor.getId().equals(id)) {
				return infractor;
			}
		}
		return null;
	}

	public Infractor buscarPorTipoDocumentoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
		for (Infractor infractor : lista) {
			if (infractor.getTipoDocumento() != null && infractor.getTipoDocumento().equals(tipoDocumento) && infractor.getNumeroDocumento() != null && infractor.getNumeroDocumento().equals(numeroDocumento)) {
				return infractor;
			}
		}
		return null;
	}

	public List<Infractor> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Infractor infractor = buscarPorId(id);
		if (infractor == null)
			return false;
		lista.remove(infractor);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
