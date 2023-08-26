package com.jeleniasty.githubsnapapi.search.repositories.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RepoSearchRequestDTO(
  String name,
  Owner owner,
  boolean fork,
  @JsonProperty("branches_url") String branchesUrl
) {}
