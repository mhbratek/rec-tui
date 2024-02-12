package com.tui.githubfacade.facade

data class GitRepository(
    val name: String,
    val owner: GitOwner,
    val fork: Boolean
)

data class GitOwner(val login: String)

data class GitBranch(
    val name: String,
    val commit: GitCommit
)

data class GitCommit(val sha: String)
