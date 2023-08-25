package com.jeleniasty.githubsnapapi.app;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

  @org.springframework.web.bind.annotation.ExceptionHandler(
    GithubUserNotFoundException.class
  )
  public ResponseEntity<Error> handleGithubUserNotFoundException(
    GithubUserNotFoundException exception
  ) {
    return exception.toResponseEntity();
  }

  @org.springframework.web.bind.annotation.ExceptionHandler(
    com.jeleniasty.githubsnapapi.app.InvalidContentTypeException.class
  )
  public ResponseEntity<Error> handleInvalidContentTypeException(
    InvalidContentTypeException exception
  ) {
    return exception.toResponseEntity();
  }
}
