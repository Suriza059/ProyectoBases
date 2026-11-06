package co.edu.unbosque.TransMilenio.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Tarifa;

public class TarifaRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Tarifa> convertir = (fila, numero) -> {
		Tarifa tarifa = new Tarifa();
		tarifa.setId(fila.getLong("ID_TARIFA"));
		tarifa.setFechaInicio(fila.getDate("FECHA_INICIO").toLocalDate());
		Date fechaFin = fila.getDate("FECHA_FIN");
		if (fechaFin == null) {
			tarifa.setFechaFin(null);
		} else {
			tarifa.setFechaFin(fechaFin.toLocalDate());
		}
		tarifa.setValor(fila.getLong("VALOR"));
		return tarifa;
	};

	public TarifaRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Tarifa guardar(Tarifa tarifa) {
		if (tarifa.getId() == null) {
			jdbc.update("INSERT INTO TARIFA (ID_TARIFA, FECHA_INICIO, FECHA_FIN, VALOR) VALUES (SEQ_TARIFA.NEXTVAL, ?, ?, ?)",
					tarifa.getFechaInicio(), tarifa.getFechaFin(), tarifa.getValor());
		} else {
			jdbc.update("UPDATE TARIFA SET FECHA_INICIO = ?, FECHA_FIN = ?, VALOR = ? WHERE ID_TARIFA = ?",
					tarifa.getFechaInicio(), tarifa.getFechaFin(), tarifa.getValor(), tarifa.getId());
		}
		return tarifa;
	}

	public Tarifa buscarPorId(Long id) {
		List<Tarifa> lista = jdbc.query("SELECT * FROM TARIFA WHERE ID_TARIFA = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Tarifa> listar() {
		return jdbc.query("SELECT * FROM TARIFA ORDER BY ID_TARIFA", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM TARIFA WHERE ID_TARIFA = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM TARIFA", Long.class);
		return total;
	}
}