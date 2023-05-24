package com.example.mqttrabbitmq.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;

@SpringBootTest
class MqttConfigTest {

  @Autowired
  private MqttConfig.MqttSubscriber mqttSubscriber;

  @Test
  public void testHandleMqttMessage() {

    String topic = "T0001";
    String payload = "Test Message";

    Message<String> message = new GenericMessage<>(payload);
    mqttSubscriber.handleMqttMessage(message, topic);
  }
}
