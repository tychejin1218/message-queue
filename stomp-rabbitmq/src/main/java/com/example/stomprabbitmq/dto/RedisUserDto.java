package com.example.stomprabbitmq.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class RedisUserDto {

  private String teacherId;
  private String userId;
  private String message;
}
