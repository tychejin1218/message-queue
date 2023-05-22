package com.example.stomprabbitmq.contoller;

import com.example.stomprabbitmq.dto.UserDto;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageContoller {

  private final SimpMessagingTemplate simpMessagingTemplate;
  private final AmqpAdmin amqpAdmin;
  private final SimpMessageSendingOperations simpMessageSendingOperations;

  /**
   * 토픽은 일대다(One-to-Many) 통신에 사용
   *
   * @param userDto 설명
   */
  @MessageMapping("/send/topic")
  public void sendMessageTopic(
      @Payload UserDto userDto) {
    simpMessageSendingOperations.convertAndSend("/topic/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  /**
   * 큐는 일대일(One-to-One) 통신에 사용
   *
   * @param userDto 설명
   */
  @MessageMapping("/send/queue")
  public void sendMessageQueue(
      @Payload UserDto userDto) {
    simpMessageSendingOperations.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  @MessageMapping("/send/message")
  public void sendMessage(
      @Payload UserDto userDto) {
    simpMessageSendingOperations.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  @MessageMapping("/send/message1")
  public void sendMessage1(
      @Payload UserDto userDto) {

    String queueName = userDto.getTeacherId();

    Queue queue = new Queue(queueName, false, false, false);
    amqpAdmin.declareQueue(queue);

    simpMessagingTemplate.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  @RequestMapping(value = "/send/rest/message", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios(@RequestBody UserDto userDto) {
    simpMessageSendingOperations.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  /**
   * /queue 테스트
   *
   * @param userDto 설명
   */
  /*@RequestMapping(value = "/send/message/test01", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios01(@RequestBody UserDto userDto) {
    simpMessagingTemplate.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage());
  }*/

  /**
   * /queue 테스트
   *
   * @param userDto 설명
   */
  @RequestMapping(value = "/send/message/test01", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios01(@RequestBody UserDto userDto) {
    Map<String, Object> headers = new HashMap<>();
    headers.put("x-max-length", 3);
    simpMessagingTemplate.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage(), headers);
  }

  /**
   * /amq/queue 테스트
   *
   * @param userDto 설명
   */
  @RequestMapping(value = "/send/message/test02", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios02(@RequestBody UserDto userDto) {
    simpMessagingTemplate.convertAndSend("/amq/queue/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  /**
   * /temp-queue 테스트
   *
   * @param userDto 설명
   */
  @RequestMapping(value = "/send/message/test03", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios03(@RequestBody UserDto userDto) {
    /*simpMessagingTemplate.convertAndSend("/temp-queue/" + userDto.getTeacherId(),
        userDto.getMessage());*/
    Map<String, Object> headers = new HashMap<>();
    headers.put("reply-to", "/temp-queue/test");
    simpMessageSendingOperations.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage(), headers);
  }

  /**
   * /topic 테스트
   *
   * @param userDto 설명
   */
  @RequestMapping(value = "/send/message/test04", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios04(@RequestBody UserDto userDto) {
    simpMessagingTemplate.convertAndSend("/topic/" + userDto.getTeacherId(),
        userDto.getMessage());
  }

  /**
   * /exchange 테스트
   *
   * @param userDto 설명
   */
  @RequestMapping(value = "/send/message/test05", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios05(@RequestBody UserDto userDto) {

    simpMessagingTemplate.convertAndSend("/amq.topic/" + userDto.getTeacherId(),
        userDto.getMessage());
    /*simpMessagingTemplate.convertAndSend("/exchange/" + userDto.getTeacherId(),
        userDto.getMessage());*/
  }
}
