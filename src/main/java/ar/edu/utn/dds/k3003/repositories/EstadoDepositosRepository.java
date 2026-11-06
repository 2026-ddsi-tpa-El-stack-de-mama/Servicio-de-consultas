package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.clases.EstadoDepositos;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public Optional<EstadoDepositos> buscarPorId(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, EstadoDepositos.class));
    }

    public Optional<List<EstadoDepositos>> buscar() {
        return Optional.of(mongoTemplate.findAll(EstadoDepositos.class));
    }

    public void eliminar(String id) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(id)), EstadoDepositos.class);
    }
}
