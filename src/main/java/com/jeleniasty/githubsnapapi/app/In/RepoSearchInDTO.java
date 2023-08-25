package com.jeleniasty.githubsnapapi.app.In;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepoSearchInDTO(
  String name,
  Owner owner,
  boolean fork,
  @JsonProperty("branches_url") String branchesUrl
) {}
