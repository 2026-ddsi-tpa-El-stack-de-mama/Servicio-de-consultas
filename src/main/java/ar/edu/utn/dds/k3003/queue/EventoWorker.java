package ar.edu.utn.dds.k3003.queue;

import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.DonadorDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.EntidadBeneficaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.logistica.AsignacionDTO;
import ar.edu.utn.dds.k3003.clases.CalidadDonacion;
import ar.edu.utn.dds.k3003.clases.EstadoDepositos;
import ar.edu.utn.dds.k3003.clases.FichaIntegralDonador;
import ar.edu.utn.dds.k3003.clases.NecesidadesUrgentesSinCubrir;
import ar.edu.utn.dds.k3003.eventos.*;
import ar.edu.utn.dds.k3003.repositories.CalidadDonacionRepository;
import ar.edu.utn.dds.k3003.repositories.EstadoDepositosRepository;
import ar.edu.utn.dds.k3003.repositories.FichaIntegralDonadorRepository;
import ar.edu.utn.dds.k3003.repositories.NecesidadesUrgentesSinCubrirRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class EventoWorker extends DefaultConsumer {
    private String queueName;
    private static final Logger log = LoggerFactory.getLogger(EventoWorker.class);
    FichaIntegralDonadorRepository fichaIntegralDonadorRepository;
    EstadoDepositosRepository estadoDepositosRepository;
    NecesidadesUrgentesSinCubrirRepository necesidadesUrgentesSinCubrirRepository;
    CalidadDonacionRepository calidadDonacionRepository;

    public EventoWorker(Channel channel, String queueName, FichaIntegralDonadorRepository fichaIntegralDonadorRepository, EstadoDepositosRepository estadoDepositosRepository, NecesidadesUrgentesSinCubrirRepository necesidadesUrgentesSinCubrirRepository, CalidadDonacionRepository calidadDonacionRepository) {
        super(channel);
        this.queueName = queueName;
        this.fichaIntegralDonadorRepository = fichaIntegralDonadorRepository;
        this.estadoDepositosRepository = estadoDepositosRepository;
        this.necesidadesUrgentesSinCubrirRepository = necesidadesUrgentesSinCubrirRepository;
        this.calidadDonacionRepository = calidadDonacionRepository;
    }

    public void init() throws IOException {
// Declarar la cola desde la cual consumir mensajes
        this.getChannel().queueDeclare(this.queueName, true, false, false, null);
// Consumir mensajes de la cola
        this.getChannel().basicConsume(this.queueName, false, this);
    };

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

        try {
            //Leer el mensaje
            String json = new String(body, StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();

            EventoQueue evento = mapper.readValue(json, EventoQueue.class);

            procesarEvento(evento, mapper);

            getChannel().basicAck(envelope.getDeliveryTag(), false);

        } catch (Exception e) {
            e.printStackTrace();

            getChannel().basicNack(
                    envelope.getDeliveryTag(),
                    false,
                    true
            );
        }
    }

    private void procesarEvento(EventoQueue evento, ObjectMapper mapper) {
        switch (evento.tipoEvento()) {
            /*
            TIPOS EVENTO:
            DepositoEvento
             */

            case "DepositoEvento" -> {
                DepositoEvento depositoEvento = mapper.convertValue(
                                evento.descripcion(),
                                DepositoEvento.class);

                EstadoDepositos datos = new EstadoDepositos();
                datos.setIdDeposito(depositoEvento.id());
                datos.setOcupacion(depositoEvento.ocupacion());
                datos.setAlgoritmo(depositoEvento.algoritmo());
                datos.setAsignaciones(depositoEvento.asignaciones());
                datos.setTiempoIngresoAEntrega(0);
                datos.setEntidades(null);
                estadoDepositosRepository.guardar(datos);

            }
            case "BorrarDepositoEvento" -> {
                DepositoEvento depositoEvento = mapper.convertValue(
                        evento.descripcion(),
                        DepositoEvento.class);

                estadoDepositosRepository.eliminar(depositoEvento.id());

            }
            case "AsignacionEvento" -> {
               AsignacionEvento asignacionEvento = mapper.convertValue(
                        evento.descripcion(),
                       AsignacionEvento.class);

               EstadoDepositos datos = estadoDepositosRepository.buscarPorId(asignacionEvento.depositoID()).orElseThrow();
               datos.getAsignaciones().add(asignacionEvento.asignacionDTO());
                estadoDepositosRepository.guardar(datos);
            }
            case "ModificarAsignacionEvento" -> {
                AsignacionEvento asignacionEvento = mapper.convertValue(
                        evento.descripcion(),
                        AsignacionEvento.class);

                EstadoDepositos datos = estadoDepositosRepository.buscarPorId(asignacionEvento.depositoID()).orElseThrow();
                List<AsignacionDTO> asignaciones = datos.getAsignaciones();

                for (int i = 0; i < asignaciones.size(); i++) {
                    AsignacionDTO actual = asignaciones.get(i);
                    if (actual.id().equals(asignacionEvento.asignacionDTO().id())) {
                        asignaciones.set(i, new AsignacionDTO(
                                actual.id(),
                                actual.paqueteID(),
                                actual.necesidadID(),
                                actual.fecha(),
                                asignacionEvento.asignacionDTO().estado()
                        )); break;
                    }
                }
                estadoDepositosRepository.guardar(datos);
            }
            case "DonadorEvento" -> {
                DonadorEvento donadorEvento = mapper.convertValue(
                        evento.descripcion(),
                        DonadorEvento.class);

                FichaIntegralDonador datos = new FichaIntegralDonador();
                datos.setIdDonador(donadorEvento.id());
                datos.setNombre(donadorEvento.nombre());
                datos.setEstado(donadorEvento.estado());
                datos.setCategoria(donadorEvento.categoria());
                datos.setInsignias(null);
                datos.setMisiones(null);
                datos.setDonacionesPorEstado(0);
                datos.setQuejas(donadorEvento.quejasRecibidas());

                fichaIntegralDonadorRepository.guardar(datos);
            }
            case "ModificarDonadorEvento" -> {
                DonadorEvento donadorEvento = mapper.convertValue(
                        evento.descripcion(),
                        DonadorEvento.class);

                FichaIntegralDonador datos = fichaIntegralDonadorRepository.buscarPorId(donadorEvento.id()).orElseThrow();
                datos.setEstado(donadorEvento.estado());
                datos.setCategoria(donadorEvento.categoria());
                datos.setQuejas(donadorEvento.quejasRecibidas());

                fichaIntegralDonadorRepository.guardar(datos);
            }

            case "ModificarNecesidadEvento" -> {
                NecesidadEvento necesidadEvento = mapper.convertValue(evento.descripcion(), NecesidadEvento.class);
                NecesidadesUrgentesSinCubrir datos = necesidadesUrgentesSinCubrirRepository.buscarPorId(necesidadEvento.id()).orElseThrow();

            }

            case "QuejaEvento" -> {
                DonadorDTO donadorEvento = mapper.convertValue(evento.descripcion(), DonadorDTO.class);
                List<CalidadDonacion> calidadDonacionList = calidadDonacionRepository.buscar().orElseThrow();

                for (int i = 0; i < calidadDonacionList.size(); i++) {
                    CalidadDonacion actual = calidadDonacionList.get(i);
                    if(Objects.equals(donadorEvento.categoria(), actual.getCategoria())){
                        actual.getDonadorConQuejas().add(donadorEvento);
                        calidadDonacionRepository.guardar(actual);
                        break;
                    }
                }
            }

            case "EntidadEvento" -> {
                EntidadBeneficaDTO entidadEvento = mapper.convertValue(evento.descripcion(), EntidadBeneficaDTO.class);
            }

            case "EntidadDepositoEvento" -> {
                EntidadDepositoEvento entidadDepositoEvento = mapper.convertValue(evento.descripcion(), EntidadDepositoEvento.class);
                EstadoDepositos datos = estadoDepositosRepository.buscarPorId(entidadDepositoEvento.depositoID()).orElseThrow();
                datos.getEntidades().add(entidadDepositoEvento.entidad());
                estadoDepositosRepository.guardar(datos);

            }

            //case ""

            default -> throw new IllegalArgumentException(
                    "No existe el evento " + evento.tipoEvento()
            );
        }
    }
}



