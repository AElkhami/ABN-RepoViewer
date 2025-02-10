package com.elkhami.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

fun DependencyHandlerScope.addUiLayerDependency(project: Project){
    // Core module Ui and Presentation module implementation
    "implementation"(project(":core:presentation:ui"))
    "implementation"(project(":core:presentation:designsystem"))

    // Compose Ui libraries
    "implementation"(project.libs.findBundle("compose").get())
    "implementation"(project.libs.findLibrary("compose-destinations").get())
    "ksp"(project.libs.findLibrary("compose-destinations-ksp").get())
    "debugImplementation"(project.libs.findBundle("compose.debug").get())
    "androidTestImplementation"(project.libs.findLibrary("androidx.compose.ui.test.junit4").get())

    // Koin Libraries
    "implementation"(project.libs.findBundle("koin.compose").get())
}