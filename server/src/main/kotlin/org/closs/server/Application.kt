package org.closs.server

import org.closs.server.config.configureHTTP
import org.closs.server.config.configureKoin
import org.closs.server.config.configureMonitoring
import org.closs.server.config.configureRoutes
import org.closs.server.config.configureRouting
import org.closs.server.config.configureSecurity
import org.closs.server.config.configureSerialization
import org.closs.server.config.configureTemplating
import org.closs.server.config.configureValidation
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    setupDotenv()
    EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSecurity()
    configureHTTP()
    configureMonitoring()
    configureRouting()
    configureSerialization()
    configureValidation()
    configureTemplating()
    configureRoutes()
}

fun setupDotenv() {
    val dotenv = dotenv {
        directory = "./src/main/resources"
        filename = ".env"
        ignoreIfMissing = true
    }

    dotenv.entries().forEach { entry ->
        System.setProperty(entry.key, entry.value)
    }
}
