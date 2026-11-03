package ar.edu.utn.dds.k3003.controllers;

import ar.edu.utn.dds.k3003.services.FichaIntegralDonadorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas/fichaintegraldonador")
public class FichaIntegralDonadorController {

    private final FichaIntegralDonadorService service;

    public FichaIntegralDonadorController(FichaIntegralDonadorService service) {
        this.service = service;
    }

}