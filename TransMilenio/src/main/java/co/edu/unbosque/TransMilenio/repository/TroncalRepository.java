package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Troncal;

public class TroncalRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Troncal> convertir = (fila, numero) -> {
		Troncal troncal = new Troncal();
		troncal.setId(fila.getLong("ID_TRONCAL"));
		troncal.setNombre(fila.getString("NOMBRE"));
		return troncal;
	};

	public TroncalRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Troncal guardar(Troncal troncal) {
		if (troncal.getId() == null) {
			jdbc.update("INSERT INTO TRONCAL (ID_TRONCAL, NOMBRE) VALUES (SEQ_TRONCAL.NEXTVAL, ?)",
					troncal.getNombre());
		} else {
			jdbc.update("UPDATE TRONCAL SET NOMBRE = ? WHERE ID_TRONCAL = ?",
					troncal.getNombre(), troncal.getId());
		}
		return troncal;
	}

	public Troncal buscarPorId(Long id) {
		List<Troncal> lista = jdbc.query("SELECT * FROM TRONCAL WHERE ID_TRONCAL = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Troncal> listar() {
		return jdbc.query("SELECT * FROM TRONCAL ORDER BY ID_TRONCAL", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM TRONCAL WHERE ID_TRONCAL = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM TRONCAL", Long.class);
		return total;
	}
}