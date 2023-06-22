package com.example.activemq.service;


import com.example.activemq.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageTopicService {

  @Value("${activemq.topic.name}")
  private String topicName;

  private final JmsTemplate jmsTemplateTopic;

  /**
   * Topic으로 메시지를 발행
   *
   * @param messageDto 발행할 메시지의 DTO 객체
   */
  public void sendMessageTopic(MessageDto messageDto) {
    log.info("Message sent to topic: {}", messageDto.toString());
    jmsTemplateTopic.convertAndSend(topicName, messageDto);
  }

  /**
   * Topic에서 메시지를 구독
   *
   * @param messageDto 구독한 메시지를 담고 있는 MessageDto 객체
   */
  @JmsListener(destination = "${activemq.topic.name}", containerFactory = "containerFactoryTopic")
  public void reciveMessageTopic01(MessageDto messageDto) {
    log.info("Received Message from Topic: {}", messageDto.toString());
  }

  /**
   * Topic에서 메시지를 구독
   *
   * @param messageDto 구독한 메시지를 담고 있는 MessageDto 객체
   */
  @JmsListener(destination = "${activemq.topic.name}", containerFactory = "containerFactoryTopic")
  public void reciveMessageTopic02(MessageDto messageDto) {
    log.info("Received Message from Topic: {}", messageDto.toString());
  }

  /**
   * Topic에서 메시지를 구독
   *
   * @param messageDto 구독한 메시지를 담고 있는 MessageDto 객체
   */
  @JmsListener(destination = "${activemq.topic.name}", containerFactory = "containerFactoryTopic")
  public void reciveMessageTopic03(MessageDto messageDto) {
    log.info("Received Message from Topic: {}", messageDto.toString());
  }
}
