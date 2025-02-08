package com.elkhami.convention

import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.addUiLayerDependency(project: Project){

    // Compose Ui libraries
    "implementation"(project.libs.findBundle("compose").get())
    "debugImplementation"(project.libs.findBundle("compose.debug").get())
    "androidTestImplementation"(project.libs.findLibrary("androidx.compose.ui.test.junit4").get())
}