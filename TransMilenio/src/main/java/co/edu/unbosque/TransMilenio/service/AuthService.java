package co.edu.unbosque.TransMilenio.service;

import co.edu.unbosque.TransMilenio.entity.Usuario;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;
import co.edu.unbosque.TransMilenio.security.JwtUtil;
import co.edu.unbosque.TransMilenio.security.PasswordUtil;

/**
 * Servicio de inicio de sesion. Comprueba el usuario y la contrasena y, si son correctos, entrega un token
 * JWT.
 */
public class AuthService {

	/**
	 * Repositorio donde se buscan los usuarios.
	 */
	private UsuarioRepository usuarioRepository;
	/**
	 * Utilidad que crea el token.
	 */
	private JwtUtil jwtUtil;

	/**
	 * Crea el servicio de autenticacion.
	 *
	 * @param usuarioRepository el repositorio de usuarios
	 * @param jwtUtil la utilidad de tokens
	 */
	public AuthService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
		this.usuarioRepository = usuarioRepository;
		this.jwtUtil = jwtUtil;
	}

	/**
	 * Inicia sesion: busca al usuario, verifica la contrasena y genera el token.
	 *
	 * @param nombreUsuario el nombre de usuario
	 * @param contrasena la contrasena en texto plano
	 * @return el token JWT que se usa en las siguientes peticiones
	 * @throws co.edu.unbosque.TransMilenio.exception.CredencialesInvalidasException si el usuario o la
	 *     contrasena son incorrectos
	 */
	public String login(String nombreUsuario, String contrasena) {
		if (nombreUsuario == null || contrasena == null)
			LanzadorDeExcepciones.credencialesInvalidas();
		Usuario usuario = usuarioRepository.buscarPorNombreUsuario(nombreUsuario);
		if (usuario == null || !PasswordUtil.verificar(contrasena, usuario.getContrasena()))
			LanzadorDeExcepciones.credencialesInvalidas();
		return jwtUtil.generarToken(usuario.getId(), usuario.getNombreUsuario(), usuario.getRol());
	}
}
