package com.example.rabbitmq.controller;

import com.example.rabbitmq.dto.UserDto;
import com.example.rabbitmq.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MessageController {

  private final MessageService messageService;

  @PostMapping(value = "/send/message", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> sendMessage(@RequestBody UserDto userDto) throws Exception {
    messageService.sendMessage(userDto);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body("성공");
  }
}
