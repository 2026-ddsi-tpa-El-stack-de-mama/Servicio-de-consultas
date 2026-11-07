package ar.edu.utn.dds.k3003.eventos;

import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.InsigniaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.MisionDTO;

import java.util.List;

public record MisionInsigniaEvento(
        String donadorId,
        List<InsigniaDTO> insignias,
        List<MisionDTO> misiones,
        Integer progresoActual,
        Integer progresoRequerido,
        String ocurridoEn
) {}
