plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlinx.serialization)
}

group = "org.closs.core.util"

dependencies {
    // Types: core
    implementation(projects.lib.core.shared.types)

    // Types: server
    implementation(projects.lib.core.server.types)

    // Ktor
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.auth.jwt)

    // Koin
    implementation(libs.koin.core)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Kotlin Datetime
    implementation(libs.kotlinx.datetime)

    // Kbcrypt
    implementation(libs.kbcrypt)
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
