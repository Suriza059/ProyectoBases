package co.edu.unbosque.TransMilenio.service;

import co.edu.unbosque.TransMilenio.entity.Usuario;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;
import co.edu.unbosque.TransMilenio.security.JwtUtil;

public class ConfirmacionService {

	private UsuarioRepository usuarioRepository;
	private JwtUtil jwtUtil;
	private CorreoService correoService;

	public ConfirmacionService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil, CorreoService correoService) {
		this.usuarioRepository = usuarioRepository;
		this.jwtUtil = jwtUtil;
		this.correoService = correoService;
	}

	public void enviarConfirmacion(Usuario usuario) {
		String token = jwtUtil.generarTokenConfirmacion(usuario.getId(), usuario.getCorreo());
		correoService.enviarConfirmacion(usuario.getCorreo(), usuario.getNombreUsuario(), token);
	}

	public String confirmar(String token) {
		String mensajeError = "El enlace de confirmacion es invalido o ya vencio";
		if (token == null || token.isBlank())
			LanzadorDeExcepciones.datoInvalido(mensajeError);
		Long id = jwtUtil.obtenerIdConfirmacion(token);
		if (id == null)
			LanzadorDeExcepciones.datoInvalido(mensajeError);
		Usuario usuario = usuarioRepository.buscarPorId(id);
		String correoToken = jwtUtil.obtenerCorreoConfirmacion(token);
		if (usuario == null || usuario.getCorreo() == null || !usuario.getCorreo().equalsIgnoreCase(correoToken))
			LanzadorDeExcepciones.datoInvalido(mensajeError);
		if ("S".equals(usuario.getCorreoConfirmado()))
			return "Tu correo ya estaba confirmado. Ya puedes iniciar sesion";
		usuario.setCorreoConfirmado("S");
		usuarioRepository.guardar(usuario);
		return "Correo confirmado. Ya puedes iniciar sesion";
	}

	public void reenviar(String nombreUsuario) {
		if (nombreUsuario == null)
			return;
		Usuario usuario = usuarioRepository.buscarPorNombreUsuario(nombreUsuario);
		if (usuario != null && usuario.getCorreo() != null && !"S".equals(usuario.getCorreoConfirmado()))
			enviarConfirmacion(usuario);
	}
}