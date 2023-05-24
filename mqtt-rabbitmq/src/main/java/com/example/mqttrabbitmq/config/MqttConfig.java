package com.example.mqttrabbitmq.config;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Configuration
public class MqttConfig {

  private static final String SERVER_URI = "tcp://127.0.0.1:1883";
  private static final String CLIENT_ID = "mqttClient";
  private static final String USER_NAME = "guest";
  private static final String PASSWORD = "guest";

  @Bean
  public static IMqttClient iMqttClient() throws MqttException {

    IMqttClient iMqttClient = new MqttClient(SERVER_URI, CLIENT_ID);

    MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
    mqttConnectOptions.setUserName(USER_NAME);
    mqttConnectOptions.setPassword(PASSWORD.toCharArray());
    mqttConnectOptions.setAutomaticReconnect(true);
    mqttConnectOptions.setCleanSession(true);
    mqttConnectOptions.setConnectionTimeout(10);

    iMqttClient.connect(mqttConnectOptions);

    return iMqttClient;
  }

  @Bean
  public MessageChannel mqttInputChannel() {
    return new DirectChannel();
  }

  @Component
  public static class MqttSubscriber {

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMqttMessage(Message<String> message, @Header(MqttHeaders.TOPIC) String topic) {
      String payload = message.getPayload();
      System.out.println("Received MQTT message on topic " + topic + ": " + payload);
      // MQTT 메시지 처리 로직 구현
    }
  }
}
