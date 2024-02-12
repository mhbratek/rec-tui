package com.tui.githubfacade.config

import com.tui.githubfacade.properties.GithubApiProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig(
    private val properties: GithubApiProperties
) {

    @Bean
    fun webClient(builder: WebClient.Builder): WebClient {
        return builder.baseUrl(properties.baseUrl)
            .defaultHeaders {
                it.setBearerAuth(properties.token)
            }
            .build()
    }
}
