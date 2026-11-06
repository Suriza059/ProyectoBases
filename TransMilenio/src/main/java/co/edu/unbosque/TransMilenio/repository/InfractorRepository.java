package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Infractor;

public class InfractorRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Infractor> convertir = (fila, numero) -> {
		Infractor infractor = new Infractor();
		infractor.setId(fila.getLong("ID_INFRACTOR"));
		infractor.setTipoDocumento(fila.getString("TIPO_DOCUMENTO"));
		infractor.setNumeroDocumento(fila.getString("NUMERO_DOCUMENTO"));
		infractor.setNombre(fila.getString("NOMBRE"));
		infractor.setApellido(fila.getString("APELLIDO"));
		return infractor;
	};

	public InfractorRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Infractor guardar(Infractor infractor) {
		if (infractor.getId() == null) {
			jdbc.update("INSERT INTO INFRACTOR (ID_INFRACTOR, TIPO_DOCUMENTO, NUMERO_DOCUMENTO, NOMBRE, APELLIDO) VALUES (SEQ_INFRACTOR.NEXTVAL, ?, ?, ?, ?)",
					infractor.getTipoDocumento(), infractor.getNumeroDocumento(), infractor.getNombre(),
					infractor.getApellido());
		} else {
			jdbc.update("UPDATE INFRACTOR SET TIPO_DOCUMENTO = ?, NUMERO_DOCUMENTO = ?, NOMBRE = ?, APELLIDO = ? WHERE ID_INFRACTOR = ?",
					infractor.getTipoDocumento(), infractor.getNumeroDocumento(), infractor.getNombre(),
					infractor.getApellido(), infractor.getId());
		}
		return infractor;
	}

	public Infractor buscarPorId(Long id) {
		List<Infractor> lista = jdbc.query("SELECT * FROM INFRACTOR WHERE ID_INFRACTOR = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public Infractor buscarPorTipoDocumentoYNumeroDocumento(String tipoDocumento, String numeroDocumento) {
		List<Infractor> lista = jdbc.query("SELECT * FROM INFRACTOR WHERE TIPO_DOCUMENTO = ? AND NUMERO_DOCUMENTO = ?",
				convertir, tipoDocumento, numeroDocumento);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Infractor> listar() {
		return jdbc.query("SELECT * FROM INFRACTOR ORDER BY ID_INFRACTOR", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM INFRACTOR WHERE ID_INFRACTOR = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM INFRACTOR", Long.class);
		return total;
	}
}