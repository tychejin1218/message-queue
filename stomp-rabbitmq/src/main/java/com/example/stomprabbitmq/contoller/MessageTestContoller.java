package com.example.stomprabbitmq.contoller;

import com.example.stomprabbitmq.dto.UserDto;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageTestContoller {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final ConnectionFactory connectionFactory;
//  private final RabbitAdmin rabbitAdmin;
//  private final RabbitTemplate rabbitTemplate;

  /*@MessageMapping("/send/message")
  public void sendMessage(@RequestBody MessageDto messageDto) {
    simpMessagingTemplate.convertAndSend("/queue/" + messageDto.getTeacherId(), messageDto.getMessage());
  }*/

  @RequestMapping(value = "/send/message/test01", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessag01(@RequestBody UserDto userDto) throws Exception {

    Map headers = Map.of(
        "amqp-message-id", userDto.getUserId(), // message-id
        "x-expires", 1000 * 60 * 60, // Queue가 사용되지 않은 상태(Consumer가 없을 때) 유지되는 시간이며, 초과 시 자동으로 삭제
        "x-message-ttl", 1000 * 60 * 10, // Queue에 전송된 메시지가 유지되는 시간이며, 초과 시 자동으로 삭제(큐 단위로 설정)
        "x-overflow", "drop-head", // 가장 오래된 메시지를 삭제하여 새로운 메시지를 수용
        "x-queue-name", "teacher_" + userDto.getTeacherId()
    );

    simpMessagingTemplate.convertAndSend("/queue/" + userDto.getTeacherId(), userDto, headers);
  }

  @RequestMapping(value = "/send/message/test02", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessag02(@RequestBody UserDto userDto) {

    Map headers = Map.of(
        "amqp-message-id", userDto.getUserId(), // message-id
        "x-expires", 1000 * 60 * 60, // Queue가 사용되지 않은 상태(Consumer가 없을 때) 유지되는 시간이며, 초과 시 자동으로 삭제
        "x-message-ttl", 1000 * 60 * 10, // Queue에 전송된 메시지가 유지되는 시간이며, 초과 시 자동으로 삭제(큐 단위로 설정)
        "x-overflow", "drop-head", // 가장 오래된 메시지를 삭제하여 새로운 메시지를 수용
        "x-queue-name", "student_" + userDto.getUserId()
    );

    simpMessagingTemplate.convertAndSend(
        "/queue/" + userDto.getTeacherId() + "-" + userDto.getUserId(), userDto, headers);
  }

  @RequestMapping(value = "/send/message/test03", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessag03(@RequestBody UserDto userDto) throws Exception {

    Map headers = Map.of(
        "amqp-message-id", userDto.getUserId(), // message-id
        "x-expires", 1000 * 60 * 60, // Queue가 사용되지 않은 상태(Consumer가 없을 때) 유지되는 시간이며, 초과 시 자동으로 삭제
        "x-message-ttl", 1000 * 60 * 10, // Queue에 전송된 메시지가 유지되는 시간이며, 초과 시 자동으로 삭제(큐 단위로 설정)
        //"x-overflow", "drop-head", // 가장 오래된 메시지를 삭제하여 새로운 메시지를 수용
        "x-queue-name", "teacher_" + userDto.getTeacherId()
    );

    try (Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(true)) {

      BasicProperties basicProperties = new BasicProperties().builder()
          .headers(headers)
          .build();

      String queueName = userDto.getTeacherId();
      channel.exchangeDeclare("lvc", "x-lvc");
      channel.basicPublish("lvc", "rabbit", basicProperties, userDto.getMessage().getBytes());
      channel.queueDeclare(queueName, true, false, false, headers);
      channel.queueBind(queueName, "lvc", "rabbit");
      //GetResponse response = channel.basicGet(queueName, true);
      //log.info("response : {}", response);
    }
  }

  /*@RequestMapping(value = "/send/message/test03", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessag03(@RequestBody UserDto userDto) throws Exception {

    Map headers = Map.of(
        "amqp-message-id", userDto.getUserId(), // message-id
        "x-expires", 1000 * 60 * 60, // Queue가 사용되지 않은 상태(Consumer가 없을 때) 유지되는 시간이며, 초과 시 자동으로 삭제
        "x-message-ttl", 1000 * 60 * 1000, // Queue에 전송된 메시지가 유지되는 시간이며, 초과 시 자동으로 삭제(큐 단위로 설정)
        "x-overflow", "drop-head", // 가장 오래된 메시지를 삭제하여 새로운 메시지를 수용
        "x-queue-name", "teacher_" + userDto.getTeacherId()
    );

    try (Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(true)) {

      String queueName = userDto.getTeacherId();
      if (isQueueExists(queueName)) {
        GetResponse response = channel.basicGet(queueName, false);
        log.info("response : {}", response);

      }
    }

    simpMessagingTemplate.convertAndSend(
        "/queue/" + userDto.getTeacherId(), userDto, headers);
  }*/

  public void deleteMessageFromQueue(String queueName) throws Exception {

    try (Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(true)) {

      GetResponse response = channel.basicGet(queueName, false);
      if (response != null) {
        log.info("response : {}", response);
        long deliveryTag = response.getEnvelope().getDeliveryTag();
        channel.basicAck(deliveryTag, false);
        System.out.println("Message deleted from the queue.");
      } else {
        System.out.println("No message found in the queue.");
      }
    }
  }

  /***
   * 큐(Queue) 존재 여부 확인
   *
   * @param queueName 큐(Queue)명
   * @return 큐 존재 여부
   */
//  public boolean isQueueExists(String queueName) {
//    return rabbitAdmin.getQueueProperties(queueName) != null;
//  }

  /**
   * 큐(Queue)를 생성
   *
   * @param queueName 설명
   */
//  public void createQueue(String queueName) {
//    Queue queue = new Queue(queueName);
//    rabbitAdmin.declareQueue(queue);
//  }
}
