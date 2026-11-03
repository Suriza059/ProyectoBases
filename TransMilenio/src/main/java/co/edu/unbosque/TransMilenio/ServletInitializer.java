package co.edu.unbosque.TransMilenio;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Permite desplegar la aplicacion como WAR en un servidor de aplicaciones externo.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * Indica cual es la clase principal de la aplicacion.
	 *
	 * @param application el constructor de la aplicacion
	 * @return el constructor configurado con TransMilenioApplication
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TransMilenioApplication.class);
	}

}
