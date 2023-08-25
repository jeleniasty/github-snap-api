package com.jeleniasty.githubsnapapi.app;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class InvalidContentTypeException extends RuntimeException {

  public InvalidContentTypeException(String message) {
    super(message);
  }

  public ResponseEntity<Error> toResponseEntity() {
    return ResponseEntity
      .status(HttpStatus.NOT_ACCEPTABLE)
      .contentType(MediaType.APPLICATION_JSON)
      .body(
        new Error(HttpStatus.NOT_ACCEPTABLE.value(), "Invalid content type")
      );
  }
}
