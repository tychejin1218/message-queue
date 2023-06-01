//package com.example.stomprabbitmq.config;
//
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitMQConfig {
//
//  @Bean
//  ConnectionFactory connectionFactory() {
//    CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//    connectionFactory.setHost("localhost");
//    connectionFactory.setVirtualHost("/dev");
//    connectionFactory.setPort(5672);
//    connectionFactory.setUsername("guest");
//    connectionFactory.setPassword("guest");
////    Host 대신에 URI를 사용
////    connectionFactory.setUri("");
//    return connectionFactory;
//  }
//
//  @Bean
//  public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//    return new RabbitAdmin(connectionFactory);
//  }
//
//  @Bean
//  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//    rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
//    return rabbitTemplate;
//  }
//}
