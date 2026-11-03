package co.edu.unbosque.TransMilenio.exception;

/**
 * Se lanza en el inicio de sesion cuando el usuario o la contrasena son incorrectos. El AuthController la
 * convierte en una respuesta 401.
 */
public class CredencialesInvalidasException extends TransMilenioException {

	/**
	 * Version de serializacion de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepcion con un mensaje.
	 *
	 * @param mensaje el mensaje que describe el error
	 */
	public CredencialesInvalidasException(String mensaje) {
		super(mensaje);
	}
}
