package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.UsuarioGuarda;

public class UsuarioGuardaRepository {

	private JdbcTemplate jdbc;

	private RowMapper<UsuarioGuarda> convertir = (fila, numero) -> {
		UsuarioGuarda usuarioGuarda = new UsuarioGuarda();
		usuarioGuarda.setIdUsuario(fila.getLong("ID_USUARIO"));
		usuarioGuarda.setFechaActivacion(fila.getDate("FECHA_ACTIVACION").toLocalDate());
		usuarioGuarda.setIdGuarda(fila.getLong("ID_GUARDA"));
		return usuarioGuarda;
	};

	public UsuarioGuardaRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public UsuarioGuarda guardar(UsuarioGuarda usuarioGuarda) {
		if (existe(usuarioGuarda.getIdUsuario())) {
			jdbc.update("UPDATE USUARIO_GUARDA SET FECHA_ACTIVACION = ?, ID_GUARDA = ? WHERE ID_USUARIO = ?",
					usuarioGuarda.getFechaActivacion(), usuarioGuarda.getIdGuarda(), usuarioGuarda.getIdUsuario());
		} else {
			jdbc.update("INSERT INTO USUARIO_GUARDA (ID_USUARIO, FECHA_ACTIVACION, ID_GUARDA) VALUES (?, ?, ?)",
					usuarioGuarda.getIdUsuario(), usuarioGuarda.getFechaActivacion(), usuarioGuarda.getIdGuarda());
		}
		return usuarioGuarda;
	}

	public UsuarioGuarda buscarPorId(Long id) {
		List<UsuarioGuarda> lista = jdbc.query("SELECT * FROM USUARIO_GUARDA WHERE ID_USUARIO = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<UsuarioGuarda> listar() {
		return jdbc.query("SELECT * FROM USUARIO_GUARDA ORDER BY ID_USUARIO", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM USUARIO_GUARDA WHERE ID_USUARIO = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM USUARIO_GUARDA", Long.class);
		return total;
	}
}