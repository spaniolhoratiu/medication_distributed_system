package ro.tuc.ds2020.rabbitmq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class ConfigureRabbitMq {
    public static final String QUEUE_NAME = "testqueue";
    public static final String EXCHANGE_NAME = "testexchange";

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate() throws URISyntaxException {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public ConnectionFactory connectionFactory() throws URISyntaxException {
        URI uri = new URI(	"amqps://xlsdmhwt:9dypdfDEmDarR6rIaIpsO66t2VzZ61Eh@chinook.rmq.cloudamqp.com/xlsdmhwt");
        return new CachingConnectionFactory(uri);
    }

//    @Bean
//    Queue createQueue() {
//        return new Queue(QUEUE_NAME, false);
//    }
//
//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange(EXCHANGE_NAME);
//    }
//
//    @Bean
//    Binding binding(Queue q, TopicExchange te) {
//        return BindingBuilder.bind(q).to(te).with("test.#");
//    }

//    @Bean
//    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(QUEUE_NAME);
//        container.setMessageListener(messageListenerAdapter);
//        return container;
//    }
//
//    @Bean
//    MessageListenerAdapter listenerAdapter(ReceiveMessageHandler handler){
//        return new MessageListenerAdapter(handler, "handleMessage");
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(){
//        return new Jackson2JsonMessageConverter();
//    }


}
