package co.edu.unbosque.TransMilenio.exception;

/**
 * Se lanza cuando falta un dato obligatorio o un dato no es valido (por ejemplo, un rol desconocido). Los
 * controllers la convierten en una respuesta 400.
 */
public class DatoInvalidoException extends TransMilenioException {

	/**
	 * Version de serializacion de la clase.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea la excepcion con un mensaje.
	 *
	 * @param mensaje el mensaje que describe el error
	 */
	public DatoInvalidoException(String mensaje) {
		super(mensaje);
	}
}
