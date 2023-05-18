package com.example.rabbitmq.componet;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Producer {

  /*@Autowired
  RabbitTemplate rabbitTemplate;

  @Scheduled(fixedDelay = 1000, initialDelay = 500)
  public void sendMessage01(){
    rabbitTemplate.convertAndSend("queue-01", "message01");
  }

  @Scheduled(fixedDelay = 2000, initialDelay = 500)
  public void sendMessage02(){
    rabbitTemplate.convertAndSend("queue-02", "message02");
  }

  @Scheduled(fixedDelay = 3000, initialDelay = 500)
  public void sendMessage03(){
    rabbitTemplate.convertAndSend("queue-03", "message03");
  }*/
}
