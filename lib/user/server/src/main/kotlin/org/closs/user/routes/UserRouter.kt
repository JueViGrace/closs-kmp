package org.closs.user.routes

import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.core.types.JwtAuthName
import org.closs.user.data.handler.UserHandler
import org.koin.ktor.ext.inject

fun Route.userRouter() {
    val handler: UserHandler by inject<UserHandler>()

    authenticate(
        JwtAuthName.SESSION.value,
        JwtAuthName.USER_ID.value,
        strategy = AuthenticationStrategy.Required
    ) {
        route("/users") {
            authenticate(JwtAuthName.ADMIN.value, strategy = AuthenticationStrategy.Required) {
                route("/admin") {
                    createUser(handler)

                    softDeleteUser(handler)

                    deleteUser(handler)
                }
            }

            getExistingUserById(handler)

            getExistingUserByUsername(handler)

            updateLastSync(handler)
        }
    }
}
