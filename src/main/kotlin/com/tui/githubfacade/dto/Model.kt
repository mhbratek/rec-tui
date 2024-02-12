package com.tui.githubfacade.dto

import com.tui.githubfacade.facade.GitBranch
import com.tui.githubfacade.facade.GitCommit
import com.tui.githubfacade.facade.GitOwner
import com.tui.githubfacade.facade.GitRepository

data class Repository(
    val name: String,
    val owner: Owner,
    val branches: List<Branch>
) {
    companion object {
        fun fromGithubApi(repository: GitRepository, branches: List<GitBranch>) = Repository(
            name = repository.name,
            owner = Owner.fromGithubApi(repository.owner),
            branches = branches.map { Branch.fromGithubApi(it) }
        )
    }
}

data class Owner(val login: String) {
    companion object {
        fun fromGithubApi(owner: GitOwner): Owner {
            return Owner(login = owner.login)
        }
    }
}

data class Branch(
    val name: String,
    val commit: Commit
) {
    companion object {
        fun fromGithubApi(branch: GitBranch): Branch {
            return Branch(
                name = branch.name,
                commit = Commit.fromGithubApi(branch.commit)
            )
        }
    }
}

data class Commit(val lastCommitSha: String) {
    companion object {
        fun fromGithubApi(commit: GitCommit): Commit {
            return Commit(lastCommitSha = commit.sha)
        }
    }
}