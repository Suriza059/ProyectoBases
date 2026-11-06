package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.RegistroCambios;

public class RegistroCambiosRepository {

	private JdbcTemplate jdbc;

	private RowMapper<RegistroCambios> convertir = (fila, numero) -> {
		RegistroCambios registro = new RegistroCambios();
		registro.setId(fila.getLong("ID_REGISTROCAMBIOS"));
		registro.setFecha(fila.getTimestamp("FECHA").toLocalDateTime());
		registro.setCampoModificado(fila.getString("CAMPO_MODIFICADO"));
		registro.setValorAnterior(fila.getString("VALOR_ANTERIOR"));
		registro.setValorNuevo(fila.getString("VALOR_NUEVO"));
		registro.setIdUsuario(fila.getLong("ID_USUARIO"));
		registro.setIdComparendo(fila.getLong("ID_COMPARENDO"));
		return registro;
	};

	public RegistroCambiosRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public RegistroCambios guardar(RegistroCambios registro) {
		if (registro.getId() == null) {
			jdbc.update("INSERT INTO REGISTRO_CAMBIOS (ID_REGISTROCAMBIOS, FECHA, CAMPO_MODIFICADO, VALOR_ANTERIOR, VALOR_NUEVO, ID_USUARIO, ID_COMPARENDO) VALUES (SEQ_REGISTRO_CAMBIOS.NEXTVAL, ?, ?, ?, ?, ?, ?)",
					registro.getFecha(), registro.getCampoModificado(), registro.getValorAnterior(),
					registro.getValorNuevo(), registro.getIdUsuario(), registro.getIdComparendo());
		} else {
			jdbc.update("UPDATE REGISTRO_CAMBIOS SET FECHA = ?, CAMPO_MODIFICADO = ?, VALOR_ANTERIOR = ?, VALOR_NUEVO = ?, ID_USUARIO = ?, ID_COMPARENDO = ? WHERE ID_REGISTROCAMBIOS = ?",
					registro.getFecha(), registro.getCampoModificado(), registro.getValorAnterior(),
					registro.getValorNuevo(), registro.getIdUsuario(), registro.getIdComparendo(), registro.getId());
		}
		return registro;
	}

	public RegistroCambios buscarPorId(Long id) {
		List<RegistroCambios> lista = jdbc.query("SELECT * FROM REGISTRO_CAMBIOS WHERE ID_REGISTROCAMBIOS = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<RegistroCambios> listar() {
		return jdbc.query("SELECT * FROM REGISTRO_CAMBIOS ORDER BY ID_REGISTROCAMBIOS", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM REGISTRO_CAMBIOS WHERE ID_REGISTROCAMBIOS = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM REGISTRO_CAMBIOS", Long.class);
		return total;
	}
}