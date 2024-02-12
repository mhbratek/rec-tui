package com.tui.githubfacade.exception

class UserNotFoundException(username: String): RuntimeException("$username not found!")