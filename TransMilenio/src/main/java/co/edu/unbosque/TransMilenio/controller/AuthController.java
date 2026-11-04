package co.edu.unbosque.TransMilenio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.exception.CredencialesInvalidasException;
import co.edu.unbosque.TransMilenio.service.AuthService;

/**
 * Controlador REST del inicio de sesion. Ruta base: /auth. Es publico: no necesita token.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class AuthController {

	/**
	 * Servicio que valida las credenciales.
	 */
	private AuthService authService;

	/**
	 * Crea el controlador.
	 *
	 * @param authService el servicio de autenticacion
	 */
	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	/**
	 * POST /auth/login. Inicia sesion con usuario y contrasena.
	 *
	 * @param nombreUsuario el nombre de usuario
	 * @param contrasena la contrasena
	 * @return 200 con el token JWT si los datos son correctos, 401 con un mensaje si no lo son
	 */
	@PostMapping("/login")
	public ResponseEntity<String> login(
			@RequestParam(name = "nombreUsuario", required = false) String nombreUsuario,
			@RequestParam(name = "contrasena", required = false) String contrasena) {
		try {
			String token = authService.login(nombreUsuario, contrasena);
			return new ResponseEntity<>(token, HttpStatus.OK);
		} catch (CredencialesInvalidasException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
}
