package com.tui.githubfacade.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(UserNotFoundException::class)
    fun handleNotFoundException(ex: UserNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            status = HttpStatus.NOT_FOUND.value(),
            message = ex.message ?: "Unknown error"
        )
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }
}