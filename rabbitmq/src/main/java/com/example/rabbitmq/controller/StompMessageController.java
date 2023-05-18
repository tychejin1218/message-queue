package com.example.rabbitmq.controller;

import com.example.rabbitmq.dto.UserDto;
import com.example.rabbitmq.service.MessageService;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Controller
public class StompMessageController {

  private final SimpMessageSendingOperations simpMessageSendingOperations;

  @MessageMapping("/hello")
  public void sendMessage(UserDto userDto) throws Exception {
//    simpMessageSendingOperations.convertAndSend("/topic/" + userDto.getUserId(),  userDto.getMessage());
    Map map = new HashMap<String, Object>();
    map.put("auto-delete", true);
    simpMessageSendingOperations.convertAndSend("/queue/" + userDto.getTeacherId(),  userDto.getMessage(), map);
  }
}
