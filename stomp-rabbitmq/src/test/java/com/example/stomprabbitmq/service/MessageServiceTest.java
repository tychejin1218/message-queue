package com.example.stomprabbitmq.service;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class MessageServiceTest {

  @Autowired
  MessageService messageService;

  @Test
  void testIsQueueExists() {

    String queueName = "test";
    Boolean isQueue = messageService.isQueueExists(queueName);
    log.info("isQueue : {}", isQueue);
  }
}
