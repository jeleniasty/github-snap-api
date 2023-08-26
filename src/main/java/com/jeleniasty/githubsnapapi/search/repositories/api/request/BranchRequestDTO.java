package com.jeleniasty.githubsnapapi.search.repositories.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BranchRequestDTO(
  String name,
  @JsonProperty("commit") Commit lastCommitSha
) {}
