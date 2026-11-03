package co.edu.unbosque.TransMilenio.exception;

/**
 * Se lanza cuando se intenta crear o actualizar un registro que repite un dato que debe ser unico (por
 * ejemplo, un nombre de usuario). Los controllers la convierten en una respuesta 400.
 */
public class RegistroDuplicadoException extends TransMilenioException {

	/**
	 * Version de serializacion de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepcion con un mensaje.
	 *
	 * @param mensaje el mensaje que describe el error
	 */
	public RegistroDuplicadoException(String mensaje) {
		super(mensaje);
	}
}
