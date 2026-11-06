package co.edu.unbosque.TransMilenio.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.EventoInfractor;

public class EventoInfractorRepository {

	private JdbcTemplate jdbc;

	private RowMapper<EventoInfractor> convertir = (fila, numero) -> {
		EventoInfractor eventoInfractor = new EventoInfractor();
		eventoInfractor.setId(fila.getLong("ID_EVENTO_INFRACTOR"));
		eventoInfractor.setIdEvento(fila.getLong("ID_EVENTO"));
		eventoInfractor.setIdInfractor(fila.getLong("ID_INFRACTOR"));
		return eventoInfractor;
	};

	public EventoInfractorRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public EventoInfractor guardar(EventoInfractor eventoInfractor) {
		if (eventoInfractor.getId() == null) {
			jdbc.update("INSERT INTO EVENTO_INFRACTOR (ID_EVENTO_INFRACTOR, ID_EVENTO, ID_INFRACTOR) VALUES (SEQ_EVENTO_INFRACTOR.NEXTVAL, ?, ?)",
					eventoInfractor.getIdEvento(), eventoInfractor.getIdInfractor());
		} else {
			jdbc.update("UPDATE EVENTO_INFRACTOR SET ID_EVENTO = ?, ID_INFRACTOR = ? WHERE ID_EVENTO_INFRACTOR = ?",
					eventoInfractor.getIdEvento(), eventoInfractor.getIdInfractor(), eventoInfractor.getId());
		}
		return eventoInfractor;
	}

	public EventoInfractor buscarPorId(Long id) {
		List<EventoInfractor> lista = jdbc.query("SELECT * FROM EVENTO_INFRACTOR WHERE ID_EVENTO_INFRACTOR = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public EventoInfractor buscarPorIdEventoYIdInfractor(Long idEvento, Long idInfractor) {
		List<EventoInfractor> lista = jdbc.query("SELECT * FROM EVENTO_INFRACTOR WHERE ID_EVENTO = ? AND ID_INFRACTOR = ?",
				convertir, idEvento, idInfractor);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<EventoInfractor> listar() {
		return jdbc.query("SELECT * FROM EVENTO_INFRACTOR ORDER BY ID_EVENTO_INFRACTOR", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM EVENTO_INFRACTOR WHERE ID_EVENTO_INFRACTOR = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM EVENTO_INFRACTOR", Long.class);
		return total;
	}
}