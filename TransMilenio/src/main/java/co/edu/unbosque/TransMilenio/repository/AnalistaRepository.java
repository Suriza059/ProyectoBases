package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Analista;

public class AnalistaRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Analista> convertir = (fila, numero) -> {
		Analista analista = new Analista();
		analista.setIdUsuario(fila.getLong("ID_USUARIO"));
		analista.setAreaAnalisis(fila.getString("AREA_ANALISIS"));
		return analista;
	};

	public AnalistaRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Analista guardar(Analista analista) {
		if (existe(analista.getIdUsuario())) {
			jdbc.update("UPDATE ANALISTA SET AREA_ANALISIS = ? WHERE ID_USUARIO = ?",
					analista.getAreaAnalisis(), analista.getIdUsuario());
		} else {
			jdbc.update("INSERT INTO ANALISTA (ID_USUARIO, AREA_ANALISIS) VALUES (?, ?)",
					analista.getIdUsuario(), analista.getAreaAnalisis());
		}
		return analista;
	}

	public Analista buscarPorId(Long id) {
		List<Analista> lista = jdbc.query("SELECT * FROM ANALISTA WHERE ID_USUARIO = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Analista> listar() {
		return jdbc.query("SELECT * FROM ANALISTA ORDER BY ID_USUARIO", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM ANALISTA WHERE ID_USUARIO = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM ANALISTA", Long.class);
		return total;
	}
}