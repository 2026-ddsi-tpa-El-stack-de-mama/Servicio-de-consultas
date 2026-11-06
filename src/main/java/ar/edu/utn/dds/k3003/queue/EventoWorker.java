package ar.edu.utn.dds.k3003.queue;

import ar.edu.utn.dds.k3003.clases.EstadoDepositos;
import ar.edu.utn.dds.k3003.eventos.DepositoEvento;
import ar.edu.utn.dds.k3003.repositories.EstadoDepositosRepository;
import ar.edu.utn.dds.k3003.repositories.FichaIntegralDonadorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EventoWorker extends DefaultConsumer {
    private String queueName;
    private static final Logger log = LoggerFactory.getLogger(EventoWorker.class);
    FichaIntegralDonadorRepository fichaIntegralDonadorRepository;
    EstadoDepositosRepository estadoDepositosRepository;

    public EventoWorker(Channel channel, String queueName, FichaIntegralDonadorRepository fichaIntegralDonadorRepository, EstadoDepositosRepository estadoDepositosRepository) {
        super(channel);
        this.queueName = queueName;
        this.fichaIntegralDonadorRepository = fichaIntegralDonadorRepository;
        this.estadoDepositosRepository = estadoDepositosRepository;
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
                                DepositoEvento.class
                        );
                EstadoDepositos datos = new EstadoDepositos();
                datos.setIdDeposito(depositoEvento.id());

                estadoDepositosRepository.guardar(datos);


            }
            default -> throw new IllegalArgumentException(
                    "No existe el evento " + evento.tipoEvento()
            );
        }
    }
}



