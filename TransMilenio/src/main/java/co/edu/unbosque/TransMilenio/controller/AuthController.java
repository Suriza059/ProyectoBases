package co.edu.unbosque.TransMilenio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.exception.CredencialesInvalidasException;
import co.edu.unbosque.TransMilenio.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

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
