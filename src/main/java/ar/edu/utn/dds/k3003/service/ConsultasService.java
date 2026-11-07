package ar.edu.utn.dds.k3003.service;

import ar.edu.utn.dds.k3003.clases.CalidadDonacion;
import ar.edu.utn.dds.k3003.clases.EstadoDepositos;
import ar.edu.utn.dds.k3003.clases.FichaIntegralDonador;
import ar.edu.utn.dds.k3003.clases.NecesidadesUrgentesSinCubrir;
import ar.edu.utn.dds.k3003.repositories.CalidadDonacionRepository;
import ar.edu.utn.dds.k3003.repositories.EstadoDepositosRepository;
import ar.edu.utn.dds.k3003.repositories.FichaIntegralDonadorRepository;
import ar.edu.utn.dds.k3003.repositories.NecesidadesUrgentesSinCubrirRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultasService {
    private final FichaIntegralDonadorRepository fichaIntegralDonadorRepository;
    private final EstadoDepositosRepository estadoDepositosRepository;
    private final CalidadDonacionRepository calidadDonacionRepository;
    private final NecesidadesUrgentesSinCubrirRepository necesidadesUrgentesSinCubrirRepository;

    public ConsultasService(FichaIntegralDonadorRepository fichaIntegralDonadorRepository, EstadoDepositosRepository estadoDepositosRepository, CalidadDonacionRepository calidadDonacionRepository, NecesidadesUrgentesSinCubrirRepository necesidadesUrgentesSinCubrirRepository) {
        this.fichaIntegralDonadorRepository = fichaIntegralDonadorRepository;
        this.estadoDepositosRepository = estadoDepositosRepository;
        this.calidadDonacionRepository = calidadDonacionRepository;
        this.necesidadesUrgentesSinCubrirRepository = necesidadesUrgentesSinCubrirRepository;
    }

    public Optional<FichaIntegralDonador> getFichaIntegralDonador(String id){
        return Optional.of(fichaIntegralDonadorRepository.buscarPorId(id).orElseThrow());
    }

    public Optional<List<FichaIntegralDonador>> getFichaIntegralDonadores(){
        return Optional.of(fichaIntegralDonadorRepository.buscar().orElseThrow());
    }

    public Optional<EstadoDepositos> getEstadoDeposito(String id){
        return Optional.of(estadoDepositosRepository.buscarPorId(id).orElseThrow());
    }

    public Optional<List<EstadoDepositos>> getEstadoDepositos(){
        return Optional.of(estadoDepositosRepository.buscar().orElseThrow());
    }

    public Optional<CalidadDonacion> getCalidadDonacion(String id){
        return Optional.of(calidadDonacionRepository.buscarPorId(id).orElseThrow());
    }

    public Optional<List<CalidadDonacion>> getCalidadDonaciones(){
        return Optional.of(calidadDonacionRepository.buscar().orElseThrow());
    }

    public Optional<NecesidadesUrgentesSinCubrir> getNecesidadUrgenteSinCubrir(String id){
        return Optional.of(necesidadesUrgentesSinCubrirRepository.buscarPorId(id).orElseThrow());
    }

    public Optional<List<NecesidadesUrgentesSinCubrir>> getNecesidadesUrgentesSinCubrir(){
        return Optional.of(necesidadesUrgentesSinCubrirRepository.buscar().orElseThrow());
    }

}
