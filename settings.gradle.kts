rootProject.name = "violet"

pluginManagement {
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
include(":usecase")
include(":repository")
include(":domain")
