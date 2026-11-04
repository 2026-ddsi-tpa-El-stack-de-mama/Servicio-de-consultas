package ar.edu.utn.dds.k3003.service;

import ar.edu.utn.dds.k3003.clases.FichaIntegralDonador;
import ar.edu.utn.dds.k3003.repositories.FichaIntegralDonadorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultasService {
    private final FichaIntegralDonadorRepository repository;

    public ConsultasService(FichaIntegralDonadorRepository repository) {
        this.repository = repository;
    }

    public Optional<FichaIntegralDonador> getFichaIntegralDonador(String id){
        return Optional.of(repository.buscarPorId(id).orElseThrow());

    }
}
