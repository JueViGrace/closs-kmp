package org.closs.company.routes

import io.ktor.server.auth.AuthenticationStrategy
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route
import org.closs.company.data.handler.CompanyHandler
import org.closs.core.types.JwtAuthName
import org.koin.ktor.ext.inject

fun Route.companyRouter() {
    val handler: CompanyHandler by inject()

    route("/company") {
        authenticate(
            JwtAuthName.SESSION.value,
            JwtAuthName.ADMIN.value,
            strategy = AuthenticationStrategy.Required
        ) {
            route("/admin") {
                createCompany(handler)

                updateCompany(handler)
            }
        }

        getCompanyByCode(handler)
    }
}
