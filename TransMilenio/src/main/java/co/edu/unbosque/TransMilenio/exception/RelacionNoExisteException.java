package co.edu.unbosque.TransMilenio.exception;

/**
 * Se lanza cuando una llave foranea apunta a un registro que no existe (por ejemplo, una estacion con una
 * troncal inexistente). Los controllers la convierten en una respuesta 400.
 */
public class RelacionNoExisteException extends TransMilenioException {

	/**
	 * Version de serializacion de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepcion con un mensaje.
	 *
	 * @param mensaje el mensaje que describe el error
	 */
	public RelacionNoExisteException(String mensaje) {
		super(mensaje);
	}
}
