package ar.edu.utn.dds.k3003.controller;

import ar.edu.utn.dds.k3003.clases.FichaIntegralDonador;
import ar.edu.utn.dds.k3003.service.ConsultasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ConsultasController {

    private final ConsultasService service;

    public ConsultasController(ConsultasService service) {
        this.service = service;
    }

    @GetMapping("/consultas/fichaintegraldonador/{id}")
    Optional<FichaIntegralDonador> getFichaIntegralDonador(@PathVariable String id){return service.getFichaIntegralDonador(id);}


}