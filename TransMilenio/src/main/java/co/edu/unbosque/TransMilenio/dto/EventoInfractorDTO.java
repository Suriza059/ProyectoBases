package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

public class EventoInfractorDTO {

	private Long idEvento;
	private Long idInfractor;

	public EventoInfractorDTO() {
	}

	public EventoInfractorDTO(Long idEvento, Long idInfractor) {
		super();
		this.idEvento = idEvento;
		this.idInfractor = idInfractor;
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
		return "EventoInfractorDTO [idEvento=" + idEvento + ", idInfractor=" + idInfractor + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(idEvento, idInfractor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventoInfractorDTO other = (EventoInfractorDTO) obj;
		return Objects.equals(idEvento, other.idEvento) && Objects.equals(idInfractor, other.idInfractor);
	}

}
