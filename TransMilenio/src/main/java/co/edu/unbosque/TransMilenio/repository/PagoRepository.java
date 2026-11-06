package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Pago;

public class PagoRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Pago> convertir = (fila, numero) -> {
		Pago pago = new Pago();
		pago.setId(fila.getLong("ID_PAGO"));
		pago.setValor(fila.getLong("VALOR"));
		pago.setNumeroRecibo(fila.getString("NUMERO_RECIBO"));
		pago.setFecha(fila.getDate("FECHA").toLocalDate());
		pago.setIdComparendo(fila.getLong("ID_COMPARENDO"));
		return pago;
	};

	public PagoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Pago guardar(Pago pago) {
		if (pago.getId() == null) {
			jdbc.update("INSERT INTO PAGO (ID_PAGO, VALOR, NUMERO_RECIBO, FECHA, ID_COMPARENDO) VALUES (SEQ_PAGO.NEXTVAL, ?, ?, ?, ?)",
					pago.getValor(), pago.getNumeroRecibo(), pago.getFecha(), pago.getIdComparendo());
		} else {
			jdbc.update("UPDATE PAGO SET VALOR = ?, NUMERO_RECIBO = ?, FECHA = ?, ID_COMPARENDO = ? WHERE ID_PAGO = ?",
					pago.getValor(), pago.getNumeroRecibo(), pago.getFecha(), pago.getIdComparendo(), pago.getId());
		}
		return pago;
	}

	public Pago buscarPorId(Long id) {
		List<Pago> lista = jdbc.query("SELECT * FROM PAGO WHERE ID_PAGO = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Pago> listar() {
		return jdbc.query("SELECT * FROM PAGO ORDER BY ID_PAGO", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM PAGO WHERE ID_PAGO = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM PAGO", Long.class);
		return total;
	}
}