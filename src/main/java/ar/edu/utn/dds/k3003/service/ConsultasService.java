package ar.edu.utn.dds.k3003.service;

import ar.edu.utn.dds.k3003.clases.EstadoDepositos;
import ar.edu.utn.dds.k3003.clases.FichaIntegralDonador;
import ar.edu.utn.dds.k3003.repositories.EstadoDepositosRepository;
import ar.edu.utn.dds.k3003.repositories.FichaIntegralDonadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsultasService {
    private final FichaIntegralDonadorRepository fichaIntegralDonadorRepository;
    private final EstadoDepositosRepository estadoDepositosRepository;

    public ConsultasService(FichaIntegralDonadorRepository fichaIntegralDonadorRepository, EstadoDepositosRepository estadoDepositosRepository) {
        this.fichaIntegralDonadorRepository = fichaIntegralDonadorRepository;
        this.estadoDepositosRepository = estadoDepositosRepository;
    }

    public Optional<FichaIntegralDonador> getFichaIntegralDonador(String id){
        return Optional.of(fichaIntegralDonadorRepository.buscarPorId(id).orElseThrow());
    }

    public Optional<EstadoDepositos> getEstadoDeposito(String id){
        return Optional.of(estadoDepositosRepository.buscarPorId(id).orElseThrow());
    }

    public Optional<List<EstadoDepositos>> getEstadoDepositos(){
        return Optional.of(estadoDepositosRepository.buscar().orElseThrow());
    }
}
