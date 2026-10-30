package co.edu.unbosque.TransMilenio.entity;

import java.util.Objects;

public class EventoInfractor {

	private Long id;
	private Long idEvento;
	private Long idInfractor;

	public EventoInfractor() {
	}

	public EventoInfractor(Long idEvento, Long idInfractor) {
		super();
		this.idEvento = idEvento;
		this.idInfractor = idInfractor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

	public Long getIdInfractor() {
		return idInfractor;
	}

	public void setIdInfractor(Long idInfractor) {
		this.idInfractor = idInfractor;
	}

	@Override
	public String toString() {
		return "EventoInfractor [id=" + id + ", idEvento=" + idEvento + ", idInfractor=" + idInfractor + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idEvento, idInfractor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventoInfractor other = (EventoInfractor) obj;
		return Objects.equals(id, other.id) && Objects.equals(idEvento, other.idEvento) && Objects.equals(idInfractor, other.idInfractor);
	}

}
