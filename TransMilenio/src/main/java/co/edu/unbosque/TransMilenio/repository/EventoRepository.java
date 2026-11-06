package co.edu.unbosque.TransMilenio.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import co.edu.unbosque.TransMilenio.entity.Evento;

public class EventoRepository {

	private JdbcTemplate jdbc;

	private RowMapper<Evento> convertir = (fila, numero) -> {
		Evento evento = new Evento();
		evento.setId(fila.getLong("ID_EVENTO"));
		evento.setFecha(fila.getDate("FECHA").toLocalDate());
		String hora = fila.getString("HORA");
		if (hora == null) {
			evento.setHora(null);
		} else {
			evento.setHora(LocalTime.parse(hora));
		}
		evento.setNumeroPersonas(fila.getInt("NUMERO_PERSONAS"));
		evento.setIdEstacion(fila.getLong("ID_ESTACION"));
		evento.setIdTarifa(fila.getLong("ID_TARIFA"));
		evento.setIdModalidad(fila.getLong("ID_MODALIDAD"));
		evento.setIdGuarda(fila.getLong("ID_GUARDA"));
		return evento;
	};

	public EventoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public Evento guardar(Evento evento) {
		String hora = null;
		if (evento.getHora() != null) {
			hora = evento.getHora().toString();
		}
		if (evento.getId() == null) {
			jdbc.update("INSERT INTO EVENTO (ID_EVENTO, FECHA, HORA, NUMERO_PERSONAS, ID_ESTACION, ID_TARIFA, ID_MODALIDAD, ID_GUARDA) VALUES (SEQ_EVENTO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)",
					evento.getFecha(), hora, evento.getNumeroPersonas(), evento.getIdEstacion(),
					evento.getIdTarifa(), evento.getIdModalidad(), evento.getIdGuarda());
		} else {
			jdbc.update("UPDATE EVENTO SET FECHA = ?, HORA = ?, NUMERO_PERSONAS = ?, ID_ESTACION = ?, ID_TARIFA = ?, ID_MODALIDAD = ?, ID_GUARDA = ? WHERE ID_EVENTO = ?",
					evento.getFecha(), hora, evento.getNumeroPersonas(), evento.getIdEstacion(),
					evento.getIdTarifa(), evento.getIdModalidad(), evento.getIdGuarda(), evento.getId());
		}
		return evento;
	}

	public Evento buscarPorId(Long id) {
		List<Evento> lista = jdbc.query("SELECT * FROM EVENTO WHERE ID_EVENTO = ?", convertir, id);
		if (lista.isEmpty()) {
			return null;
		}
		return lista.get(0);
	}

	public List<Evento> listar() {
		return jdbc.query("SELECT * FROM EVENTO ORDER BY ID_EVENTO", convertir);
	}

	public boolean eliminar(Long id) {
		return jdbc.update("DELETE FROM EVENTO WHERE ID_EVENTO = ?", id) > 0;
	}

	public boolean existe(Long id) {
		return buscarPorId(id) != null;
	}

	public long contar() {
		Long total = jdbc.queryForObject("SELECT COUNT(*) FROM EVENTO", Long.class);
		return total;
	}
}