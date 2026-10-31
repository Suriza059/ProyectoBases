package co.edu.unbosque.TransMilenio.exception;

public class LanzadorDeExcepciones {

	public static void registroNoExiste(String nombre) {
		throw new RegistroNoExisteException(nombre + " no existe");
	}

	public static void registroDuplicado(String mensaje) {
		throw new RegistroDuplicadoException(mensaje);
	}

	public static void relacionNoExiste(String mensaje) {
		throw new RelacionNoExisteException(mensaje);
	}

	public static void datoInvalido(String mensaje) {
		throw new DatoInvalidoException(mensaje);
	}
}
