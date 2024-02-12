package com.tui.githubfacade.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Configuration
@ConfigurationProperties(prefix = "github.api")
@Validated
class GithubApiProperties {

    @NotNull
    lateinit var token: String

    var pageSize: Int = 100

    @NotNull
    lateinit var baseUrl: String
}
