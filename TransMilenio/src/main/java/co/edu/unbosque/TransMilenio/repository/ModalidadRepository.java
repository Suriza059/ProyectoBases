package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Modalidad;

public class ModalidadRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Modalidad> convertir = (fila, numero) -> {
		Modalidad modalidad = new Modalidad();
		modalidad.setId(fila.getLong("ID_MODALIDAD"));
		modalidad.setNombre(fila.getString("NOMBRE"));
		return modalidad;
	};

	public ModalidadRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Modalidad guardar(Modalidad modalidad) {
		if (modalidad.getId() == null) {
			jdbc.update("INSERT INTO MODALIDAD (ID_MODALIDAD, NOMBRE) VALUES (SEQ_MODALIDAD.NEXTVAL, ?)",
					modalidad.getNombre());
		} else {
			jdbc.update("UPDATE MODALIDAD SET NOMBRE = ? WHERE ID_MODALIDAD = ?",
					modalidad.getNombre(), modalidad.getId());
		}
		return modalidad;
	}

	public Modalidad buscarPorId(Long id) {
		List<Modalidad> lista = jdbc.query("SELECT * FROM MODALIDAD WHERE ID_MODALIDAD = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Modalidad> listar() {
		return jdbc.query("SELECT * FROM MODALIDAD ORDER BY ID_MODALIDAD", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM MODALIDAD WHERE ID_MODALIDAD = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM MODALIDAD", Long.class);
		return total;
	}
}