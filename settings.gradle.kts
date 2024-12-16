rootProject.name = "closs-kmp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":client:acclossApp")
include(":server")
include(":lib")

// core client library
include(":lib:core:client:api")
include(":lib:core:client:database")
include(":lib:core:client:di")
include(":lib:core:client:presentation")
include(":lib:core:client:resources")
include(":lib:core:client:types")

// core server library
include(":lib:core:server:api")
include(":lib:core:server:database")
include(":lib:core:server:types")
include(":lib:core:server:util")

// core shared library
include(":lib:core:shared:di")
include(":lib:core:shared:types")

// auth library

//  auth client
include(":lib:auth:client")

// auth server
include(":lib:auth:server")

// company library

// company server
include(":lib:company:server")

// company client
include(":lib:company:client")

// config library

// config server
include(":lib:config:server")

// config client
include(":lib:config:client")

// user library

// user server
include(":lib:user:server")

// user client
include(":lib:user:client")

// manager library

// manager server
include(":lib:manager:server")

// manager client
include(":lib:manager:client")

// salesman library

// salesman server
include(":lib:salesman:server")

// salesman client
include(":lib:salesman:client")

// product library

// product server
include(":lib:product:server")

// product client
include(":lib:product:client")

// customer library

// customer server
include(":lib:customer:server")

// customer client
include(":lib:customer:client")

// order library

// order server
include(":lib:order:server")

// order client
include(":lib:order:client")

// document library

// document server
include(":lib:document:server")

// document client
include(":lib:document:client")
