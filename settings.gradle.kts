pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FitFlow"
include(":app")
include(":nutritiondatabase")
include(":nutritiondatabase:api")
include(":app:auth")
include(":auth")
include(":common")
include(":nutritionremote")
include(":mylibrary")
include(":nutrition")
include(":supabase")
