package com.example.stompactivemq.config;

import jakarta.jms.Queue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@EnableJms
@Configuration
public class ActiveMQConfig {

  @Value("${activemq.broker-url}")
  private String brokerUrl;

  @Value("${activemq.user}")
  private String user;

  @Value("${activemq.password}")
  private String password;

  @Bean
  public Queue queue() {
    return new ActiveMQQueue("test-queue");
  }

  @Bean
  public ActiveMQConnectionFactory activeMQConnectionFactory() {
    ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL(brokerUrl);
    activeMQConnectionFactory.setUserName(user);
    activeMQConnectionFactory.setPassword(password);
    return activeMQConnectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate() {
    return new JmsTemplate(activeMQConnectionFactory());
  }
}
