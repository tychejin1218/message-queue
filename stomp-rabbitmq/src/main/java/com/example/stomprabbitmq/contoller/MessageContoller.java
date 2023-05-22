package com.example.stomprabbitmq.contoller;

import com.example.stomprabbitmq.dto.UserDto;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MessageContoller {

  private final SimpMessagingTemplate simpMessagingTemplate;

  @RequestMapping(value = "/send/message/test01", method = RequestMethod.POST)
  @ResponseBody
  public void sendMessagAxios01(@RequestBody UserDto userDto) {
    Map<String, Object> headers = new HashMap<>();
    headers.put("x-max-length", 3);
    simpMessagingTemplate.convertAndSend("/queue/" + userDto.getTeacherId(),
        userDto.getMessage(), headers);
  }
}
