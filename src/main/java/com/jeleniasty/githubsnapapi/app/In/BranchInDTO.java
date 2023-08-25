package com.jeleniasty.githubsnapapi.app.In;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BranchInDTO(
  String name,
  @JsonProperty("commit") Commit lastCommitSha
) {}
