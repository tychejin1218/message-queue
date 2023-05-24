package com.example.mqttrabbitmq.controller;

import com.example.mqttrabbitmq.config.MqttConfig;
import com.example.mqttrabbitmq.dto.MqttDto;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MqttController {

  private final MqttConfig mqttConfig;

  @PostMapping("/api/mqtt/publish")
  public void publishMessage(@RequestBody MqttDto.Request mqttRequest) throws Exception {

    MqttMessage mqttMessage = new MqttMessage(mqttRequest.getMessage().getBytes());
    mqttMessage.setQos(0);
    mqttMessage.setRetained(true);

    mqttConfig.iMqttClient().publish(mqttRequest.getTopic(), mqttMessage);
  }

  @GetMapping("/api/mqtt/subscribe")
  public List<MqttDto.Response> subscribeChannel(@RequestParam(value = "topic") String topic)
      throws Exception {

    List<MqttDto.Response> mqttResponses = new ArrayList<>();

    CountDownLatch countDownLatch = new CountDownLatch(10);
    mqttConfig.iMqttClient().subscribeWithResponse(topic, (s, mqttMessage) -> {
      MqttDto.Response mqttResponse = new MqttDto.Response();
      mqttResponse.setId(mqttMessage.getId());
      mqttResponse.setMessage(new String(mqttMessage.getPayload()));
      mqttResponses.add(mqttResponse);
      countDownLatch.countDown();
    });

    countDownLatch.await(1000, TimeUnit.MILLISECONDS);

    return mqttResponses;
  }
}
