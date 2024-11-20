plugins {
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kover)
}

android {
    namespace = "org.pointyware.commonsense.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "org.pointyware.commonsense.android"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
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

    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.composeMaterial3)
    debugImplementation(libs.androidx.composeTooling)
    implementation(libs.androidx.composePreview)
    implementation(compose.components.resources)

    androidTestDebugImplementation(libs.androidx.composeManifest)
    implementation(libs.kotlinx.coroutinesAndroid)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
}
