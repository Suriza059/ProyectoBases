package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Usuario;

public class UsuarioRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Usuario> convertir = (fila, numero) -> {
		Usuario usuario = new Usuario();
		usuario.setId(fila.getLong("ID_USUARIO"));
		usuario.setRol(fila.getString("ROL"));
		usuario.setNombreUsuario(fila.getString("NOMBRE_USUARIO"));
		usuario.setContrasena(fila.getString("CONTRASENA"));
		return usuario;
	};

	public UsuarioRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Usuario guardar(Usuario usuario) {
		if (usuario.getId() == null) {
			jdbc.update("INSERT INTO USUARIO (ID_USUARIO, ROL, NOMBRE_USUARIO, CONTRASENA) VALUES (SEQ_USUARIO.NEXTVAL, ?, ?, ?)",
					usuario.getRol(), usuario.getNombreUsuario(), usuario.getContrasena());
		} else {
			jdbc.update("UPDATE USUARIO SET ROL = ?, NOMBRE_USUARIO = ?, CONTRASENA = ? WHERE ID_USUARIO = ?",
					usuario.getRol(), usuario.getNombreUsuario(), usuario.getContrasena(), usuario.getId());
		}
		return usuario;
	}

	public Usuario buscarPorId(Long id) {
		List<Usuario> lista = jdbc.query("SELECT * FROM USUARIO WHERE ID_USUARIO = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public Usuario buscarPorNombreUsuario(String nombreUsuario) {
		List<Usuario> lista = jdbc.query("SELECT * FROM USUARIO WHERE NOMBRE_USUARIO = ?", convertir, nombreUsuario);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Usuario> listar() {
		return jdbc.query("SELECT * FROM USUARIO ORDER BY ID_USUARIO", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM USUARIO WHERE ID_USUARIO = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM USUARIO", Long.class);
		return total;
	}
}