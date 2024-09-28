
plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    `maven-publish`
}

tasks.named<Zip>("distZip") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}
tasks.named<Tar>("distTar") {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

description = "Common Sense API"
version = libs.versions.commonSense.get()

kotlin {
    dependencies {
        implementation(projects.core.entities)

        implementation(libs.koin.ktor)

        implementation(libs.ktor.server.core)
        implementation(libs.ktor.server.netty)

        implementation(libs.kotlinx.coroutines)

        testImplementation(libs.kotlin.test)
        testImplementation(libs.kotlinx.coroutinesTest)
    }
}

application {
    mainClass = "org.pointyware.commonsense.api.ServerKt"
}

ktor {
    fatJar {
        archiveFileName = "Common-Sense-API-${version}.jar"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "org.pointyware.replace-me"
            artifactId = "replace-me-api"
            from(components["java"])
        }
    }
}
