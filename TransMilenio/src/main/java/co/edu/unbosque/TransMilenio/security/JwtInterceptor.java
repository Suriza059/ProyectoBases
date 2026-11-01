package co.edu.unbosque.TransMilenio.security;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

	private static final String[] SOLO_ADMIN = { "/usuario", "/administrador", "/analista", "/usuarioGuarda", "/registroCambios" };

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
		return true;
	}

	private void responder(HttpServletResponse response, int estado, String mensaje) throws Exception {
		response.setStatus(estado);
		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write(mensaje);
	}
}
