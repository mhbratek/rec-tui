package com.tui.githubfacade.facade

import com.tui.githubfacade.exception.UserNotFoundException
import com.tui.githubfacade.properties.GithubApiProperties
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class GithubFacade(
    private val webClient: WebClient,
    private val properties: GithubApiProperties
) {
    companion object {
        private const val PAGE_QUERY_PARAM = "page"
        private const val PAGE_SIZE_QUERY_PARAM = "per_page"
        private const val FIRST_INDEX_PAGE = 1
    }

    fun fetchAllRepositories(username: String): Flux<GitRepository> {
        return fetchRepositoriesPage(username, FIRST_INDEX_PAGE)
    }

    fun fetchAllBranchesForRepository(login: String, name: String): Flux<GitBranch> {
        return fetchBranchPage(login, name, FIRST_INDEX_PAGE)
    }

    private fun fetchRepositoriesPage(username: String, page: Int): Flux<GitRepository> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/users/$username/repos")
                    .queryParam(PAGE_QUERY_PARAM, page)
                    .queryParam(PAGE_SIZE_QUERY_PARAM, properties.pageSize)
                    .build()
            }
            .retrieve()
            .onStatus({ status -> status == HttpStatus.NOT_FOUND }, {
                Mono.error(UserNotFoundException(username))
            })
            .bodyToMono(object : ParameterizedTypeReference<List<GitRepository>>() {})
            .flatMapMany { repos ->
                if (repos.isNotEmpty()) {
                    val nextRepos = fetchRepositoriesPage(username, page + 1)
                    Flux.fromIterable(repos).concatWith(nextRepos)
                } else {
                    Flux.empty()
                }
            }
    }

    private fun fetchBranchPage(login: String, repositoryName: String, page: Int): Flux<GitBranch> {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("repos/${login}/${repositoryName}/branches")
                    .queryParam(PAGE_QUERY_PARAM, page)
                    .queryParam(PAGE_SIZE_QUERY_PARAM, properties.pageSize)
                    .build()
            }
            .retrieve()
            .bodyToMono(object : ParameterizedTypeReference<List<GitBranch>>() {})
            .flatMapMany { branches ->
                if (branches.isNotEmpty()) {
                    val nextPage = fetchBranchPage(login, repositoryName, page + 1)
                    Flux.fromIterable(branches).concatWith(nextPage)
                } else {
                    Flux.empty()
                }
            }
    }

}