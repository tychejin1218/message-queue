package com.example.stompactivemq.service;

import com.example.stompactivemq.dto.MessageDto;
import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Queue;
import java.util.Enumeration;
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

      String destinationName = messageDto.getTeacherId();
      String correlationId = messageDto.getStudentId();

      jmsTemplate.browse(destinationName, (session, browser) -> {

        Enumeration enumeration = browser.getEnumeration();
        while (enumeration.hasMoreElements()) {
          Message message = (Message) enumeration.nextElement();
          log.info("MessageID: {}, CorrelationID: {}", message.getJMSMessageID(),
              message.getJMSCorrelationID());

          if (correlationId.equals(message.getJMSCorrelationID())) {
            message.acknowledge();

            Queue queue = session.createQueue(destinationName);

            MessageConsumer messageConsumer = session.createConsumer(queue,
                "JMSCorrelationID = '" + message.getJMSCorrelationID() + "'");
            Message receivedMessage = messageConsumer.receiveNoWait();

            // null...
            if (receivedMessage != null) {

              log.info("receivedMessage.getJMSMessageID: {}, message.getJMSMessageID: {}",
                  receivedMessage.getJMSMessageID(), message.getJMSMessageID());

              if (receivedMessage.getJMSMessageID().equals(message.getJMSMessageID())) {
                receivedMessage.acknowledge();
                log.info("Message Deleted - MessageId:{}", message.getJMSMessageID());
              }
            }

            messageConsumer.close();
          }
        }

        return null;
      });

      //String data = new ObjectMapper().writeValueAsString(messageDto);
      String data = messageDto.getMessage();

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
