package ar.edu.utn.dds.k3003.services;

import ar.edu.utn.dds.k3003.clases.FichaIntegralDonador;
import ar.edu.utn.dds.k3003.repositories.FichaIntegralDonadorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FichaIntegralDonadorService {
    private final FichaIntegralDonadorRepository repository;

    public FichaIntegralDonadorService(FichaIntegralDonadorRepository repository) {
        this.repository = repository;
    }

    public void actualizarFicha(FichaIntegralDonador fichaIntegralDonador) {
        repository.guardar(fichaIntegralDonador);
    }

    public Optional<FichaIntegralDonador> buscarDonador(String id) {
        return repository.buscarPorId(id);
    }
}
