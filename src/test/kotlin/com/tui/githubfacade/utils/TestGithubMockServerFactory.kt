package com.tui.githubfacade.utils

import com.github.tomakehurst.wiremock.client.WireMock

object TestGithubMockServerFactory {
    fun stubGetPathWithGivenResponse(
        path: String,
        body: String
    ) {
        WireMock.stubFor(
            WireMock.get(WireMock.urlEqualTo(path))
                .willReturn(
                    WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(body)
                )
        )
    }
}
