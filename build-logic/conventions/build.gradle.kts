
plugins {
    `kotlin-dsl`
}

group = "org.pointyware.commonsense.buildlogic"

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        create("koin-dependency-injection-convention") {
            id = "org.pointyware.commonsense.koin-dependency-injection-convention"
            version = "0.1.0"
            implementationClass = "org.pointyware.commonsense.buildlogic.KoinDependencyInjectionConventionPlugin"
        }

        create("kmp-convention") {
            id = "org.pointyware.commonsense.kmp-convention"
            version = "0.1.0"
            implementationClass = "org.pointyware.commonsense.buildlogic.KmpTargetsConventionPlugin"
        }

        create("compose-convention") {
            id = "org.pointyware.commonsense.compose-convention"
            version = "0.1.0"
            implementationClass = "org.pointyware.commonsense.buildlogic.ComposeTargetsConventionPlugin"
        }

        create("core-projects-convention") {
            id = "org.pointyware.commonsense.core-projects-convention"
            version = "0.1.0"
            implementationClass = "org.pointyware.commonsense.buildlogic.CoreProjectsConventionPlugin"
        }
    }
}
