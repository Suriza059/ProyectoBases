package co.edu.unbosque.TransMilenio.exception;

/**
 * Se lanza cuando se busca, actualiza o elimina un registro que no existe. Los controllers la convierten en
 * una respuesta 404.
 */
public class RegistroNoExisteException extends TransMilenioException {

	/**
	 * Version de serializacion de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepcion con un mensaje.
	 *
	 * @param mensaje el mensaje que describe el error
	 */
	public RegistroNoExisteException(String mensaje) {
		super(mensaje);
	}
}
