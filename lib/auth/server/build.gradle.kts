plugins {
    alias(libs.plugins.kotlinJvm)
}

group = "org.closs.auth"

dependencies {
    // Types: core
    implementation(projects.lib.core.shared.types)

    // Types: server
    implementation(projects.lib.core.server.types)

    // Util: server
    implementation(projects.lib.core.server.util)

    // Database: server
    implementation(projects.lib.core.server.database)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Server
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.ktor.server.request.validation)

    // Koin
    implementation(libs.koin.core)
    implementation(libs.koin.ktor)

    // Kbcrypt
    implementation(libs.kbcrypt)
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
