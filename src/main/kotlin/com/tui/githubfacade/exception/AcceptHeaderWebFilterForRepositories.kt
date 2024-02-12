package com.tui.githubfacade.exception

import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import org.springframework.web.util.pattern.PathPatternParser
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

private val logger = KotlinLogging.logger {}

@Component
class AcceptHeaderWebFilterForRepositories : WebFilter {

    override fun filter(ctx: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val pathPatternParser = PathPatternParser()
        val targetedPathPattern = pathPatternParser.parse("/v1/repositories/{username}")

        if (targetedPathPattern.matches(ctx.request.path.pathWithinApplication())) {
            val contentType = ctx.request.headers.getFirst(HttpHeaders.ACCEPT)
            if (contentType != MediaType.APPLICATION_JSON_VALUE) {
                logger.error { "Wrong media type for request path: ${ctx.request.path}, headers: ${ctx.request.headers}" }
                val errorResponse =
                    ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), "Only application/json format is supported")
                ctx.response.statusCode = HttpStatus.NOT_ACCEPTABLE
                ctx.response.headers.contentType = MediaType.APPLICATION_JSON
                return ctx.response.writeWith(
                    ctx.response.bufferFactory().wrap(ObjectMapper().writeValueAsBytes(errorResponse)).toMono()
                )
            }
        }
        return chain.filter(ctx)

    }
}


