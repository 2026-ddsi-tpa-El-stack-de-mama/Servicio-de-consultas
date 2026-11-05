package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.clases.EstadoDepositos;
import ar.edu.utn.dds.k3003.clases.FichaIntegralDonador;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EstadoDepositosRepository {
    private final MongoTemplate mongoTemplate;

    public EstadoDepositosRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void guardar(EstadoDepositos estadoDepositos) {
        mongoTemplate.save(estadoDepositos);
    }

}
