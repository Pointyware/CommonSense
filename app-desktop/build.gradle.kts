plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kover)
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

    kover(projects.core.common)
    kover(projects.core.data)
    kover(projects.core.entities)
    kover(projects.core.interactors)
    kover(projects.core.local)
    kover(projects.core.navigation)
    kover(projects.core.remote)
    kover(projects.core.ui)
    kover(projects.core.viewModels)

    kover(projects.feature.epistemology)
    kover(projects.feature.ontology)

    kover(projects.appShared)

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
