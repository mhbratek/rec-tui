package com.tui.githubfacade

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GithubFacadeApplication

fun main(args: Array<String>) {
    runApplication<GithubFacadeApplication>(*args)
}
