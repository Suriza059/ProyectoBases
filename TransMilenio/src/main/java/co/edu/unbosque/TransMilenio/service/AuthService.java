package co.edu.unbosque.TransMilenio.service;

import co.edu.unbosque.TransMilenio.entity.Usuario;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;
import co.edu.unbosque.TransMilenio.security.JwtUtil;
import co.edu.unbosque.TransMilenio.security.PasswordUtil;

public class AuthService {

	private UsuarioRepository usuarioRepository;
	private JwtUtil jwtUtil;

	public AuthService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
		this.usuarioRepository = usuarioRepository;
		this.jwtUtil = jwtUtil;
	}

	public String login(String nombreUsuario, String contrasena) {
		if (nombreUsuario == null || contrasena == null)
			LanzadorDeExcepciones.credencialesInvalidas();
		Usuario usuario = usuarioRepository.buscarPorNombreUsuario(nombreUsuario);
		if (usuario == null || !PasswordUtil.verificar(contrasena, usuario.getContrasena()))
			LanzadorDeExcepciones.credencialesInvalidas();
		if (!"S".equals(usuario.getCorreoConfirmado()))
			LanzadorDeExcepciones.correoNoConfirmado();
		return jwtUtil.generarToken(usuario.getId(), usuario.getNombreUsuario(), usuario.getRol());
	}
}