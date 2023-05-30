package com.example.stomprabbitmq.contoller;

import com.example.stomprabbitmq.dto.MessageDto;
import com.example.stomprabbitmq.service.MessageService;
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
public class MessageContoller {

  private final MessageService messageService;

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   * @return 메시지 전송 결과
   */
  @RequestMapping(value = "/send/message", method = RequestMethod.POST)
  public ResponseEntity<?> sendMessage(@RequestBody MessageDto messageDto) {
    messageService.sendMessage(messageDto);
    return ResponseEntity.ok().build();
  }

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   * @return 메시지 전송 결과
   */
  @RequestMapping(value = "/send/message/header", method = RequestMethod.POST)
  public ResponseEntity<?> sendMessageHeader(@RequestBody MessageDto messageDto) {
    messageService.sendMessageHeader(messageDto);
    return ResponseEntity.ok().build();
  }

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   * @return 메시지 전송 결과
   */
  @RequestMapping(value = "/send/message/test", method = RequestMethod.POST)
  public ResponseEntity<?> sendMessageTest(@RequestBody MessageDto messageDto) {
    messageService.sendMessageTest(messageDto);
    return ResponseEntity.ok().build();
  }
}
