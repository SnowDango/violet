rootProject.name = "violet"

pluginManagement {
    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version ("0.4.0")
    }
    repositories {
        mavenCentral()
    }
    dependencyResolutionManagement {
        versionCatalogs {
            create("vers") {
                from(files("versions/vers.versions.toml"))
            }
            create("plugs") {
                from(files("versions/plugs.versions.toml"))
            }
            create("libs") {
                from(files("versions/libraries.versions.toml"))
            }
        }
    }
}

include(":app")
include(":repository")
include(":domain")
include(":usecase")
include(":model")
