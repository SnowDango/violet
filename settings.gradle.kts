rootProject.name = "violet"

pluginManagement {
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
