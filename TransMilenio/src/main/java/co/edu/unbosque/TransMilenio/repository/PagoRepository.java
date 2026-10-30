package co.edu.unbosque.TransMilenio.repository;

import java.util.ArrayList;
import java.util.List;

import co.edu.unbosque.TransMilenio.entity.Pago;

public class PagoRepository {

	// Por ahora guarda en memoria. Aqui va la conexion a la base de datos hecha a mano.
	private ArrayList<Pago> lista = new ArrayList<>();
	private long siguienteId = 1;

	public Pago guardar(Pago pago) {
		if (pago.getId() == null) {
			pago.setId(siguienteId);
			siguienteId++;
			lista.add(pago);
			return pago;
		}
		for (int i = 0; i < lista.size(); i++) {
			if (lista.get(i).getId().equals(pago.getId())) {
				lista.set(i, pago);
				return pago;
			}
		}
		lista.add(pago);
		return pago;
	}

	public Pago buscarPorId(Long id) {
		for (Pago pago : lista) {
			if (pago.getId().equals(id)) {
				return pago;
			}
		}
		return null;
	}

	public List<Pago> listar() {
		return new ArrayList<>(lista);
	}

	public boolean eliminar(Long id) {
		Pago pago = buscarPorId(id);
		if (pago == null)
			return false;
		lista.remove(pago);
		return true;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		return lista.size();
	}
}
