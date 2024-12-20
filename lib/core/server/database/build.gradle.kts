plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.sqldelight)
}

group = "org.closs.core.database"

dependencies {
    // Kbcrypt
    implementation(libs.kbcrypt)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // Sqldelight
    implementation(libs.sqldelight.coroutines.extensions)
    implementation(libs.sqldelight.async.extensions)

    // Sqlite
    implementation(libs.sqldelight.sqlite.driver)
    implementation(libs.sqlite)

    // Postgres
    // implementation(libs.sqldelight.postgres.driver)
    // implementation(libs.postgresql)
}

sqldelight {
    databases {
        create("ClossSvDb") {
            packageName.set("org.closs.core.database")
            dialect(libs.sqldelight.sqlite.dialect)
            schemaOutputDirectory.set(file("src/main/sqldelight/databases"))
            verifyMigrations.set(true)
            generateAsync.set(true)
        }
        // If server needs Pg database uncomment
//        create("ClossPgDb") {
//            packageName.set("org.closs.core.database")
//            dialect(libs.sqldelight.postgres.dialect)
//            generateAsync.set(true)
//        }
    }
}

tasks.withType<Jar> {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
