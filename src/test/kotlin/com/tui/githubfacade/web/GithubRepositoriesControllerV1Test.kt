package com.tui.githubfacade.web

import com.tui.githubfacade.BaseIntTest
import com.tui.githubfacade.utils.TestGithubMockServerFactory
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import java.nio.file.Files
import java.nio.file.Paths

class GithubRepositoriesControllerV1Test : BaseIntTest() {

    @Test
    fun `should return error message and 406 status when accept header is different than application_json`() {
        webTestClient.get().uri("/v1/repositories/testUser")
            .accept(MediaType.APPLICATION_XML)
            .exchange()
            .expectStatus().isEqualTo(406)
            .expectBody()
            .jsonPath("status").isEqualTo(406)
            .jsonPath("message").isEqualTo("Only application/json format is supported")
    }

    @Test
    fun `should return error message and 404 when username does not exists`() {
        TestGithubMockServerFactory.stubGetPathWithGivenResponse(
            path = "/users/notExistingUser/repos",
            body = loadJsonFromFile("classpath:notFoundResponse.json")
        )
        webTestClient.get().uri("/v1/repositories/notExistingUser")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(404)
            .expectBody()
            .jsonPath("status").isEqualTo(404)
            .jsonPath("message").isEqualTo("notExistingUser not found!")
    }

    @Test
    fun `should return repositories for a given username`() {
        createsRepositoryRobotAndTicTacToe()
        createsBranchesForRobot()
        createsBranchesForTicTacToe()

        webTestClient.get().uri("/v1/repositories/mhbratek")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isEqualTo(200)
            .expectBody()
            .jsonPath("$").isArray
            .jsonPath("$.length()").isEqualTo(2)
            .jsonPath("$[0].branches.length()").isEqualTo(2)
            .jsonPath("$[1].branches.length()").isEqualTo(8)
    }
}