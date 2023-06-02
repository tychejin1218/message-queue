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

  @Value("${activemq.openwire.protocol}")
  private String openWireProtocol;

  @Value("${activemq.openwire.url}")
  private String openWireUrl;

  @Value("${activemq.openwire.port}")
  private String openWirePort;

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
    activeMQConnectionFactory.setBrokerURL(
        openWireProtocol + "://" + openWireUrl + ":" + openWirePort);
    activeMQConnectionFactory.setUserName(user);
    activeMQConnectionFactory.setPassword(password);
    return activeMQConnectionFactory;
  }

  @Bean
  public JmsTemplate jmsTemplate() {
    JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
    jmsTemplate.setExplicitQosEnabled(true);
    jmsTemplate.setDeliveryPersistent(false);
    jmsTemplate.setReceiveTimeout(1000 * 10);
    jmsTemplate.setTimeToLive(6000 * 10);
    return jmsTemplate;
  }

  @Bean
  public JmsTemplate jmsTemplate2() {
    JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
    jmsTemplate.setExplicitQosEnabled(true);
    jmsTemplate.setDeliveryPersistent(false);
    jmsTemplate.setReceiveTimeout(1000 * 10);
    jmsTemplate.setTimeToLive(6000 * 10 * 2);
    return jmsTemplate;
  }

  @Bean
  public JmsTemplate jmsTemplate3() {
    JmsTemplate jmsTemplate = new JmsTemplate(activeMQConnectionFactory());
    jmsTemplate.setExplicitQosEnabled(true);
    jmsTemplate.setDeliveryPersistent(false);
    jmsTemplate.setReceiveTimeout(1000 * 10);
    jmsTemplate.setTimeToLive(6000 * 10 * 3);
    return jmsTemplate;
  }
}
