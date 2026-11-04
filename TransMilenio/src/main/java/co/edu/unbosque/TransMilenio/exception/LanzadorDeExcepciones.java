package co.edu.unbosque.TransMilenio.exception;

/**
 * Clase unica desde la que se lanzan todas las excepciones del sistema. Los services la usan para validar
 * datos sin repetir codigo. Cada metodo lanza una excepcion distinta y nunca devuelve normalmente.
 */
public class LanzadorDeExcepciones {

	/**
	 * Lanza una RegistroNoExisteException con el mensaje "{nombre} no existe".
	 *
	 * @param nombre el nombre del registro que no existe (por ejemplo, La estacion)
	 */
	public static void registroNoExiste(String nombre) {
		throw new RegistroNoExisteException(nombre + " no existe");
	}

	/**
	 * Lanza una RegistroDuplicadoException.
	 *
	 * @param mensaje el mensaje que describe el dato repetido
	 */
	public static void registroDuplicado(String mensaje) {
		throw new RegistroDuplicadoException(mensaje);
	}

	/**
	 * Lanza una RelacionNoExisteException.
	 *
	 * @param mensaje el mensaje que indica que registro relacionado no existe
	 */
	public static void relacionNoExiste(String mensaje) {
		throw new RelacionNoExisteException(mensaje);
	}

	/**
	 * Lanza una DatoInvalidoException.
	 *
	 * @param mensaje el mensaje que indica que dato es invalido o falta
	 */
	public static void datoInvalido(String mensaje) {
		throw new DatoInvalidoException(mensaje);
	}

	/**
	 * Lanza una CredencialesInvalidasException con el mensaje "Usuario o contrasena incorrectos". No indica
	 * cual de los dos datos fallo, a proposito.
	 */
	public static void credencialesInvalidas() {
		throw new CredencialesInvalidasException("Usuario o contrasena incorrectos");
	}
}
