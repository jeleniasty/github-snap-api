package com.jeleniasty.githubsnapapi.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class GithubUserNotFoundException extends RuntimeException {

  private final String username;

  public GithubUserNotFoundException(String username) {
    super("User '" + username + "' not found.");
    this.username = username;
  }

  public ResponseEntity<Error> toResponseEntity() {
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .contentType(MediaType.APPLICATION_JSON)
      .body(
        new Error(
          HttpStatus.NOT_FOUND.value(),
          "User '" + username + "' not found."
        )
      );
  }
}
