package com.jeleniasty.githubsnapapi.app.Out;

import java.util.List;

public record RepoSearchOutDTO(
  String repositoryName,
  String ownerLogin,
  List<BranchOutDTO> repositoryBranches
) {}
