package com.example.stompactivemq.controller;

import com.example.stompactivemq.dto.MessageDto;
import com.example.stompactivemq.service.MessageService;
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

  private final MessageService messageService;

  @RequestMapping(value = "/message/publish", method = RequestMethod.POST)
  public ResponseEntity<?> messagePublish(@RequestBody MessageDto messageDto) {
    messageService.sendMessage(messageDto);
    return ResponseEntity.ok().build();
  }
}
