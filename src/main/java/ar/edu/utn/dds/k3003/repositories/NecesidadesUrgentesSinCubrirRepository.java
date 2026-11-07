package ar.edu.utn.dds.k3003.repositories;


import ar.edu.utn.dds.k3003.clases.NecesidadesUrgentesSinCubrir;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class NecesidadesUrgentesSinCubrirRepository {
    private final MongoTemplate mongoTemplate;

    public NecesidadesUrgentesSinCubrirRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void guardar(NecesidadesUrgentesSinCubrir necesidad) {
        mongoTemplate.save(necesidad);
    }

    public Optional<NecesidadesUrgentesSinCubrir> buscarPorId(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, NecesidadesUrgentesSinCubrir.class));
    }

    public Optional<List<NecesidadesUrgentesSinCubrir>> buscar() {
        return Optional.of(mongoTemplate.findAll(NecesidadesUrgentesSinCubrir.class));
    }
}
