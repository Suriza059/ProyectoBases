package co.edu.unbosque.TransMilenio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.unbosque.TransMilenio.exception.CorreoNoConfirmadoException;
import co.edu.unbosque.TransMilenio.exception.CredencialesInvalidasException;
import co.edu.unbosque.TransMilenio.exception.TransMilenioException;
import co.edu.unbosque.TransMilenio.service.AuthService;
import co.edu.unbosque.TransMilenio.service.ConfirmacionService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080", "*" })
public class AuthController {

	private AuthService authService;
	private ConfirmacionService confirmacionService;

	public AuthController(AuthService authService, ConfirmacionService confirmacionService) {
		this.authService = authService;
		this.confirmacionService = confirmacionService;
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
		} catch (CorreoNoConfirmadoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
		}
	}

	// Es el enlace que llega en el correo. Se abre desde el navegador.
	@GetMapping("/confirmar")
	public ResponseEntity<String> confirmar(@RequestParam(name = "token", required = false) String token) {
		try {
			String mensaje = confirmacionService.confirmar(token);
			return new ResponseEntity<>(mensaje, HttpStatus.OK);
		} catch (TransMilenioException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/reenviar-confirmacion")
	public ResponseEntity<String> reenviarConfirmacion(
			@RequestParam(name = "nombreUsuario", required = false) String nombreUsuario) {
		confirmacionService.reenviar(nombreUsuario);
		return new ResponseEntity<>("Si el usuario existe y su correo no esta confirmado, se envio un nuevo correo", HttpStatus.OK);
	}
}