package co.edu.unbosque.TransMilenio.exception;

/**
 * Excepcion base del sistema. Todas las excepciones propias de TransMilenio heredan de ella, asi los
 * controllers pueden atraparlas con un solo catch. Es una excepcion no verificada (hereda de
 * RuntimeException).
 */
public class TransMilenioException extends RuntimeException {

	/**
	 * Version de serializacion de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepcion con un mensaje.
	 *
	 * @param mensaje el mensaje que describe el error
	 */
	public TransMilenioException(String mensaje) {
		super(mensaje);
	}
}
