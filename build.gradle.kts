import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.composeMultiplatform).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.kotlinJvm).apply(false)
    alias(libs.plugins.kotlinSerialization).apply(false)
    alias(libs.plugins.sqlDelight).apply(false)

    // apply dokka now
    alias(libs.plugins.dokka)

    alias(libs.plugins.commonsense.koin).apply(false)
    alias(libs.plugins.commonsense.kmp).apply(false)
    alias(libs.plugins.kover)
}

tasks.dokkaHtmlMultiModule {
    moduleName.set("Common Sense")
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")

    tasks.withType<DokkaTask>().configureEach {

        dokkaSourceSets.configureEach {

            sourceLink {
                localDirectory.set(projectDir.resolve("src"))
                remoteUrl.set(URL("https://github.com/Pointyware/CommonSense/tree/main/src"))
                remoteLineSuffix.set("#L")
            }
        }
    }
}

dependencies {
    // setup this module as the merge-module for coverage reports
//    subprojects.forEach {
//        kover(it)
//    }
    kover(project(":app-shared"))
}
