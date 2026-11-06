package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Comparendo;

public class ComparendoRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Comparendo> convertir = (fila, numero) -> {
		Comparendo comparendo = new Comparendo();
		comparendo.setId(fila.getLong("ID_COMPARENDO"));
		comparendo.setFecha(fila.getDate("FECHA").toLocalDate());
		comparendo.setValor(fila.getLong("VALOR"));
		comparendo.setEstado(fila.getString("ESTADO"));
		comparendo.setIdEventoInfractor(fila.getLong("ID_EVENTO_INFRACTOR"));
		return comparendo;
	};

	public ComparendoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Comparendo guardar(Comparendo comparendo) {
		if (comparendo.getId() == null) {
			jdbc.update("INSERT INTO COMPARENDO (ID_COMPARENDO, FECHA, VALOR, ESTADO, ID_EVENTO_INFRACTOR) VALUES (SEQ_COMPARENDO.NEXTVAL, ?, ?, ?, ?)",
					comparendo.getFecha(), comparendo.getValor(), comparendo.getEstado(),
					comparendo.getIdEventoInfractor());
		} else {
			jdbc.update("UPDATE COMPARENDO SET FECHA = ?, VALOR = ?, ESTADO = ?, ID_EVENTO_INFRACTOR = ? WHERE ID_COMPARENDO = ?",
					comparendo.getFecha(), comparendo.getValor(), comparendo.getEstado(),
					comparendo.getIdEventoInfractor(), comparendo.getId());
		}
		return comparendo;
	}

	public Comparendo buscarPorId(Long id) {
		List<Comparendo> lista = jdbc.query("SELECT * FROM COMPARENDO WHERE ID_COMPARENDO = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Comparendo> listar() {
		return jdbc.query("SELECT * FROM COMPARENDO ORDER BY ID_COMPARENDO", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM COMPARENDO WHERE ID_COMPARENDO = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM COMPARENDO", Long.class);
		return total;
	}
}