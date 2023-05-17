package com.example.activemq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

public class MessageDto {

  @Getter
  @Setter
  @ToString
  @SuperBuilder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Request {

    private String messageType;
    private String messageDesc;
  }

  @Getter
  @Setter
  @ToString
  @SuperBuilder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Response {

    private String data;
  }
}
