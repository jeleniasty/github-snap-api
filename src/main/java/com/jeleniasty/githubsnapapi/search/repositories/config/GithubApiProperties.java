package com.jeleniasty.githubsnapapi.search.repositories.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "github.api")
@Getter
@Setter
public class GithubApiProperties {

  private String baseUrl;
  private String users;
  private String repos;
}
