package com.jeleniasty.githubsnapapi.search.repositories;

import com.jeleniasty.githubsnapapi.search.repositories.api.response.RepoSearchResponseDTO;
import com.jeleniasty.githubsnapapi.search.repositories.exceptions.InvalidContentTypeException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RepoSearchController {

  private final RepoSearchService repoSearchService;

  @GetMapping(value = "/api/repos")
  public ResponseEntity<List<RepoSearchResponseDTO>> getGithubRepos(
    @RequestHeader("Accept") String acceptHeader,
    @RequestParam String username
  ) {
    if (!acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
      throw new InvalidContentTypeException("Invalid content type");
    }

    return ResponseEntity.ok().body(repoSearchService.getUserRepos(username));
  }
}
