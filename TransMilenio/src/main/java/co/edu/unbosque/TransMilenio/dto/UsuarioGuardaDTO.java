package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

public class UsuarioGuardaDTO {

	private Long idUsuario;
	private LocalDate fechaActivacion;
	private Long idGuarda;

	public UsuarioGuardaDTO() {
	}

	public UsuarioGuardaDTO(Long idUsuario, LocalDate fechaActivacion, Long idGuarda) {
		super();
		this.idUsuario = idUsuario;
		this.fechaActivacion = fechaActivacion;
		this.idGuarda = idGuarda;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public LocalDate getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(LocalDate fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Long getIdGuarda() {
		return idGuarda;
	}

	public void setIdGuarda(Long idGuarda) {
		this.idGuarda = idGuarda;
	}

	@Override
	public String toString() {
		return "UsuarioGuardaDTO [idUsuario=" + idUsuario + ", fechaActivacion=" + fechaActivacion + ", idGuarda=" + idGuarda + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaActivacion, idGuarda, idUsuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioGuardaDTO other = (UsuarioGuardaDTO) obj;
		return Objects.equals(fechaActivacion, other.fechaActivacion) && Objects.equals(idGuarda, other.idGuarda) && Objects.equals(idUsuario, other.idUsuario);
	}

}
