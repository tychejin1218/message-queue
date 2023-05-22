package com.example.stomprabbitmq.config;

import java.security.Principal;

public class SimpleUsernamePrincipal implements Principal {
  private String username;

  public SimpleUsernamePrincipal(String username) {
    this.username = username;
  }

  @Override
  public String getName() {
    return username;
  }
}
