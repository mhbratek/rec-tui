package com.tui.githubfacade.service

import com.tui.githubfacade.dto.Repository
import com.tui.githubfacade.facade.GithubFacade
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import kotlin.math.log

private val logger = KotlinLogging.logger {}

@Service
class GithubService(
    private val githubFacade: GithubFacade
) {
    fun getNotForkedRepositories(username: String): Flux<Repository> {
        return githubFacade.fetchAllRepositories(username)
            .filter { !it.fork }
            .flatMap { repository ->
                githubFacade.fetchAllBranchesForRepository(repository.owner.login, repository.name)
                    .collectList()
                    .map { branches ->
                        Repository.fromGithubApi(repository, branches)
                    }
            }.doOnNext {
                logger.info { "Fetched ${it.name} repository, for user: $username" }
            }
    }
}


