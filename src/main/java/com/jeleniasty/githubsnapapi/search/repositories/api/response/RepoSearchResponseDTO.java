package com.jeleniasty.githubsnapapi.search.repositories.api.response;

import java.util.List;

public record RepoSearchResponseDTO(
  String repositoryName,
  String ownerLogin,
  List<BranchResponseDTO> repositoryBranches
) {}
