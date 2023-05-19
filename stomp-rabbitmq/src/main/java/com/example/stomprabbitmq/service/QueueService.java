package com.example.stomprabbitmq.service;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;

public class QueueService {

  public void deleteQueue(ConnectionFactory connectionFactory){

    connectionFactory.getPublisherConnectionFactory();
  }
}
