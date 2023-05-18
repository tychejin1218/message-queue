package com.example.rabbitmq.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class KeyValueMessage {

  private String key;
  private String value;
}
