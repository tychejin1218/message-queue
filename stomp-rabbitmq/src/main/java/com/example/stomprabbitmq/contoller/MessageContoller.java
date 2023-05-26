package com.example.stomprabbitmq.contoller;

import com.example.stomprabbitmq.dto.MessageDto;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageContoller {

  private final SimpMessagingTemplate simpMessagingTemplate;

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   * @return 메시지 전송 결과
   */
  @RequestMapping(value = "/send/message", method = RequestMethod.POST)
  public ResponseEntity<?> sendMessag(@RequestBody MessageDto messageDto) {
    simpMessagingTemplate.convertAndSend("/queue/" + messageDto.getTeacherId(),
        messageDto.getMessage());
    return ResponseEntity.ok().build();
  }

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   * @return 메시지 전송 결과
   */
  @RequestMapping(value = "/send/message/header", method = RequestMethod.POST)
  public ResponseEntity<?> sendMessagHeader(@RequestBody MessageDto messageDto) {

    Map headers = Map.of(
        "amqp-message-id", messageDto.getStudentId(), // message-id
        "x-expires", 1000 * 60 * 60, // Queue가 사용되지 않은 상태(Consumer가 없을 때) 유지되는 시간이며, 초과 시 자동으로 삭제
        "x-message-ttl", 1000 * 60 * 10, // Queue에 전송된 메시지가 유지되는 시간이며, 초과 시 자동으로 삭제(큐 단위로 설정)
        "x-overflow", "drop-head", // 가장 오래된 메시지를 삭제하여 새로운 메시지를 수용
        "x-queue-name", "teacher_" + messageDto.getTeacherId()
    );

    simpMessagingTemplate.convertAndSend("/queue/" + messageDto.getTeacherId(),
        messageDto.getMessage(), headers);
    return ResponseEntity.ok().build();
  }
}
