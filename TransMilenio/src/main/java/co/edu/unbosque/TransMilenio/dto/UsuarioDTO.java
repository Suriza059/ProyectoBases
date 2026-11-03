package co.edu.unbosque.TransMilenio.dto;

import java.util.Objects;

public class UsuarioDTO {

	private Long id;
	private String rol;
	private String nombreUsuario;
	private String contrasena;
	private String correo;
	private String correoConfirmado;

	public UsuarioDTO() {
	}

	public UsuarioDTO(String rol, String nombreUsuario, String contrasena, String correo, String correoConfirmado) {
		super();
		this.rol = rol;
		this.nombreUsuario = nombreUsuario;
		this.contrasena = contrasena;
		this.correo = correo;
		this.correoConfirmado = correoConfirmado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCorreoConfirmado() {
		return correoConfirmado;
	}

	public void setCorreoConfirmado(String correoConfirmado) {
		this.correoConfirmado = correoConfirmado;
	}

	@Override
	public String toString() {
		return "UsuarioDTO [id=" + id + ", rol=" + rol + ", nombreUsuario=" + nombreUsuario + ", contrasena=" + contrasena + ", correo=" + correo + ", correoConfirmado=" + correoConfirmado + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(contrasena, correo, correoConfirmado, id, nombreUsuario, rol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		return Objects.equals(contrasena, other.contrasena) && Objects.equals(correo, other.correo) && Objects.equals(correoConfirmado, other.correoConfirmado) && Objects.equals(id, other.id) && Objects.equals(nombreUsuario, other.nombreUsuario) && Objects.equals(rol, other.rol);
	}

}