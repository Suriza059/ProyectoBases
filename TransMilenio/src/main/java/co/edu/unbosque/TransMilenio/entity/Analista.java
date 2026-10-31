package co.edu.unbosque.TransMilenio.entity;

import java.util.Objects;

public class Analista {

	private Long idUsuario;
	private String areaAnalisis;

	public Analista() {
	}

	public Analista(Long idUsuario, String areaAnalisis) {
		super();
		this.idUsuario = idUsuario;
		this.areaAnalisis = areaAnalisis;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getAreaAnalisis() {
		return areaAnalisis;
	}

	public void setAreaAnalisis(String areaAnalisis) {
		this.areaAnalisis = areaAnalisis;
	}

	@Override
	public String toString() {
		return "Analista [idUsuario=" + idUsuario + ", areaAnalisis=" + areaAnalisis + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(areaAnalisis, idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Analista other = (Analista) obj;
		return Objects.equals(areaAnalisis, other.areaAnalisis) && Objects.equals(idUsuario, other.idUsuario);
	}

}
