package org.pointyware.commonsense.buildlogic

import org.gradle.api.DefaultTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class Task: DefaultTask() {
    @Input
    var message: String = "Hello, World!"

    @TaskAction
    fun printMessage() {
        println(message)
    }
}

/**
 * A plugin that sets up the Koin dependency injection framework.
 */
class KoinDependencyInjectionConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {

    }
}
