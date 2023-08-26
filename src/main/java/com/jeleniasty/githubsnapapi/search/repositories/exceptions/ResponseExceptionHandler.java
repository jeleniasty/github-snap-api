package com.jeleniasty.githubsnapapi.search.repositories.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ResponseExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(
    GithubUserNotFoundException.class
  )
  public ResponseEntity<ErrorResponse> handleGithubUserNotFoundException(
    GithubUserNotFoundException exception
  ) {
    return ResponseEntity
      .status(HttpStatus.NOT_FOUND)
      .contentType(MediaType.APPLICATION_JSON)
      .body(
        new ErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getMessage())
      );
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(
    InvalidContentTypeException.class
  )
  public ResponseEntity<ErrorResponse> handleInvalidContentTypeException(
    InvalidContentTypeException exception
  ) {
    return ResponseEntity
      .status(HttpStatus.NOT_ACCEPTABLE)
      .contentType(MediaType.APPLICATION_JSON)
      .body(
        new ErrorResponse(
          HttpStatus.NOT_ACCEPTABLE.value(),
          exception.getMessage()
        )
      );
  }
}
