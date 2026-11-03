package co.edu.unbosque.TransMilenio.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;

import co.edu.unbosque.TransMilenio.repository.AdministradorRepository;
import co.edu.unbosque.TransMilenio.repository.AgresionRepository;
import co.edu.unbosque.TransMilenio.repository.AnalistaRepository;
import co.edu.unbosque.TransMilenio.repository.ComparendoRepository;
import co.edu.unbosque.TransMilenio.repository.EstacionRepository;
import co.edu.unbosque.TransMilenio.repository.EventoInfractorRepository;
import co.edu.unbosque.TransMilenio.repository.EventoRepository;
import co.edu.unbosque.TransMilenio.repository.GuardaRepository;
import co.edu.unbosque.TransMilenio.repository.InfractorRepository;
import co.edu.unbosque.TransMilenio.repository.ModalidadRepository;
import co.edu.unbosque.TransMilenio.repository.PagoRepository;
import co.edu.unbosque.TransMilenio.repository.RegistroCambiosRepository;
import co.edu.unbosque.TransMilenio.repository.TarifaRepository;
import co.edu.unbosque.TransMilenio.repository.TroncalRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioGuardaRepository;
import co.edu.unbosque.TransMilenio.repository.UsuarioRepository;
import co.edu.unbosque.TransMilenio.security.JwtUtil;
import co.edu.unbosque.TransMilenio.service.AdministradorService;
import co.edu.unbosque.TransMilenio.service.AgresionService;
import co.edu.unbosque.TransMilenio.service.AnalistaService;
import co.edu.unbosque.TransMilenio.service.AuthService;
import co.edu.unbosque.TransMilenio.service.ComparendoService;
import co.edu.unbosque.TransMilenio.service.ConfirmacionService;
import co.edu.unbosque.TransMilenio.service.CorreoService;
import co.edu.unbosque.TransMilenio.service.EstacionService;
import co.edu.unbosque.TransMilenio.service.EventoInfractorService;
import co.edu.unbosque.TransMilenio.service.EventoService;
import co.edu.unbosque.TransMilenio.service.GuardaService;
import co.edu.unbosque.TransMilenio.service.InfractorService;
import co.edu.unbosque.TransMilenio.service.ModalidadService;
import co.edu.unbosque.TransMilenio.service.PagoService;
import co.edu.unbosque.TransMilenio.service.RegistroCambiosService;
import co.edu.unbosque.TransMilenio.service.TarifaService;
import co.edu.unbosque.TransMilenio.service.TroncalService;
import co.edu.unbosque.TransMilenio.service.UsuarioGuardaService;
import co.edu.unbosque.TransMilenio.service.UsuarioService;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * Configuracion de Spring que crea y conecta los objetos del back. Como las clases repository y service no
 * llevan anotaciones, aqui se registra cada una como bean y se le pasan por constructor los repositories
 * que necesita. Tambien registra el JwtUtil, el AuthService y el boton Authorize de Swagger.
 */
@Configuration
public class AppConfig {

	/**
	 * Registra el repositorio de la troncal.
	 *
	 * @return el TroncalRepository
	 */
	@Bean
	public TroncalRepository troncalRepository() {
		return new TroncalRepository();
	}

	/**
	 * Registra el repositorio de la estacion.
	 *
	 * @return el EstacionRepository
	 */
	@Bean
	public EstacionRepository estacionRepository() {
		return new EstacionRepository();
	}

	/**
	 * Registra el repositorio del guarda.
	 *
	 * @return el GuardaRepository
	 */
	@Bean
	public GuardaRepository guardaRepository() {
		return new GuardaRepository();
	}

	/**
	 * Registra el repositorio de la tarifa.
	 *
	 * @return el TarifaRepository
	 */
	@Bean
	public TarifaRepository tarifaRepository() {
		return new TarifaRepository();
	}

	/**
	 * Registra el repositorio de la modalidad.
	 *
	 * @return el ModalidadRepository
	 */
	@Bean
	public ModalidadRepository modalidadRepository() {
		return new ModalidadRepository();
	}

	/**
	 * Registra el repositorio del evento.
	 *
	 * @return el EventoRepository
	 */
	@Bean
	public EventoRepository eventoRepository() {
		return new EventoRepository();
	}

	/**
	 * Registra el repositorio de la agresion.
	 *
	 * @return el AgresionRepository
	 */
	@Bean
	public AgresionRepository agresionRepository() {
		return new AgresionRepository();
	}

	/**
	 * Registra el repositorio del infractor.
	 *
	 * @return el InfractorRepository
	 */
	@Bean
	public InfractorRepository infractorRepository() {
		return new InfractorRepository();
	}

	/**
	 * Registra el repositorio de la relacion evento-infractor.
	 *
	 * @return el EventoInfractorRepository
	 */
	@Bean
	public EventoInfractorRepository eventoInfractorRepository() {
		return new EventoInfractorRepository();
	}

	/**
	 * Registra el repositorio del comparendo.
	 *
	 * @return el ComparendoRepository
	 */
	@Bean
	public ComparendoRepository comparendoRepository() {
		return new ComparendoRepository();
	}

	/**
	 * Registra el repositorio del pago.
	 *
	 * @return el PagoRepository
	 */
	@Bean
	public PagoRepository pagoRepository() {
		return new PagoRepository();
	}

	/**
	 * Registra el repositorio del registro de cambios.
	 *
	 * @return el RegistroCambiosRepository
	 */
	@Bean
	public RegistroCambiosRepository registroCambiosRepository() {
		return new RegistroCambiosRepository();
	}

	/**
	 * Registra el repositorio del usuario.
	 *
	 * @return el UsuarioRepository
	 */
	@Bean
	public UsuarioRepository usuarioRepository() {
		return new UsuarioRepository();
	}

	/**
	 * Registra el repositorio de la activacion de guarda.
	 *
	 * @return el UsuarioGuardaRepository
	 */
	@Bean
	public UsuarioGuardaRepository usuarioGuardaRepository() {
		return new UsuarioGuardaRepository();
	}

	/**
	 * Registra el repositorio del administrador.
	 *
	 * @return el AdministradorRepository
	 */
	@Bean
	public AdministradorRepository administradorRepository() {
		return new AdministradorRepository();
	}

	/**
	 * Registra el repositorio del analista.
	 *
	 * @return el AnalistaRepository
	 */
	@Bean
	public AnalistaRepository analistaRepository() {
		return new AnalistaRepository();
	}

	/**
	 * Registra el servicio de la troncal, con los repositories que necesita.
	 *
	 * @param troncalRepository el repositorio de la troncal
	 * @return el TroncalService
	 */
	@Bean
	public TroncalService troncalService(TroncalRepository troncalRepository) {
		return new TroncalService(troncalRepository);
	}

	/**
	 * Registra el servicio de la estacion, con los repositories que necesita.
	 *
	 * @param estacionRepository el repositorio de la estacion
	 * @param troncalRepository el repositorio de la troncal
	 * @return el EstacionService
	 */
	@Bean
	public EstacionService estacionService(EstacionRepository estacionRepository, TroncalRepository troncalRepository) {
		return new EstacionService(estacionRepository, troncalRepository);
	}

	/**
	 * Registra el servicio del guarda, con los repositories que necesita.
	 *
	 * @param guardaRepository el repositorio del guarda
	 * @param estacionRepository el repositorio de la estacion
	 * @return el GuardaService
	 */
	@Bean
	public GuardaService guardaService(GuardaRepository guardaRepository, EstacionRepository estacionRepository) {
		return new GuardaService(guardaRepository, estacionRepository);
	}

	/**
	 * Registra el servicio de la tarifa, con los repositories que necesita.
	 *
	 * @param tarifaRepository el repositorio de la tarifa
	 * @return el TarifaService
	 */
	@Bean
	public TarifaService tarifaService(TarifaRepository tarifaRepository) {
		return new TarifaService(tarifaRepository);
	}

	/**
	 * Registra el servicio de la modalidad, con los repositories que necesita.
	 *
	 * @param modalidadRepository el repositorio de la modalidad
	 * @return el ModalidadService
	 */
	@Bean
	public ModalidadService modalidadService(ModalidadRepository modalidadRepository) {
		return new ModalidadService(modalidadRepository);
	}

	/**
	 * Registra el servicio del evento, con los repositories que necesita.
	 *
	 * @param eventoRepository el repositorio del evento
	 * @param estacionRepository el repositorio de la estacion
	 * @param tarifaRepository el repositorio de la tarifa
	 * @param modalidadRepository el repositorio de la modalidad
	 * @param guardaRepository el repositorio del guarda
	 * @return el EventoService
	 */
	@Bean
	public EventoService eventoService(EventoRepository eventoRepository, EstacionRepository estacionRepository, TarifaRepository tarifaRepository, ModalidadRepository modalidadRepository, GuardaRepository guardaRepository) {
		return new EventoService(eventoRepository, estacionRepository, tarifaRepository, modalidadRepository, guardaRepository);
	}

	/**
	 * Registra el servicio de la agresion, con los repositories que necesita.
	 *
	 * @param agresionRepository el repositorio de la agresion
	 * @param eventoRepository el repositorio del evento
	 * @param guardaRepository el repositorio del guarda
	 * @return el AgresionService
	 */
	@Bean
	public AgresionService agresionService(AgresionRepository agresionRepository, EventoRepository eventoRepository, GuardaRepository guardaRepository) {
		return new AgresionService(agresionRepository, eventoRepository, guardaRepository);
	}

	/**
	 * Registra el servicio del infractor, con los repositories que necesita.
	 *
	 * @param infractorRepository el repositorio del infractor
	 * @return el InfractorService
	 */
	@Bean
	public InfractorService infractorService(InfractorRepository infractorRepository) {
		return new InfractorService(infractorRepository);
	}

	/**
	 * Registra el servicio de la relacion evento-infractor, con los repositories que necesita.
	 *
	 * @param eventoInfractorRepository el repositorio de la relacion evento-infractor
	 * @param eventoRepository el repositorio del evento
	 * @param infractorRepository el repositorio del infractor
	 * @return el EventoInfractorService
	 */
	@Bean
	public EventoInfractorService eventoInfractorService(EventoInfractorRepository eventoInfractorRepository, EventoRepository eventoRepository, InfractorRepository infractorRepository) {
		return new EventoInfractorService(eventoInfractorRepository, eventoRepository, infractorRepository);
	}

	/**
	 * Registra el servicio del comparendo, con los repositories que necesita.
	 *
	 * @param comparendoRepository el repositorio del comparendo
	 * @param eventoInfractorRepository el repositorio de la relacion evento-infractor
	 * @return el ComparendoService
	 */
	@Bean
	public ComparendoService comparendoService(ComparendoRepository comparendoRepository, EventoInfractorRepository eventoInfractorRepository) {
		return new ComparendoService(comparendoRepository, eventoInfractorRepository);
	}

	/**
	 * Registra el servicio del pago, con los repositories que necesita.
	 *
	 * @param pagoRepository el repositorio del pago
	 * @param comparendoRepository el repositorio del comparendo
	 * @return el PagoService
	 */
	@Bean
	public PagoService pagoService(PagoRepository pagoRepository, ComparendoRepository comparendoRepository) {
		return new PagoService(pagoRepository, comparendoRepository);
	}

	/**
	 * Registra el servicio del registro de cambios, con los repositories que necesita.
	 *
	 * @param registroCambiosRepository el repositorio del registro de cambios
	 * @param usuarioRepository el repositorio del usuario
	 * @param comparendoRepository el repositorio del comparendo
	 * @return el RegistroCambiosService
	 */
	@Bean
	public RegistroCambiosService registroCambiosService(RegistroCambiosRepository registroCambiosRepository, UsuarioRepository usuarioRepository, ComparendoRepository comparendoRepository) {
		return new RegistroCambiosService(registroCambiosRepository, usuarioRepository, comparendoRepository);
	}

	/**
	 * Registra el servicio del usuario, con los repositories que necesita. Ademas crea el administrador
	 * inicial.
	 *
	 * @param usuarioRepository el repositorio del usuario
	 * @return el UsuarioService
	 */
	@Bean
	public CorreoService correoService(JavaMailSender mailSender, Environment env) {
		String remitente = env.getProperty("spring.mail.username");
		String urlBase = env.getProperty("app.url-base", "http://localhost:8080");
		return new CorreoService(mailSender, remitente, urlBase);
	}

	@Bean
	public ConfirmacionService confirmacionService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil,
			CorreoService correoService) {
		return new ConfirmacionService(usuarioRepository, jwtUtil, correoService);
	}

	@Bean
	public UsuarioService usuarioService(UsuarioRepository usuarioRepository,
			ConfirmacionService confirmacionService) {
		UsuarioService servicio = new UsuarioService(usuarioRepository, confirmacionService);
		servicio.crearAdministradorInicial();
		return servicio;
	}

	/**
	 * Registra el servicio de la activacion de guarda, con los repositories que necesita.
	 *
	 * @param usuarioGuardaRepository el repositorio de la activacion de guarda
	 * @param usuarioRepository el repositorio del usuario
	 * @param guardaRepository el repositorio del guarda
	 * @return el UsuarioGuardaService
	 */
	@Bean
	public UsuarioGuardaService usuarioGuardaService(UsuarioGuardaRepository usuarioGuardaRepository, UsuarioRepository usuarioRepository, GuardaRepository guardaRepository) {
		return new UsuarioGuardaService(usuarioGuardaRepository, usuarioRepository, guardaRepository);
	}

	/**
	 * Registra el servicio del administrador, con los repositories que necesita.
	 *
	 * @param administradorRepository el repositorio del administrador
	 * @param usuarioRepository el repositorio del usuario
	 * @return el AdministradorService
	 */
	@Bean
	public AdministradorService administradorService(AdministradorRepository administradorRepository, UsuarioRepository usuarioRepository) {
		return new AdministradorService(administradorRepository, usuarioRepository);
	}

	/**
	 * Registra el servicio del analista, con los repositories que necesita.
	 *
	 * @param analistaRepository el repositorio del analista
	 * @param usuarioRepository el repositorio del usuario
	 * @return el AnalistaService
	 */
	@Bean
	public AnalistaService analistaService(AnalistaRepository analistaRepository, UsuarioRepository usuarioRepository) {
		return new AnalistaService(analistaRepository, usuarioRepository);
	}

	/**
	 * Registra la utilidad que crea y valida los tokens JWT.
	 *
	 * @return el JwtUtil
	 */
	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}

	/**
	 * Registra el servicio de inicio de sesion.
	 *
	 * @param usuarioRepository el repositorio de usuarios
	 * @param jwtUtil la utilidad de tokens
	 * @return el AuthService
	 */
	@Bean
	public AuthService authService(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
		return new AuthService(usuarioRepository, jwtUtil);
	}

	/**
	 * Agrega a Swagger el boton Authorize para pegar el token JWT (esquema Bearer) y que se envie en cada
	 * peticion.
	 *
	 * @return la configuracion de OpenAPI
	 */
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new Components().addSecuritySchemes("bearerAuth",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}
}
