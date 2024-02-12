package com.tui.githubfacade.exception

data class ErrorResponse(
    val status: Int,
    val message: String
)