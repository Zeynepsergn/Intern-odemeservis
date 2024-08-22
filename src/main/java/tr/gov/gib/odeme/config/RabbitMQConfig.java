package tr.gov.gib.odeme.config;

import org.aopalliance.aop.Advice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableRabbit
@EnableTransactionManagement
public class RabbitMQConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConfig.class);

    @Value("${spring.rabbitmq.listener.simple.retry.max-attempts:3}")
    private int maxAttempts;
    @Value("${spring.rabbitmq.listener.simple.retry.max-interval:10000}")
    private int maxInterval;
    @Value("${spring.rabbitmq.listener.simple.retry.initial-interval:10000}")
    private int initialInterval;
    @Value("${spring.rabbitmq.listener.simple.retry.multiplier:1.0}")
    private double multiplier;

    @Bean
    protected MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    protected RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setChannelTransacted(true);
        //rabbitTemplate.setReplyErrorHandler(new BrokerExceptionHandler());
        return rabbitTemplate;
    }
//
//    @Bean
//    protected RabbitExit observableRecoverer() {
//        return new RabbitExit();
//    }

    @Bean
    protected RetryOperationsInterceptor retryInterceptor() {
        return RetryInterceptorBuilder.stateless()
                .backOffOptions(initialInterval, multiplier, maxInterval)
                .maxAttempts(maxAttempts)
//                .recoverer(observableRecoverer())
                .build();
    }


    @Bean
    protected SimpleRabbitListenerContainerFactory retryContainerFactory(ConnectionFactory connectionFactory, RetryOperationsInterceptor retryInterceptor) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter());

        Advice[] adviceChain = { retryInterceptor };
        factory.setAdviceChain(adviceChain);

        return factory;
    }

    @Bean
    @ConditionalOnMissingClass("org.springframework.orm.jpa.JpaTransactionManager")
    protected PlatformTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }


}
