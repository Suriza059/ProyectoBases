package co.edu.unbosque.TransMilenio.service;

import co.edu.unbosque.TransMilenio.entity.Usuario;
import co.edu.unbosque.TransMilenio.entity.UsuarioGuarda;
import co.edu.unbosque.TransMilenio.exception.LanzadorDeExcepciones;
import co.edu.unbosque.TransMilenio.repository.UsuarioGuardaRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;
import co.edu.unbosque.TransMilenio.security.JwtUtil;
import co.edu.unbosque.TransMilenio.security.PasswordUtil;

/**
 * Servicio de inicio de sesion. Comprueba el usuario y la contrasena y, si son correctos, entrega un token
 * JWT. Si el usuario tiene rol Guarda, el token tambien lleva el id del guarda que tiene activado.
 */
public class AuthService {

	/**
	 * Repositorio donde se buscan los usuarios.
	 */
	private UsuarioRepository usuarioRepository;
	/**
	 * Repositorio donde se busca el guarda activado de cada usuario.
	 */
	private UsuarioGuardaRepository usuarioGuardaRepository;
	/**
	 * Utilidad que crea el token.
	 */
	private JwtUtil jwtUtil;

	/**
	 * Crea el servicio de autenticacion.
	 *
	 * @param usuarioRepository el repositorio de usuarios
	 * @param usuarioGuardaRepository el repositorio de la activacion de guarda
	 * @param jwtUtil la utilidad de tokens
	 */
	public AuthService(UsuarioRepository usuarioRepository, UsuarioGuardaRepository usuarioGuardaRepository, JwtUtil jwtUtil) {
		this.usuarioRepository = usuarioRepository;
		this.usuarioGuardaRepository = usuarioGuardaRepository;
		this.jwtUtil = jwtUtil;
	}

	/**
	 * Inicia sesion: busca al usuario, verifica la contrasena y genera el token. Si el usuario tiene rol
	 * Guarda, busca su idGuarda en la activacion de guarda (la llave es el id del usuario) y lo incluye en
	 * el token; si no tiene guarda activado, el token se crea sin ese dato.
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
		Long idGuarda = null;
		if ("Guarda".equals(usuario.getRol())) {
			UsuarioGuarda activacion = usuarioGuardaRepository.buscarPorId(usuario.getId());
			if (activacion != null)
				idGuarda = activacion.getIdGuarda();
		}
		return jwtUtil.generarToken(usuario.getId(), usuario.getNombreUsuario(), usuario.getRol(), idGuarda);
	}
}
