package com.jeleniasty.githubsnapapi.search.repositories.exceptions;

public class GithubUserNotFoundException extends RuntimeException {

  public GithubUserNotFoundException(String username) {
    super("User '" + username + "' not found.");
  }
}
