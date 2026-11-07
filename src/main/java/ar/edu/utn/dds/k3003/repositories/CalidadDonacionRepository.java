package ar.edu.utn.dds.k3003.repositories;

import ar.edu.utn.dds.k3003.clases.CalidadDonacion;
import ar.edu.utn.dds.k3003.clases.EstadoDepositos;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class CalidadDonacionRepository {
    private final MongoTemplate mongoTemplate;

    public CalidadDonacionRepository(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public void guardar(CalidadDonacion donacion) {
        mongoTemplate.save(donacion);
    }

    public Optional<List<CalidadDonacion>> buscar() {
        return Optional.of(mongoTemplate.findAll(CalidadDonacion.class));
    }

    public Optional<CalidadDonacion> buscarPorId(String id) {
        return Optional.ofNullable(mongoTemplate.findById(id, CalidadDonacion.class));
    }

}
