package org.pointyware.commonsense.buildlogic

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.targets

/**
 * A plugin that sets up the KMP targets convention for the project.
 */
class KmpTargetsConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
//        target.plugins.apply("koin")
        target.plugins.apply("kotlin-multiplatform")

        // Configure Kotlin targets
        target.kotlinExtension.targets.forEach {
            when (it) {
                is KotlinNativeTarget -> {
                    it.binaries.framework {
                        baseName = "app_shared"
                        isStatic = true
                    }
                }
                else -> {
                    it.compilations.all {
                        kotlinOptions {
                            apiVersion = "1.8"
                        }
                    }
                }
            }
        }

        // Add dependencies after evaluation
        target.afterEvaluate {

            val coreProject = project(":core:entities")
            val ontologyProject = project(":feature:ontology")
            val epistemologyProject = project(":feature:epistemology")

            dependencies {
                listOf(
                    coreProject,
                    ontologyProject,
                    epistemologyProject
                ).forEach {
                    add("implementation", it)
                }
            }
        }
    }
}
