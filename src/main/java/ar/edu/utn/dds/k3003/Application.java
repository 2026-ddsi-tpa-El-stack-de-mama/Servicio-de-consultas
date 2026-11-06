package ar.edu.utn.dds.k3003;

import ar.edu.utn.dds.k3003.queue.EventoWorker;
import ar.edu.utn.dds.k3003.repositories.EstadoDepositosRepository;
import ar.edu.utn.dds.k3003.repositories.FichaIntegralDonadorRepository;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private final EstadoDepositosRepository estadoDepositosRepository;
    private final FichaIntegralDonadorRepository fichaIntegralDonadorRepository;

    public Application(
            EstadoDepositosRepository estadoDepositosRepository,
            FichaIntegralDonadorRepository fichaIntegralDonadorRepository
    ) {
        this.estadoDepositosRepository = estadoDepositosRepository;
        this.fichaIntegralDonadorRepository = fichaIntegralDonadorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Map<String, String> env = System.getenv();

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(env.get("E_QUEUE_HOST"));
        factory.setUsername(env.get("E_QUEUE_USERNAME"));
        factory.setPassword(env.get("E_QUEUE_PASSWORD"));
        factory.setUsername(env.get("E_QUEUE_USERNAME"));
        String queueName = env.get("E_QUEUE_NAME");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        EventoWorker worker = new EventoWorker(
                        channel,
                        queueName,
                fichaIntegralDonadorRepository,
                estadoDepositosRepository
                );

        worker.init();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}