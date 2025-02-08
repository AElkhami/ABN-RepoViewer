plugins {
    `kotlin-dsl`
}

group = "com.elkhami.abnrepoviewer.buildlogic"

dependencies{
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "abnrepoviewer.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose"){
            id = "abnrepoviewer.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
    }
}