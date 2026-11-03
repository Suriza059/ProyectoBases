package co.edu.unbosque.TransMilenio.security;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Guardia que se ejecuta antes de cada endpoint. Revisa que la peticion traiga un token valido y que el rol
 * tenga permiso para la ruta. Reglas: sin token o con token invalido responde 401; las rutas de SOLO_ADMIN
 * solo las usa el Administrador (403 para los demas); las rutas de SOLO_ADMIN_ESCRIBIR se pueden consultar
 * con GET, pero solo el Administrador puede crear, cambiar o borrar.
 */
public class JwtInterceptor implements HandlerInterceptor {

	/**
	 * Rutas que solo puede usar el rol Administrador (con cualquier metodo HTTP).
	 */
	private static final String[] SOLO_ADMIN = { "/usuario", "/administrador", "/analista", "/usuarioGuarda",
			"/registroCambios" };

	// Rutas que todos pueden leer (GET), pero donde solo el Administrador
	// puede crear, cambiar o borrar (POST, PUT, DELETE)
	/**
	 * Rutas que todos los roles pueden consultar (GET), pero en las que solo el Administrador puede crear,
	 * cambiar o borrar.
	 */
	private static final String[] SOLO_ADMIN_ESCRIBIR = { "/troncal", "/estacion", "/tarifa", "/modalidad", "/guarda" };

	/**
	 * Utilidad para validar el token y leer su rol.
	 */
	private JwtUtil jwtUtil;

	/**
	 * Crea el interceptor.
	 *
	 * @param jwtUtil la utilidad para validar tokens
	 */
	public JwtInterceptor(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	/**
	 * Decide si la peticion puede continuar hacia el endpoint. Las consultas OPTIONS (CORS) pasan sin
	 * token.
	 *
	 * @param request la peticion HTTP
	 * @param response la respuesta HTTP, donde se escribe el error si se bloquea
	 * @param handler el manejador que atenderia la peticion
	 * @return true si la peticion puede continuar, false si fue bloqueada (401 o 403)
	 * @throws Exception si ocurre un error al atender la peticion
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (request.getMethod().equals("OPTIONS"))
			return true;

		String encabezado = request.getHeader("Authorization");
		if (encabezado == null || !encabezado.startsWith("Bearer ")) {
			responder(response, 401, "Falta el token. Inicia sesion en /auth/login");
			return false;
		}
		String token = encabezado.substring(7);
		if (!jwtUtil.esValido(token)) {
			responder(response, 401, "Token invalido o vencido");
			return false;
		}

		String ruta = request.getRequestURI();
		String rol = jwtUtil.obtenerRol(token);
		for (String prefijo : SOLO_ADMIN) {
			if (ruta.startsWith(prefijo) && !"Administrador".equals(rol)) {
				responder(response, 403, "Solo el Administrador puede usar esta ruta");
				return false;
			}
		}
		for (String prefijo : SOLO_ADMIN_ESCRIBIR) {
			if (ruta.startsWith(prefijo) && !request.getMethod().equals("GET") && !"Administrador".equals(rol)) {
				responder(response, 403, "Solo el Administrador puede crear, cambiar o borrar en esta ruta");
				return false;
			}
		}
		return true;
	}

	/**
	 * Escribe en la respuesta un estado HTTP y un mensaje de texto.
	 *
	 * @param response la respuesta HTTP
	 * @param estado el codigo de estado HTTP (401 o 403)
	 * @param mensaje el mensaje de error
	 * @throws Exception si ocurre un error al atender la peticion
	 */
	private void responder(HttpServletResponse response, int estado, String mensaje) throws Exception {
		response.setStatus(estado);
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write(mensaje);
	}
}
