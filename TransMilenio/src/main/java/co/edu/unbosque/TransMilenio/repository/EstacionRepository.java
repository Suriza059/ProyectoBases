package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Estacion;

public class EstacionRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Estacion> convertir = (fila, numero) -> {
		Estacion estacion = new Estacion();
		estacion.setId(fila.getLong("ID_ESTACION"));
		estacion.setNombre(fila.getString("NOMBRE"));
		estacion.setActiva(fila.getString("ACTIVA"));
		estacion.setIdTroncal(fila.getLong("ID_TRONCAL"));
		return estacion;
	};

	public EstacionRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Estacion guardar(Estacion estacion) {
		if (estacion.getId() == null) {
			jdbc.update("INSERT INTO ESTACION (ID_ESTACION, NOMBRE, ACTIVA, ID_TRONCAL) VALUES (SEQ_ESTACION.NEXTVAL, ?, ?, ?)",
					estacion.getNombre(), estacion.getActiva(), estacion.getIdTroncal());
		} else {
			jdbc.update("UPDATE ESTACION SET NOMBRE = ?, ACTIVA = ?, ID_TRONCAL = ? WHERE ID_ESTACION = ?",
					estacion.getNombre(), estacion.getActiva(), estacion.getIdTroncal(), estacion.getId());
		}
		return estacion;
	}

	public Estacion buscarPorId(Long id) {
		List<Estacion> lista = jdbc.query("SELECT * FROM ESTACION WHERE ID_ESTACION = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Estacion> listar() {
		return jdbc.query("SELECT * FROM ESTACION ORDER BY ID_ESTACION", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM ESTACION WHERE ID_ESTACION = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM ESTACION", Long.class);
		return total;
	}
}