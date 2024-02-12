package com.tui.githubfacade.web

import com.tui.githubfacade.dto.Branch
import com.tui.githubfacade.dto.Repository

data class RepositoryResource(
    val name: String,
    val ownerLogin: String,
    val branches: List<BranchResource>
) {
    companion object {
        fun fromModel(model: Repository): RepositoryResource {
            return RepositoryResource(
                name = model.name,
                ownerLogin = model.owner.login,
                branches = BranchResource.fromModel(model.branches)
            )
        }
    }
}

data class BranchResource(
    val name: String,
    val lastCommitSha: String
) {
    companion object {
        fun fromModel(branches: List<Branch>): List<BranchResource> {
            return branches.map { branch ->
                BranchResource(
                    name = branch.name,
                    lastCommitSha = branch.commit.lastCommitSha
                )
            }
        }
    }
}