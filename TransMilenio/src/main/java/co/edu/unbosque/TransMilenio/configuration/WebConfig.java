package co.edu.unbosque.TransMilenio.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import co.edu.unbosque.TransMilenio.security.JwtInterceptor;
import co.edu.unbosque.TransMilenio.security.JwtUtil;

/**
 * Configuracion web. Registra el JwtInterceptor para que revise todas las rutas, excepto el login
 * (/auth/**), las estadisticas de la pagina de inicio (/estadistica/**, publicas y sin token), Swagger y
 * /error.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * Utilidad de tokens que se le entrega al interceptor.
	 */
	private JwtUtil jwtUtil;

	/**
	 * Crea la configuracion web.
	 *
	 * @param jwtUtil la utilidad de tokens
	 */
	public WebConfig(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	/**
	 * Registra el JwtInterceptor en todas las rutas, salvo las publicas (/auth/**, /estadistica/**,
	 * Swagger y /error). /estadistica/** es publica porque la pagina de inicio no envia token.
	 *
	 * @param registry el registro de interceptores de Spring
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new JwtInterceptor(jwtUtil))
				.addPathPatterns("/**")
				.excludePathPatterns("/auth/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs",
						"/v3/api-docs/**", "/error", "/estadistica/**");
	}
}
