package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Agresion;

public class AgresionRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Agresion> convertir = (fila, numero) -> {
		Agresion agresion = new Agresion();
		agresion.setId(fila.getLong("ID_AGRESION"));
		agresion.setFecha(fila.getTimestamp("FECHA").toLocalDateTime());
		agresion.setGravedad(fila.getString("GRAVEDAD"));
		agresion.setDescripcion(fila.getString("DESCRIPCION"));
		agresion.setIdEvento(fila.getLong("ID_EVENTO"));
		agresion.setIdGuarda(fila.getLong("ID_GUARDA"));
		return agresion;
	};

	public AgresionRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Agresion guardar(Agresion agresion) {
		if (agresion.getId() == null) {
			jdbc.update("INSERT INTO AGRESION (ID_AGRESION, FECHA, GRAVEDAD, DESCRIPCION, ID_EVENTO, ID_GUARDA) VALUES (SEQ_AGRESION.NEXTVAL, ?, ?, ?, ?, ?)",
					agresion.getFecha(), agresion.getGravedad(), agresion.getDescripcion(),
					agresion.getIdEvento(), agresion.getIdGuarda());
		} else {
			jdbc.update("UPDATE AGRESION SET FECHA = ?, GRAVEDAD = ?, DESCRIPCION = ?, ID_EVENTO = ?, ID_GUARDA = ? WHERE ID_AGRESION = ?",
					agresion.getFecha(), agresion.getGravedad(), agresion.getDescripcion(),
					agresion.getIdEvento(), agresion.getIdGuarda(), agresion.getId());
		}
		return agresion;
	}

	public Agresion buscarPorId(Long id) {
		List<Agresion> lista = jdbc.query("SELECT * FROM AGRESION WHERE ID_AGRESION = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Agresion> listar() {
		return jdbc.query("SELECT * FROM AGRESION ORDER BY ID_AGRESION", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM AGRESION WHERE ID_AGRESION = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM AGRESION", Long.class);
		return total;
	}
}