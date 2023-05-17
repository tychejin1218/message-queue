package com.example.activemq.service;

import com.example.activemq.dto.MessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageService {

  private final JmsTemplate jmsTemplate;

  public MessageDto.Response sendMessage(MessageDto.Request messageRequest) {

    try {

      String messageJson = new ObjectMapper().writeValueAsString(messageRequest);
      log.debug("messageJson: {}", messageJson);
      jmsTemplate.convertAndSend("test-queue", messageJson);

    } catch (Exception e) {
      log.error("MessageRequest : {}", messageRequest, e);
    }

    return MessageDto.Response.builder()
        .data("성공")
        .build();
  }
}
