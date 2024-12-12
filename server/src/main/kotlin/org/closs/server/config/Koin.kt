package org.closs.server.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.closs.core.api.di.serverModule
import org.koin.core.logger.Level
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    val env = environment.config.property("ktor.development").getString().toBoolean()

    install(Koin) {
        slf4jLogger(
            level = if (env) {
                Level.DEBUG
            } else {
                Level.INFO
            }
        )
        modules(serverModule())
    }
}
