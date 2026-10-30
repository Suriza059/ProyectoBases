package co.edu.unbosque.TransMilenio.dto;

import java.time.LocalDate;
import java.util.Objects;

public class UsuarioGuardaDTO {

	private Long id;
	private LocalDate fechaActivacion;
	private Long idUsuario;
	private Long idGuarda;

	public UsuarioGuardaDTO() {
	}

	public UsuarioGuardaDTO(LocalDate fechaActivacion, Long idUsuario, Long idGuarda) {
		super();
		this.fechaActivacion = fechaActivacion;
		this.idUsuario = idUsuario;
		this.idGuarda = idGuarda;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaActivacion() {
		return fechaActivacion;
	}

	public void setFechaActivacion(LocalDate fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Long getIdGuarda() {
		return idGuarda;
	}

	public void setIdGuarda(Long idGuarda) {
		this.idGuarda = idGuarda;
	}

	@Override
	public String toString() {
		return "UsuarioGuardaDTO [id=" + id + ", fechaActivacion=" + fechaActivacion + ", idUsuario=" + idUsuario + ", idGuarda=" + idGuarda + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fechaActivacion, id, idGuarda, idUsuario);
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
		return Objects.equals(fechaActivacion, other.fechaActivacion) && Objects.equals(id, other.id) && Objects.equals(idGuarda, other.idGuarda) && Objects.equals(idUsuario, other.idUsuario);
	}

}
