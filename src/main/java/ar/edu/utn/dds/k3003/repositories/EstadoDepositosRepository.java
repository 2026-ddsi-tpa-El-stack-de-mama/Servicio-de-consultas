package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.clases.EstadoDepositos;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

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
