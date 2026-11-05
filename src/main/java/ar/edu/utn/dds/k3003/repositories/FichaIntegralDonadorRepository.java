package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.clases.FichaIntegralDonador;
import ar.edu.utn.dds.k3003.eventos.DepositoEvento;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class FichaIntegralDonadorRepository {

    private final MongoTemplate mongoTemplate;

    public FichaIntegralDonadorRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void guardar(DepositoEvento depositoEvento) {
        mongoTemplate.save(depositoEvento);
    }

    public Optional<FichaIntegralDonador> buscarPorId(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, FichaIntegralDonador.class));
    }
}