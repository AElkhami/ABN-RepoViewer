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
        register("androidLibrary"){
            id = "abnrepoviewer.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("androidLibraryCompose"){
            id = "abnrepoviewer.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidFeatureUi"){
            id = "abnrepoviewer.android.feature.ui"
            implementationClass = "AndroidFeatureUiConventionPlugin"
        }
        register("jvmLibrary"){
            id = "abnrepoviewer.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}