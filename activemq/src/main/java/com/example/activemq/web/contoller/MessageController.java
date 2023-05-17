package com.example.activemq.web.contoller;

import com.example.activemq.dto.MessageDto;
import com.example.activemq.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MessageController {

  private final MessageService messageService;

  @PostMapping("/message")
  public ResponseEntity<?> sendMessage(@RequestBody MessageDto.Request messageRequest) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(messageService.sendMessage(messageRequest));
  }
}
