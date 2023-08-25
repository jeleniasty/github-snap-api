package com.jeleniasty.githubsnapapi.app;

import com.jeleniasty.githubsnapapi.app.In.AuthRequest;
import com.jeleniasty.githubsnapapi.app.In.BranchInDTO;
import com.jeleniasty.githubsnapapi.app.In.RepoSearchInDTO;
import com.jeleniasty.githubsnapapi.app.Out.BranchOutDTO;
import com.jeleniasty.githubsnapapi.app.Out.RepoSearchOutDTO;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class RepoSearchService {

  @Value("${github.api.base-url}")
  private String baseUrl;

  @Value("${github.api.users}")
  private String usersUrl;

  @Value("${github.api.repos}")
  private String reposUrl;

  private final WebClient webClient;

  public List<RepoSearchOutDTO> getUserReposWithAuth(
    String username,
    AuthRequest authRequest
  ) {
    webClient
      .mutate()
      .defaultHeader(
        HttpHeaders.AUTHORIZATION,
        "Bearer " + authRequest.accessToken()
      )
      .build();
    return getUserRepos(username);
  }

  public List<RepoSearchOutDTO> getUserRepos(String username) {
    System.out.println(webClient);
    return getUsersRepositoryNames(username)
      .stream()
      .filter(this::isNotFork)
      .map(repo ->
        new RepoSearchOutDTO(
          repo.name(),
          repo.owner().login(),
          getRepoBranches(repo)
        )
      )
      .toList();
  }

  private List<RepoSearchInDTO> getUsersRepositoryNames(String username) {
    return webClient
      .get()
      .uri(getUserReposSearchUrl(username))
      .retrieve()
      .onStatus(
        status -> status.value() == HttpStatus.NOT_FOUND.value(),
        response -> Mono.error(new GithubUserNotFoundException(username))
      )
      .bodyToFlux(RepoSearchInDTO.class)
      .collectList()
      .block();
  }

  private boolean isNotFork(RepoSearchInDTO repoSearchInDTO) {
    return !repoSearchInDTO.fork();
  }

  private List<BranchOutDTO> getRepoBranches(RepoSearchInDTO repo) {
    return Objects
      .requireNonNull(
        webClient
          .get()
          .uri(cleanBranchUrl(repo.branchesUrl()))
          .retrieve()
          .bodyToFlux(BranchInDTO.class)
          .collectList()
          .block()
      )
      .stream()
      .map(this::toBranchOutDTO)
      .toList();
  }

  private String getUserReposSearchUrl(String username) {
    return baseUrl + usersUrl + "/" + username + reposUrl;
  }

  private BranchOutDTO toBranchOutDTO(BranchInDTO branch) {
    return new BranchOutDTO(branch.name(), branch.lastCommitSha().sha());
  }

  private String cleanBranchUrl(String branchesUrl) {
    return branchesUrl.replace("{/branch}", "");
  }
}
