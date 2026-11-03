package co.edu.unbosque.TransMilenio.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class CorreoService {

	private JavaMailSender mailSender;
	private String remitente;
	private String urlBase;

	public CorreoService(JavaMailSender mailSender, String remitente, String urlBase) {
		this.mailSender = mailSender;
		this.remitente = remitente;
		this.urlBase = urlBase;
	}

	public void enviarConfirmacion(String destino, String nombreUsuario, String token) {
		String enlace = urlBase + "/auth/confirmar?token=" + token;
		String texto = "Hola " + nombreUsuario + ",\n\n"
				+ "Para confirmar tu correo abre este enlace (vence en 24 horas):\n" + enlace + "\n\n"
				+ "Si no creaste esta cuenta, ignora este mensaje.";
		try {
			SimpleMailMessage mensaje = new SimpleMailMessage();
			if (remitente != null && !remitente.isBlank())
				mensaje.setFrom(remitente);
			mensaje.setTo(destino);
			mensaje.setSubject("Confirma tu correo - TransMilenio");
			mensaje.setText(texto);
			mailSender.send(mensaje);
		} catch (Exception e) {
			System.out.println("No se pudo enviar el correo a " + destino + ": " + e.getMessage());
			System.out.println("Enlace de confirmacion: " + enlace);
		}
	}
}