package com.example.activemq.config;

import jakarta.jms.JMSException;
import jakarta.jms.Queue;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class ActiveMqConfig {

  @Value("${spring.activemq.broker-url}")
  private String brokerUrl;

  @Bean
  public Queue activeMQQueue() {
    return new ActiveMQQueue("test-queue");
  }

  @Bean
  public ActiveMQConnectionFactory activeMQConnectionFactory() throws JMSException {
    ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
    activeMQConnectionFactory.setBrokerURL(brokerUrl);

    return activeMQConnectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate() throws JMSException {
    return new JmsTemplate(activeMQConnectionFactory());
  }
}
