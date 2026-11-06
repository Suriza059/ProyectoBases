package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Guarda;

public class GuardaRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Guarda> convertir = (fila, numero) -> {
		Guarda guarda = new Guarda();
		guarda.setId(fila.getLong("ID_GUARDA"));
		guarda.setTurno(fila.getString("TURNO"));
		guarda.setNombre(fila.getString("NOMBRE"));
		guarda.setApellido(fila.getString("APELLIDO"));
		guarda.setActivo(fila.getString("ACTIVO"));
		guarda.setCargo(fila.getString("CARGO"));
		guarda.setIdEstacion(fila.getLong("ID_ESTACION"));
		if (fila.getObject("ID_SUPERVISOR") == null) {
			guarda.setIdSupervisor(null);
		} else {
			guarda.setIdSupervisor(fila.getLong("ID_SUPERVISOR"));
		}
		return guarda;
	};

	public GuardaRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Guarda guardar(Guarda guarda) {
		if (guarda.getId() == null) {
			jdbc.update("INSERT INTO GUARDA (ID_GUARDA, TURNO, NOMBRE, APELLIDO, ACTIVO, CARGO, ID_ESTACION, ID_SUPERVISOR) VALUES (SEQ_GUARDA.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)",
					guarda.getTurno(), guarda.getNombre(), guarda.getApellido(), guarda.getActivo(),
					guarda.getCargo(), guarda.getIdEstacion(), guarda.getIdSupervisor());
		} else {
			jdbc.update("UPDATE GUARDA SET TURNO = ?, NOMBRE = ?, APELLIDO = ?, ACTIVO = ?, CARGO = ?, ID_ESTACION = ?, ID_SUPERVISOR = ? WHERE ID_GUARDA = ?",
					guarda.getTurno(), guarda.getNombre(), guarda.getApellido(), guarda.getActivo(),
					guarda.getCargo(), guarda.getIdEstacion(), guarda.getIdSupervisor(), guarda.getId());
		}
		return guarda;
	}

	public Guarda buscarPorId(Long id) {
		List<Guarda> lista = jdbc.query("SELECT * FROM GUARDA WHERE ID_GUARDA = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Guarda> listar() {
		return jdbc.query("SELECT * FROM GUARDA ORDER BY ID_GUARDA", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM GUARDA WHERE ID_GUARDA = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM GUARDA", Long.class);
		return total;
	}
}