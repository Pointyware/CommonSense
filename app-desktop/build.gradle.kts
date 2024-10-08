plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    jvmToolchain(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.entities)
    implementation(projects.core.interactors)
    implementation(projects.core.local)
    implementation(projects.core.navigation)
    implementation(projects.core.remote)
    implementation(projects.core.ui)
    implementation(projects.core.viewModels)

    implementation(projects.feature.epistemology)
    implementation(projects.feature.ontology)

    implementation(projects.appShared)

    implementation(libs.kotlinx.dateTime)
    implementation(libs.koin.core)

    implementation(compose.desktop.currentOs)
    implementation(compose.ui)
    implementation(compose.preview)
    implementation(compose.material3)
    implementation(compose.components.resources)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "org.pointyware.commonsense.desktop"
    generateResClass = always
}

compose.desktop {
    application {
        mainClass = "org.pointyware.commonsense.desktop.MainKt"
    }
}
