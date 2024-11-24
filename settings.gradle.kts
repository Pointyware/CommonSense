enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "Common_Sense"
include(
    ":core", ":core:common", // TODO: migrate common to core
    ":core:data",
    ":core:entities",
    ":core:interactors",
    ":core:local",
    ":core:navigation",
    ":core:remote",
    ":core:ui",
    ":core:view-models"
)

include(
    ":feature",
    ":feature:ontology",
    ":feature:epistemology",
)

include(
    ":api",
    ":app-shared",
    ":app-android",
//    ":app-ios",
    ":app-desktop",
)
