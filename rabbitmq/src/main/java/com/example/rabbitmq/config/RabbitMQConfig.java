package com.example.rabbitmq.config;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Envelope;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.MessagePropertiesConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @Value("${rabbitmq.queue.stk.name}")
  private String queueStk;

  @Value("${rabbitmq.queue.ste.name}")
  private String queueSte;

  @Value("${rabbitmq.routing.stk.key}")
  private String routingKeyStk;

  @Value("${rabbitmq.routing.ste.key}")
  private String routingKeySte;

  @Bean
  public TopicExchange exchange() {
    return new TopicExchange(exchange);
  }

  @Bean
  public Queue queueStk() {
    return new Queue(queueStk);
  }

  @Bean
  public Queue queueSte() {
    return new Queue(queueSte);
  }

  @Bean
  public Binding bindingStk() {
    return BindingBuilder
        .bind(queueStk())
        .to(exchange())
        .with(routingKeyStk);
  }

  @Bean
  public Binding bindingSte() {
    return BindingBuilder
        .bind(queueSte())
        .to(exchange())
        .with(routingKeySte);
  }

  @Bean
  public MessageConverter messageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  @Bean
  public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(messageConverter());

    return rabbitTemplate;
  }
}
