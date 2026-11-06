package co.edu.unbosque.TransMilenio.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

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
import co.edu.unbosque.TransMilenio.service.EstacionService;
import co.edu.unbosque.TransMilenio.service.EstadisticaService;
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

@Configuration
public class AppConfig {

	@Bean
	public TroncalRepository troncalRepository(JdbcTemplate jdbcTemplate) {
		return new TroncalRepository(jdbcTemplate);
	}

	@Bean
	public EstacionRepository estacionRepository(JdbcTemplate jdbcTemplate) {
		return new EstacionRepository(jdbcTemplate);
	}

	@Bean
	public GuardaRepository guardaRepository(JdbcTemplate jdbcTemplate) {
		return new GuardaRepository(jdbcTemplate);
	}

	@Bean
	public TarifaRepository tarifaRepository(JdbcTemplate jdbcTemplate) {
		return new TarifaRepository(jdbcTemplate);
	}

	@Bean
	public ModalidadRepository modalidadRepository(JdbcTemplate jdbcTemplate) {
		return new ModalidadRepository(jdbcTemplate);
	}

	@Bean
	public EventoRepository eventoRepository(JdbcTemplate jdbcTemplate) {
		return new EventoRepository(jdbcTemplate);
	}

	@Bean
	public AgresionRepository agresionRepository(JdbcTemplate jdbcTemplate) {
		return new AgresionRepository(jdbcTemplate);
	}

	@Bean
	public InfractorRepository infractorRepository(JdbcTemplate jdbcTemplate) {
		return new InfractorRepository(jdbcTemplate);
	}

	@Bean
	public EventoInfractorRepository eventoInfractorRepository(JdbcTemplate jdbcTemplate) {
		return new EventoInfractorRepository(jdbcTemplate);
	}

	@Bean
	public ComparendoRepository comparendoRepository(JdbcTemplate jdbcTemplate) {
		return new ComparendoRepository(jdbcTemplate);
	}

	@Bean
	public PagoRepository pagoRepository(JdbcTemplate jdbcTemplate) {
		return new PagoRepository(jdbcTemplate);
	}

	@Bean
	public RegistroCambiosRepository registroCambiosRepository(JdbcTemplate jdbcTemplate) {
		return new RegistroCambiosRepository(jdbcTemplate);
	}

	@Bean
	public UsuarioRepository usuarioRepository(JdbcTemplate jdbcTemplate) {
		return new UsuarioRepository(jdbcTemplate);
	}

	@Bean
	public UsuarioGuardaRepository usuarioGuardaRepository(JdbcTemplate jdbcTemplate) {
		return new UsuarioGuardaRepository(jdbcTemplate);
	}

	@Bean
	public AdministradorRepository administradorRepository(JdbcTemplate jdbcTemplate) {
		return new AdministradorRepository(jdbcTemplate);
	}

	@Bean
	public AnalistaRepository analistaRepository(JdbcTemplate jdbcTemplate) {
		return new AnalistaRepository(jdbcTemplate);
	}

	@Bean
	public TroncalService troncalService(TroncalRepository troncalRepository) {
		return new TroncalService(troncalRepository);
	}

	@Bean
	public EstacionService estacionService(EstacionRepository estacionRepository, TroncalRepository troncalRepository) {
		return new EstacionService(estacionRepository, troncalRepository);
	}

	@Bean
	public GuardaService guardaService(GuardaRepository guardaRepository, EstacionRepository estacionRepository) {
		return new GuardaService(guardaRepository, estacionRepository);
	}

	@Bean
	public TarifaService tarifaService(TarifaRepository tarifaRepository) {
		return new TarifaService(tarifaRepository);
	}

	@Bean
	public ModalidadService modalidadService(ModalidadRepository modalidadRepository) {
		return new ModalidadService(modalidadRepository);
	}

	@Bean
	public EventoService eventoService(EventoRepository eventoRepository, EstacionRepository estacionRepository, TarifaRepository tarifaRepository, ModalidadRepository modalidadRepository, GuardaRepository guardaRepository) {
		return new EventoService(eventoRepository, estacionRepository, tarifaRepository, modalidadRepository, guardaRepository);
	}

	@Bean
	public AgresionService agresionService(AgresionRepository agresionRepository, EventoRepository eventoRepository, GuardaRepository guardaRepository) {
		return new AgresionService(agresionRepository, eventoRepository, guardaRepository);
	}

	@Bean
	public InfractorService infractorService(InfractorRepository infractorRepository) {
		return new InfractorService(infractorRepository);
	}

	@Bean
	public EventoInfractorService eventoInfractorService(EventoInfractorRepository eventoInfractorRepository, EventoRepository eventoRepository, InfractorRepository infractorRepository) {
		return new EventoInfractorService(eventoInfractorRepository, eventoRepository, infractorRepository);
	}

	@Bean
	public ComparendoService comparendoService(ComparendoRepository comparendoRepository, EventoInfractorRepository eventoInfractorRepository) {
		return new ComparendoService(comparendoRepository, eventoInfractorRepository);
	}

	@Bean
	public PagoService pagoService(PagoRepository pagoRepository, ComparendoRepository comparendoRepository) {
		return new PagoService(pagoRepository, comparendoRepository);
	}

	@Bean
	public RegistroCambiosService registroCambiosService(RegistroCambiosRepository registroCambiosRepository, UsuarioRepository usuarioRepository, ComparendoRepository comparendoRepository) {
		return new RegistroCambiosService(registroCambiosRepository, usuarioRepository, comparendoRepository);
	}

	@Bean
	public UsuarioService usuarioService(UsuarioRepository usuarioRepository) {
		UsuarioService servicio = new UsuarioService(usuarioRepository);
		servicio.crearAdministradorInicial();
		return servicio;
	}

	@Bean
	public UsuarioGuardaService usuarioGuardaService(UsuarioGuardaRepository usuarioGuardaRepository, UsuarioRepository usuarioRepository, GuardaRepository guardaRepository) {
		return new UsuarioGuardaService(usuarioGuardaRepository, usuarioRepository, guardaRepository);
	}

	@Bean
	public AdministradorService administradorService(AdministradorRepository administradorRepository, UsuarioRepository usuarioRepository) {
		return new AdministradorService(administradorRepository, usuarioRepository);
	}

	@Bean
	public AnalistaService analistaService(AnalistaRepository analistaRepository, UsuarioRepository usuarioRepository) {
		return new AnalistaService(analistaRepository, usuarioRepository);
	}

	@Bean
	public EstadisticaService estadisticaService(EventoRepository eventoRepository, EstacionRepository estacionRepository, TroncalRepository troncalRepository, TarifaRepository tarifaRepository) {
		return new EstadisticaService(eventoRepository, estacionRepository, troncalRepository, tarifaRepository);
	}

	@Bean
	public JwtUtil jwtUtil() {
		return new JwtUtil();
	}

	@Bean
	public AuthService authService(UsuarioRepository usuarioRepository, UsuarioGuardaRepository usuarioGuardaRepository, JwtUtil jwtUtil) {
		return new AuthService(usuarioRepository, usuarioGuardaRepository, jwtUtil);
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
				.components(new Components().addSecuritySchemes("bearerAuth",
						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
	}
}