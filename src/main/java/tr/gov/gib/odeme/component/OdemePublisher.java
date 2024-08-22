package tr.gov.gib.odeme.component;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tr.gov.gib.odeme.entity.Odeme;

@Component
public class OdemePublisher {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.client.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.client.routing}")
    private String routing;


    public OdemePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public boolean publish(Odeme odeme) {
        try {
            rabbitTemplate.convertAndSend(exchange, routing, odeme);
            return true;
        } catch (AmqpException e) {
            return false;
        }
    }
}
