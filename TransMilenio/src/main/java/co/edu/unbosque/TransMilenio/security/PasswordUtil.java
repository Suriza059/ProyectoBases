package co.edu.unbosque.TransMilenio.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {

	public static String encriptar(String contrasena) {
		byte[] sal = new byte[16];
		new SecureRandom().nextBytes(sal);
		String salTexto = Base64.getEncoder().encodeToString(sal);
		return salTexto + ":" + hash(salTexto, contrasena);
	}

	public static boolean verificar(String contrasena, String guardada) {
		if (contrasena == null || guardada == null || !guardada.contains(":"))
			return false;
		String[] partes = guardada.split(":");
		return partes[1].equals(hash(partes[0], contrasena));
	}

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
