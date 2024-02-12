package com.tui.githubfacade.service

import com.tui.githubfacade.facade.GithubFacade
import com.tui.githubfacade.utils.TestGitApiResponseFactory
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import reactor.core.publisher.Flux
import reactor.test.StepVerifier


class GithubServiceTest {
    private val githubFacade = mock<GithubFacade>()
    private val githubService = GithubService(githubFacade)

    @Test
    fun `should filter out forked repositories`() {
        // given
        whenever(githubFacade.fetchAllRepositories(any())).thenAnswer {
            Flux.fromIterable(
                listOf(
                    TestGitApiResponseFactory.createGitRepository(fork = true, name = "forked"),
                    TestGitApiResponseFactory.createGitRepository(fork = false, name = "not forked")
                )
            )
        }
        whenever(githubFacade.fetchAllBranchesForRepository(any(), any())).thenAnswer {
            Flux.fromIterable(
                listOf(
                    TestGitApiResponseFactory.createGitBranch()
                )
            )
        }
        // when & then
        StepVerifier.create(githubService.getNotForkedRepositories("username"))
            .expectNextMatches { repository ->
                repository.name == "not forked"
            }
            .verifyComplete()
    }
}