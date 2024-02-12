package com.tui.githubfacade

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.tui.githubfacade.utils.TestGithubMockServerFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ResourceLoader
import org.springframework.test.web.reactive.server.WebTestClient
import java.nio.file.Files
import java.nio.file.Paths

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BaseIntTest {
    @Autowired
    protected lateinit var webTestClient: WebTestClient

    @Autowired
    protected lateinit var resourceLoader: ResourceLoader

    protected lateinit var githubServer: WireMockServer

    @BeforeEach
    fun setup() {
        githubServer = WireMockServer(8089)
        githubServer.start()
        WireMock.configureFor("localhost", 8089)
    }

    @AfterEach
    fun teardown() {
        githubServer.stop()
    }

    protected fun createsBranchesForTicTacToe() {
        TestGithubMockServerFactory.stubGetPathWithGivenResponse(
            path = "/repos/mhbratek/TicTacToe/branches?page=1&per_page=4",
            body = loadJsonFromFile("classpath:getBranches_tictactoe.json")
        )
        TestGithubMockServerFactory.stubGetPathWithGivenResponse(
            path = "/repos/mhbratek/TicTacToe/branches?page=2&per_page=4",
            body = "[]"
        )
    }

    protected fun createsBranchesForRobot() {
        TestGithubMockServerFactory.stubGetPathWithGivenResponse(
            path = "/repos/mhbratek/robot/branches?page=1&per_page=4",
            body = loadJsonFromFile("classpath:getBranches_robot_page_1.json")
        )
        TestGithubMockServerFactory.stubGetPathWithGivenResponse(
            path = "/repos/mhbratek/robot/branches?page=2&per_page=4",
            body = loadJsonFromFile("classpath:getBranches_robot_page_2.json")
        )
        TestGithubMockServerFactory.stubGetPathWithGivenResponse(
            path = "/repos/mhbratek/robot/branches?page=3&per_page=4",
            body = "[]"
        )
    }

    protected fun createsRepositoryRobotAndTicTacToe() {
        TestGithubMockServerFactory.stubGetPathWithGivenResponse(
            path = "/users/mhbratek/repos?page=1&per_page=4",
            body = loadJsonFromFile("classpath:getRepositories_robot_and_tiktactoe.json")
        )
        TestGithubMockServerFactory.stubGetPathWithGivenResponse(
            path = "/users/mhbratek/repos?page=2&per_page=4",
            body = "[]"
        )
    }

    protected fun loadJsonFromFile(filePath: String): String {
        val resource = resourceLoader.getResource(filePath)
        val file = resource.file
        return String(Files.readAllBytes(Paths.get(file.toURI())))
    }
}