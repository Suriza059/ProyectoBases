package co.edu.unbosque.TransMilenio.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import co.edu.unbosque.TransMilenio.security.JwtInterceptor;
import co.edu.unbosque.TransMilenio.security.JwtUtil;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private JwtUtil jwtUtil;

	public WebConfig(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new JwtInterceptor(jwtUtil))
				.addPathPatterns("/**")
				.excludePathPatterns("/auth/**", "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs",
						"/v3/api-docs/**", "/error");
	}
}
