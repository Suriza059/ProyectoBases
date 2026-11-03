package co.edu.unbosque.TransMilenio.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilidad para proteger contrasenas. Nunca se guarda la contrasena real: se guarda una sal aleatoria junto
 * con el hash SHA-256 de (sal + contrasena), con el formato "sal:hash".
 */
public class PasswordUtil {

	/**
	 * Convierte una contrasena en el texto que se guarda: una sal aleatoria y el hash, separados por dos
	 * puntos.
	 *
	 * @param contrasena la contrasena en texto plano
	 * @return el texto cifrado con formato sal:hash
	 */
	public static String encriptar(String contrasena) {
		byte[] sal = new byte[16];
		new SecureRandom().nextBytes(sal);
		String salTexto = Base64.getEncoder().encodeToString(sal);
		return salTexto + ":" + hash(salTexto, contrasena);
	}

	/**
	 * Comprueba si una contrasena corresponde al texto guardado.
	 *
	 * @param contrasena la contrasena escrita por la persona
	 * @param guardada el texto guardado con formato sal:hash
	 * @return true si coincide, false si no coincide o si el texto guardado no es valido
	 */
	public static boolean verificar(String contrasena, String guardada) {
		if (contrasena == null || guardada == null || !guardada.contains(":"))
			return false;
		String[] partes = guardada.split(":");
		return partes[1].equals(hash(partes[0], contrasena));
	}

	/**
	 * Calcula el hash SHA-256 de la sal junto con la contrasena.
	 *
	 * @param sal la sal aleatoria
	 * @param contrasena la contrasena en texto plano
	 * @return el hash codificado en Base64
	 */
	private static String hash(String sal, String contrasena) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] resultado = md.digest((sal + contrasena).getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(resultado);
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException(e);
		}
	}
}
