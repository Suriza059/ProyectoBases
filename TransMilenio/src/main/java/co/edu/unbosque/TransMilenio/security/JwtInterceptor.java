package co.edu.unbosque.TransMilenio.security;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

	// Rutas que solo puede usar el Administrador (con cualquier metodo)
	private static final String[] SOLO_ADMIN = { "/usuario", "/administrador", "/analista", "/usuarioGuarda", "/registroCambios" };

	// NUEVO: rutas que todos pueden leer (GET), pero donde solo el Administrador
	// puede crear, editar o eliminar (POST, PUT, DELETE)
	private static final String[] ESCRITURA_SOLO_ADMIN = { "/estacion", "/modalidad", "/tarifa", "/troncal", "/guarda" };

	private JwtUtil jwtUtil;

	public JwtInterceptor(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

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

		// NUEVO: si no es GET, en estas rutas solo puede el Administrador
		String metodo = request.getMethod();
		for (String prefijo : ESCRITURA_SOLO_ADMIN) {
			if (ruta.startsWith(prefijo) && !metodo.equals("GET") && !"Administrador".equals(rol)) {
				responder(response, 403, "Solo el Administrador puede crear, editar o eliminar en esta ruta");
				return false;
			}
		}
		return true;
	}

	private void responder(HttpServletResponse response, int estado, String mensaje) throws Exception {
		response.setStatus(estado);
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write(mensaje);
	}
}