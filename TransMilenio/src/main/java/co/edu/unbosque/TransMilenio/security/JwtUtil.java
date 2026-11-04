package co.edu.unbosque.TransMilenio.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * Crea y revisa los tokens JWT. El token es como una manilla: se entrega una vez al iniciar sesion y se
 * muestra en cada peticion. Va firmado con una clave secreta (HS256), por eso nadie puede fabricar uno
 * falso sin conocerla.
 */
public class JwtUtil {

	/**
	 * Clave secreta con la que se firman los tokens. Debe tener minimo 32 caracteres. Conviene cambiarla
	 * por una propia.
	 */
	private static final String SECRETO = "TransMilenio-clave-secreta-JWT-cambiala-por-una-propia-2026";
	/**
	 * Tiempo de vida del token en milisegundos (2 horas).
	 */
	private static final long DURACION = 2L * 60 * 60 * 1000;

	/**
	 * Clave de firma construida a partir del secreto.
	 */
	private Key clave = Keys.hmacShaKeyFor(SECRETO.getBytes(StandardCharsets.UTF_8));

	/**
	 * Crea un token firmado con el nombre de usuario, el rol y el id, que vence a las 2 horas.
	 *
	 * @param idUsuario el identificador del usuario
	 * @param nombreUsuario el nombre de usuario (va como sujeto del token)
	 * @param rol el rol del usuario
	 * @return el token JWT
	 */
	public String generarToken(Long idUsuario, String nombreUsuario, String rol) {
		Date ahora = new Date();
		return Jwts.builder()
				.setSubject(nombreUsuario)
				.claim("rol", rol)
				.claim("idUsuario", idUsuario)
				.setIssuedAt(ahora)
				.setExpiration(new Date(ahora.getTime() + DURACION))
				.signWith(clave, SignatureAlgorithm.HS256)
				.compact();
	}

	/**
	 * Lee y verifica un token (firma y vencimiento).
	 *
	 * @param token el token JWT
	 * @return los datos del token, o null si es invalido o esta vencido
	 */
	private Claims leer(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(clave).build().parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Indica si el token es autentico y no esta vencido.
	 *
	 * @param token el token JWT
	 * @return true si es valido, false si no
	 */
	public boolean esValido(String token) {
		return leer(token) != null;
	}

	/**
	 * Obtiene el nombre de usuario guardado en el token.
	 *
	 * @param token el token JWT
	 * @return el nombre de usuario, o null si el token no es valido
	 */
	public String obtenerNombre(String token) {
		Claims claims = leer(token);
		if (claims == null)
			return null;
		return claims.getSubject();
	}

	/**
	 * Obtiene el rol guardado en el token.
	 *
	 * @param token el token JWT
	 * @return el rol, o null si el token no es valido
	 */
	public String obtenerRol(String token) {
		Claims claims = leer(token);
		if (claims == null)
			return null;
		return claims.get("rol", String.class);
	}
}
