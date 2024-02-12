package com.tui.githubfacade.facade

import com.tui.githubfacade.BaseIntTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.test.StepVerifier

class GithubFacadeTest : BaseIntTest() {
    @Autowired
    private lateinit var githubFacade: GithubFacade

    @Test
    fun `should return github repositories`() {
        // given
        createsRepositoryRobotAndTicTacToe()

        // when
        StepVerifier.create(githubFacade.fetchAllRepositories("mhbratek"))
            .expectNextMatches { it.name == "robot" }
            .expectNextMatches { it.name == "TicTacToe" }
            .verifyComplete()
    }

    @Test
    fun `should return github branches for given owner and repository`() {
        // given
        createsBranchesForRobot()
        // when
        StepVerifier.create(githubFacade.fetchAllBranchesForRepository("mhbratek", "robot"))
            .expectNextMatches { it.name == "devs" }
            .expectNextMatches { it.name == "feature_webScrapper" }
            .expectNextMatches { it.name == "future_webscrapers_refactor" }
            .expectNextMatches { it.name == "googleBooks" }
            .expectNextMatches { it.name == "master" }
            .expectNextMatches { it.name == "modules" }
            .expectNextMatches { it.name == "persistence" }
            .expectNextMatches { it.name == "tests" }
            .verifyComplete()
    }
}