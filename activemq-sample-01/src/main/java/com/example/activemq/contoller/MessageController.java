package com.example.activemq.contoller;

import com.example.activemq.dto.MessageDto;
import com.example.activemq.service.MessageQueueService;
import com.example.activemq.service.MessageTopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {

  private final MessageQueueService messageQueueService;
  private final MessageTopicService messageTopicService;

  /**
   * Queue로 메시지를 발행
   *
   * @param messageDto 발행할 메시지의 DTO 객체
   * @return ResponseEntity 객체로 응답을 반환
   */
  @RequestMapping(value = "/send/message/queue", method = RequestMethod.POST)
  public ResponseEntity<?> sendMessageQueue(@RequestBody MessageDto messageDto) {
    messageQueueService.sendMessageQueue(messageDto);
    return ResponseEntity.ok("Message sent to Queue!");
  }

  /**
   * Topic으로 메시지를 발행
   *
   * @param messageDto 발행할 메시지의 DTO 객체
   * @return ResponseEntity 객체로 응답을 반환
   */
  @RequestMapping(value = "/send/message/topic", method = RequestMethod.POST)
  public ResponseEntity<?> sendMessageTopic(@RequestBody MessageDto messageDto) {
    messageTopicService.sendMessageTopic(messageDto);
    return ResponseEntity.ok("Message sent to Topic!");
  }
}
