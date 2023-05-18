package com.example.rabbitmq.componet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

  /*@RabbitListener(queues = "queue-01")
  public void consumerQueue01(Message message) {
    log.info("consumer queue 01 message : {}", message);
  }

  @RabbitListener(queues = "queue-02")
  public void consumerQueue02(Message message) {
    log.info("consumer queue 02 message : {}", message);
  }

  @RabbitListener(queues = "queue-03")
  public void consumerQueue03(Message message) {
    log.info("consumer queue 03 message : {}", message);
  }*/
}
