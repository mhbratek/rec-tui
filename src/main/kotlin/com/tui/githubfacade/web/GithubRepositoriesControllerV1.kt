package com.tui.githubfacade.web

import com.tui.githubfacade.service.GithubService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/v1/repositories/")
class GithubRepositoriesControllerV1(
    private val githubService: GithubService
) {

    @GetMapping("{username}")
    fun getUserNotForkedRepositories(@PathVariable username: String): Flux<RepositoryResource> {
        return githubService.getNotForkedRepositories(username).map { RepositoryResource.fromModel(it) }
    }
}