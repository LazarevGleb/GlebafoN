package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JmsProducer {
    private RabbitTemplate rabbitTemplate;
    private Queue queue;

    private static Logger logger = LoggerFactory.getLogger(JmsProducer.class);
    private static final String MESSAGE = "update";


    @Autowired
    public JmsProducer(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    /**
     * Sends message to Promotion app
     */
    public void send() {
        logger.debug("send()");
        rabbitTemplate.convertAndSend(queue.getName(), MESSAGE.getBytes());
        logger.info("Producer sent message: {}", MESSAGE);
    }
}