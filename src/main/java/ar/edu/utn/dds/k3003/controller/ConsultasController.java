package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.clases.EstadoDepositos;
import ar.edu.utn.dds.k3003.clases.FichaIntegralDonador;
import ar.edu.utn.dds.k3003.service.ConsultasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ConsultasController {

    private final ConsultasService service;

    public ConsultasController(ConsultasService service) {
        this.service = service;
    }

    @GetMapping("/consultas/fichaIntegralDonador/{id}")
    Optional<FichaIntegralDonador> getFichaIntegralDonador(@PathVariable String id){return service.getFichaIntegralDonador(id);}

    @GetMapping("/consultas/estadoDepositos/{id}")
    Optional<EstadoDepositos> getEstadoDeposito(@PathVariable String id){return service.getEstadoDeposito(id);}

    @GetMapping("/consultas/estadoDepositos")
    Optional<List<EstadoDepositos>> getEstadoDepositos(){return service.getEstadoDepositos();}


}