package co.edu.unbosque.TransMilenio.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {

	// Debe tener minimo 32 caracteres. Cambiala por una propia.
	private static final String SECRETO = "TransMilenio-clave-secreta-JWT-cambiala-por-una-propia-2026";
	// 2 horas
	private static final long DURACION = 2L * 60 * 60 * 1000;

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

	// Devuelve null si el token es invalido o esta vencido
	private Claims leer(String token) {
		try {
			return Jwts.parserBuilder().setSigningKey(clave).build().parseClaimsJws(token).getBody();
		} catch (Exception e) {
			return null;
		}
	}

	public boolean esValido(String token) {
		return leer(token) != null;
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
}
