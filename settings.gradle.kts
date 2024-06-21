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
include(":core:common")
include(":core:data")
include(":core:entities")
include(":core:interactors")
include(":core:local")
include(":core:navigation")
include(":core:remote")
include(":core:ui")
include(":core:view-models")

include(":feature:ontology")
include(":feature:epistemology")

include(":api")
include(":app-shared")
include(":app-android")
//include(":app-ios")
include(":app-desktop")
