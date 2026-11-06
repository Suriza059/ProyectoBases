package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Administrador;

public class AdministradorRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Administrador> convertir = (fila, numero) -> {
		Administrador administrador = new Administrador();
		administrador.setIdUsuario(fila.getLong("ID_USUARIO"));
		administrador.setAreaGestion(fila.getString("AREA_GESTION"));
		administrador.setFechaGestion(fila.getDate("FECHA_GESTION").toLocalDate());
		return administrador;
	};

	public AdministradorRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Administrador guardar(Administrador administrador) {
		if (existe(administrador.getIdUsuario())) {
			jdbc.update("UPDATE ADMINISTRADOR SET AREA_GESTION = ?, FECHA_GESTION = ? WHERE ID_USUARIO = ?",
					administrador.getAreaGestion(), administrador.getFechaGestion(), administrador.getIdUsuario());
		} else {
			jdbc.update("INSERT INTO ADMINISTRADOR (ID_USUARIO, AREA_GESTION, FECHA_GESTION) VALUES (?, ?, ?)",
					administrador.getIdUsuario(), administrador.getAreaGestion(), administrador.getFechaGestion());
		}
		return administrador;
	}

	public Administrador buscarPorId(Long id) {
		List<Administrador> lista = jdbc.query("SELECT * FROM ADMINISTRADOR WHERE ID_USUARIO = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Administrador> listar() {
		return jdbc.query("SELECT * FROM ADMINISTRADOR ORDER BY ID_USUARIO", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM ADMINISTRADOR WHERE ID_USUARIO = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM ADMINISTRADOR", Long.class);
		return total;
	}
}