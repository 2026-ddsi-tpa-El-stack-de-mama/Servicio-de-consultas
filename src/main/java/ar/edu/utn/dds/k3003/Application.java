package ar.edu.utn.dds.k3003;

import ar.edu.utn.dds.k3003.queue.EventoWorker;
import ar.edu.utn.dds.k3003.repositories.CalidadDonacionRepository;
import ar.edu.utn.dds.k3003.repositories.EstadoDepositosRepository;
import ar.edu.utn.dds.k3003.repositories.FichaIntegralDonadorRepository;
import ar.edu.utn.dds.k3003.repositories.NecesidadesUrgentesSinCubrirRepository;
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
    private final NecesidadesUrgentesSinCubrirRepository necesidadesUrgentesSinCubrirRepository;
    private final CalidadDonacionRepository calidadDonacionRepository;


    public Application(
            EstadoDepositosRepository estadoDepositosRepository,
            FichaIntegralDonadorRepository fichaIntegralDonadorRepository,
            NecesidadesUrgentesSinCubrirRepository necesidadesUrgentesSinCubrirRepository,
            CalidadDonacionRepository calidadDonacionRepository

    ) {
        this.estadoDepositosRepository = estadoDepositosRepository;
        this.fichaIntegralDonadorRepository = fichaIntegralDonadorRepository;
        this.necesidadesUrgentesSinCubrirRepository = necesidadesUrgentesSinCubrirRepository;
        this.calidadDonacionRepository = calidadDonacionRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Map<String, String> env = System.getenv();

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(env.get("E_QUEUE_HOST"));
        factory.setUsername(env.get("E_QUEUE_USERNAME"));
        factory.setPassword(env.get("E_QUEUE_PASSWORD"));
        factory.setVirtualHost(env.get("E_QUEUE_USERNAME"));
        String queueName = env.get("E_QUEUE_NAME");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        EventoWorker worker = new EventoWorker(
                        channel,
                        queueName,
                fichaIntegralDonadorRepository,
                estadoDepositosRepository,
                necesidadesUrgentesSinCubrirRepository,
                calidadDonacionRepository
                );

        worker.init();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}