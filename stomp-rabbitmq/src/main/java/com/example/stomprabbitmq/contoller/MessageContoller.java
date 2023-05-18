package com.example.stomprabbitmq.contoller;

import com.example.stomprabbitmq.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageContoller {

  private final SimpMessageSendingOperations simpMessageSendingOperations;

  @MessageMapping("/send")
  public void sendMessage(
      SimpMessageHeaderAccessor simpMessageHeaderAccessor,
      @Payload UserDto userDto) {

    simpMessageSendingOperations.convertAndSend("/topic/" + userDto.getTeacherId(), userDto);
  }
}
