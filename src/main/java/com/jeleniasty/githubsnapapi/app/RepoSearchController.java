package com.jeleniasty.githubsnapapi.app;

import com.jeleniasty.githubsnapapi.app.In.AuthRequest;
import com.jeleniasty.githubsnapapi.app.Out.RepoSearchOutDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RepoSearchController {

  private final RepoSearchService repoSearchService;

  @GetMapping(value = "/api/repos")
  public ResponseEntity<List<RepoSearchOutDTO>> getGithubRepos(
    @RequestHeader("Accept") String acceptHeader,
    @RequestParam String username
  ) {
    if (!acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
      throw new InvalidContentTypeException("Invalid content type");
    }
    return ResponseEntity.ok().body(repoSearchService.getUserRepos(username));
  }

  @GetMapping(value = "/api/repos-auth")
  public ResponseEntity<List<RepoSearchOutDTO>> getGithubReposWithAuth(
    @RequestHeader("Accept") String acceptHeader,
    @RequestParam String username,
    @RequestBody AuthRequest authRequest
  ) {
    if (!acceptHeader.contains(MediaType.APPLICATION_JSON_VALUE)) {
      throw new InvalidContentTypeException("Invalid content type");
    }

    return ResponseEntity
      .ok()
      .body(repoSearchService.getUserReposWithAuth(username, authRequest));
  }
}
