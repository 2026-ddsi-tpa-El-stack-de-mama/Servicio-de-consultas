package ar.edu.utn.dds.k3003.service;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.DonadorDTO;
import ar.edu.utn.dds.k3003.clases.FichaIntegralDonador;
import ar.edu.utn.dds.k3003.clients.DonadoresYEntidadesClient;
import ar.edu.utn.dds.k3003.repositories.FichaIntegralDonadorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultasService {
    private final FichaIntegralDonadorRepository repository;
    private final DonadoresYEntidadesClient donadoresYEntidadesClient;

    public ConsultasService(FichaIntegralDonadorRepository repository, DonadoresYEntidadesClient donadoresYEntidadesClient) {
        this.repository = repository;
        this.donadoresYEntidadesClient = donadoresYEntidadesClient;
    }

    public Optional<FichaIntegralDonador> getFichaIntegralDonador(String id){
        return Optional.of(repository.buscarPorId(id).orElseThrow());

    }
}
