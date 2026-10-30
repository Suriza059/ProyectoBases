package co.edu.unbosque.TransMilenio.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
import co.edu.unbosque.TransMilenio.service.AdministradorService;
import co.edu.unbosque.TransMilenio.service.AgresionService;
import co.edu.unbosque.TransMilenio.service.AnalistaService;
import co.edu.unbosque.TransMilenio.service.ComparendoService;
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

@Configuration
public class AppConfig {

	@Bean
	public TroncalRepository troncalRepository() {
		return new TroncalRepository();
	}

	@Bean
	public EstacionRepository estacionRepository() {
		return new EstacionRepository();
	}

	@Bean
	public GuardaRepository guardaRepository() {
		return new GuardaRepository();
	}

	@Bean
	public TarifaRepository tarifaRepository() {
		return new TarifaRepository();
	}

	@Bean
	public ModalidadRepository modalidadRepository() {
		return new ModalidadRepository();
	}

	@Bean
	public EventoRepository eventoRepository() {
		return new EventoRepository();
	}

	@Bean
	public AgresionRepository agresionRepository() {
		return new AgresionRepository();
	}

	@Bean
	public InfractorRepository infractorRepository() {
		return new InfractorRepository();
	}

	@Bean
	public EventoInfractorRepository eventoInfractorRepository() {
		return new EventoInfractorRepository();
	}

	@Bean
	public ComparendoRepository comparendoRepository() {
		return new ComparendoRepository();
	}

	@Bean
	public PagoRepository pagoRepository() {
		return new PagoRepository();
	}

	@Bean
	public RegistroCambiosRepository registroCambiosRepository() {
		return new RegistroCambiosRepository();
	}

	@Bean
	public UsuarioRepository usuarioRepository() {
		return new UsuarioRepository();
	}

	@Bean
	public UsuarioGuardaRepository usuarioGuardaRepository() {
		return new UsuarioGuardaRepository();
	}

	@Bean
	public AdministradorRepository administradorRepository() {
		return new AdministradorRepository();
	}

	@Bean
	public AnalistaRepository analistaRepository() {
		return new AnalistaRepository();
	}

	// Services
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
		return new UsuarioService(usuarioRepository);
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
}
