package org.closs.server.config

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.install
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.http.content.staticResources
import io.ktor.server.plugins.requestvalidation.RequestValidationException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.server.routing.routing
import org.closs.core.types.APIResponse
import org.closs.core.types.ServerResponse.badRequest
import org.closs.core.types.ServerResponse.forbidden
import org.closs.core.types.ServerResponse.internalServerError
import org.closs.core.types.ServerResponse.unauthorized
import org.slf4j.LoggerFactory

fun Application.configureRouting() {
    val logger = LoggerFactory.getLogger("Routing")

    install(StatusPages) {
        exception<Throwable> { call: ApplicationCall, cause: Throwable ->
            logger.error("Unhandled error at: ${call.request.path()}", cause)
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = internalServerError(
                    message = "Something unexpected happened, try again later."
                ) as APIResponse.Failure
            )
        }

        exception<RequestValidationException> { call, cause ->
            logger.error("Validation error", cause)
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = badRequest(
                    data = cause.reasons.joinToString(", "),
                    message = "Invalid request."
                ) as APIResponse.Failure
            )
        }

        status(HttpStatusCode.Unauthorized) { call, code ->
            val id = call.principal<JWTPrincipal>()
                ?.payload
                ?.getClaim("user_claims")
                ?.asMap()
                ?.get("user_id") as String?
            if (id != null) {
                return@status call.respond(
                    status = HttpStatusCode.Forbidden,
                    message = forbidden(
                        message = "You are not allowed to access this resource"
                    ) as APIResponse.Failure
                )
            }

            call.respond(
                status = code,
                message = unauthorized(
                    message = "You are not authorized to access this endpoint"
                ) as APIResponse.Failure
            )
        }

        status(HttpStatusCode.Forbidden) { call, code ->
            call.respond(
                status = code,
                message = forbidden(
                    message = "You are not allowed to access this resource"
                ) as APIResponse.Failure
            )
        }
    }

//    install(SSE)

    routing {
        staticResources("/static", "static")
    }
}
