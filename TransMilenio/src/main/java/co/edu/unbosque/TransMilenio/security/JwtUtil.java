package co.edu.unbosque.TransMilenio.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

	private static final String SECRETO = "TransMilenio-clave-secreta-JWT-cambiala-por-una-propia-2026";
	private static final long DURACION = 2L * 60 * 60 * 1000;
	private static final long DURACION_CONFIRMACION = 24L * 60 * 60 * 1000;

	private Key clave = Keys.hmacShaKeyFor(SECRETO.getBytes(StandardCharsets.UTF_8));

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

	public String generarTokenConfirmacion(Long idUsuario, String correo) {
		Date ahora = new Date();
		return Jwts.builder()
				.setSubject(String.valueOf(idUsuario))
				.claim("tipo", "confirmacion")
				.claim("correo", correo)
				.setIssuedAt(ahora)
				.setExpiration(new Date(ahora.getTime() + DURACION_CONFIRMACION))
				.signWith(clave, SignatureAlgorithm.HS256)
				.compact();
	}

	private Claims leer(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(clave).build().parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean esValido(String token) {
		Claims claims = leer(token);
		return claims != null && claims.get("rol", String.class) != null;
	}

	public String obtenerNombre(String token) {
		Claims claims = leer(token);
		if (claims == null)
			return null;
		return claims.getSubject();
	}

	public String obtenerRol(String token) {
		Claims claims = leer(token);
		if (claims == null)
			return null;
		return claims.get("rol", String.class);
	}

	public Long obtenerIdConfirmacion(String token) {
		Claims claims = leer(token);
		if (claims == null || !"confirmacion".equals(claims.get("tipo", String.class)))
			return null;
		try {
			return Long.valueOf(claims.getSubject());
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String obtenerCorreoConfirmacion(String token) {
		Claims claims = leer(token);
		if (claims == null)
			return null;
		return claims.get("correo", String.class);
	}
}