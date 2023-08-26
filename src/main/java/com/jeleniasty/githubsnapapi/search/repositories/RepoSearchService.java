package com.jeleniasty.githubsnapapi.search.repositories;

import com.jeleniasty.githubsnapapi.search.repositories.api.request.BranchRequestDTO;
import com.jeleniasty.githubsnapapi.search.repositories.api.request.RepoSearchRequestDTO;
import com.jeleniasty.githubsnapapi.search.repositories.api.response.BranchResponseDTO;
import com.jeleniasty.githubsnapapi.search.repositories.api.response.RepoSearchResponseDTO;
import com.jeleniasty.githubsnapapi.search.repositories.config.GithubApiProperties;
import com.jeleniasty.githubsnapapi.search.repositories.exceptions.GithubUserNotFoundException;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RepoSearchService {

  private final GithubApiProperties githubApiProperties;

  private final WebClient webClient;

  public List<RepoSearchResponseDTO> getUserRepos(String username) {
    return getUsersRepositoryNames(username)
      .stream()
      .filter(this::isNotFork)
      .map(repo ->
        new RepoSearchResponseDTO(
          repo.name(),
          repo.owner().login(),
          getRepoBranches(repo)
        )
      )
      .toList();
  }

  private List<RepoSearchRequestDTO> getUsersRepositoryNames(String username) {
    return webClient
      .get()
      .uri(getUserReposSearchUrl(username))
      .retrieve()
      .onStatus(
        status -> status.value() == HttpStatus.NOT_FOUND.value(),
        response -> Mono.error(new GithubUserNotFoundException(username))
      )
      .bodyToFlux(RepoSearchRequestDTO.class)
      .collectList()
      .block();
  }

  private boolean isNotFork(RepoSearchRequestDTO repoSearchRequestDTO) {
    return !repoSearchRequestDTO.fork();
  }

  private List<BranchResponseDTO> getRepoBranches(RepoSearchRequestDTO repo) {
    var request = webClient
      .get()
      .uri(cleanBranchUrl(repo.branchesUrl()))
      .retrieve()
      .bodyToFlux(BranchRequestDTO.class)
      .collectList()
      .block();

    return request == null
      ? Collections.emptyList()
      : request.stream().map(this::toBranchOutDTO).toList();
  }

  private String getUserReposSearchUrl(String username) {
    return (
      githubApiProperties.getBaseUrl() +
      githubApiProperties.getUsers() +
      "/" +
      username +
      githubApiProperties.getRepos()
    );
  }

  private BranchResponseDTO toBranchOutDTO(BranchRequestDTO branch) {
    return new BranchResponseDTO(branch.name(), branch.lastCommitSha().sha());
  }

  private String cleanBranchUrl(String branchesUrl) {
    return branchesUrl.replace("{/branch}", "");
  }
}
