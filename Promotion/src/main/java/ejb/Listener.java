package ejb;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.WebSocket;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

@Singleton(name = "listener")
@Startup
public class Listener {
    private static final String QUEUE = "QEBAB";
    private static final String EXCHANGE = "EXREGOR";
    private static final Logger logger = LoggerFactory.getLogger(Listener.class);
    private Connection connection;
    private Channel channel;
    private WebSocket webSocket = new WebSocket();

    @PostConstruct
    public void init() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE, true, false, false, null);
            channel.exchangeDeclare(EXCHANGE, BuiltinExchangeType.FANOUT, true);
            channel.queueBind(QUEUE, EXCHANGE, "");

            logger.info("Waiting for messages");
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, StandardCharsets.UTF_8);
                    logger.info("Received: {}", message);
                    webSocket.notifyAllPeers(message);
                }
            };
            channel.basicConsume(QUEUE, true, consumer);
        } catch (IOException | TimeoutException e) {
            logger.error("Error in init method");
        }
    }

    @PreDestroy
    public void stop() {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            logger.error(e.getCause().toString());
        }
    }
}
