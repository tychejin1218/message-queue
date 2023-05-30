package com.example.stomprabbitmq.service;

import com.example.stomprabbitmq.dto.MessageDto;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
public class MessageService {

  private final RabbitAdmin rabbitAdmin;
  private final SimpMessagingTemplate simpMessagingTemplate;

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   */
  public void sendMessage(MessageDto messageDto) {
    simpMessagingTemplate.convertAndSend("/queue/" + messageDto.getTeacherId(),
        messageDto.getMessage());
  }

  /**
   * 메시지 브로커로 메시지를 전송
   *
   * @param messageDto 메시지 정보
   */
  public void sendMessageHeader(MessageDto messageDto) {

    Map headers = Map.of(
        "persistent", false,  // 메시지 브로커에 의해 메시지를 디스크에 지속적으로 저장 여부를 설정
        "exclusive", false, // 생성한 연결에 대해 전용으로 접근 권한 여부를 설정
        "auto-delete", true,  // 모든 구독이 해제될 때 자동으로 삭제되는지 여부를 설정
        "x-expires", 1000 * 60 * 30, // 큐가 사용되지 않은 상태(Consumer가 없을 때) 유지되는 시간이며, 초과 시 자동으로 삭제
        "x-message-ttl", 1000 * 60 * 10, // 큐(Queue)에 전송된 메시지가 유지되는 시간이며, 초과 시 자동으로 삭제(큐 단위로 설정)
        "x-max-length", 100 // 큐(Queue)에 저장할 수 있는 메시지 개수를 설정
    );

    String queueName = messageDto.getTeacherId();
    if (!isQueueExists(queueName)) {
      createQueue(queueName, headers);
    }

    simpMessagingTemplate.convertAndSend("/queue/" + queueName, messageDto.getMessage(), headers);
  }

  /**
   * 큐(Queue) 존재 여부 확인
   *
   * @param queueName 큐(Queue)명
   * @return 큐 존재 여부
   */
  public boolean isQueueExists(String queueName) {
    return rabbitAdmin.getQueueProperties(queueName) != null;
  }

  /**
   * 큐(Queue)를 생성
   *
   * @param queueName 설명
   */
  public void createQueue(String queueName, Map<String, Object> arguments) {
    Queue queue = new Queue(queueName,
        (Boolean) arguments.get("persistent"),
        (Boolean) arguments.get("exclusive"),
        (Boolean) arguments.get("auto-delete"),
        arguments);
    rabbitAdmin.declareQueue(queue);
  }
}
