package com.tui.githubfacade.utils

import com.tui.githubfacade.facade.GitBranch
import com.tui.githubfacade.facade.GitCommit
import com.tui.githubfacade.facade.GitOwner
import com.tui.githubfacade.facade.GitRepository

object TestGitApiResponseFactory {
    fun createGitRepository(
        name: String = "name",
        fork: Boolean = false
    ): GitRepository {
        return GitRepository(
            name = name,
            owner = GitOwner("owner"),
            fork = fork
        )
    }

    fun createGitBranch(): GitBranch {
        return GitBranch(
            name = "name",
            commit = GitCommit("sha")
        )
    }
}