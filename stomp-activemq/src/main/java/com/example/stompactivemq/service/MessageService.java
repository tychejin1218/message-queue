package com.example.stompactivemq.service;

import com.example.stompactivemq.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

  private final JmsTemplate jmsTemplate;
  private final JmsTemplate jmsTemplate2;
  private final JmsTemplate jmsTemplate3;

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   */
  public void sendMessage(MessageDto messageDto) {

    try {

      //String data = new ObjectMapper().writeValueAsString(messageDto);
      String data = messageDto.getMessage();
      String destinationName = messageDto.getTeacherId();
      String correlationId = messageDto.getStudentId();

      jmsTemplate.convertAndSend(destinationName, data, message -> {
        message.setStringProperty("subject-code", messageDto.getSubjectCode());
        message.setJMSCorrelationID(correlationId);
        return message;
      });

    } catch (Exception e) {
      log.error("sendMessage:{}", messageDto, e);
    }
  }

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   */
  public void sendMessage2(MessageDto messageDto) {
    String queueName = messageDto.getTeacherId();
    jmsTemplate2.convertAndSend(queueName, messageDto.getMessage());
  }

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   */
  public void sendMessage3(MessageDto messageDto) {
    String queueName = messageDto.getTeacherId();
    jmsTemplate3.convertAndSend(queueName, messageDto.getMessage());
  }
}
