package co.edu.unbosque.TransMilenio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal del back de TransMilenio. Arranca la aplicacion Spring Boot.
 */
@SpringBootApplication
public class TransMilenioApplication {

	/**
	 * Punto de entrada de la aplicacion.
	 *
	 * @param args los argumentos de la linea de comandos
	 */
	public static void main(String[] args) {
		SpringApplication.run(TransMilenioApplication.class, args);
	}

}
