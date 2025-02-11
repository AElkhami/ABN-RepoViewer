plugins {
    `kotlin-dsl`
}

group = "com.elkhami.abnrepoviewer.buildlogic"

dependencies{
    compileOnly(libs.room.gradlePlugin)
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
        register("jvmKtor"){
            id = "abnrepoviewer.jvm.ktor"
            implementationClass = "JvmKtorConventionPlugin"
        }
        register("jvmJunit5") {
            id = "abnrepoviewer.jvm.junit5"
            implementationClass = "JvmJUnit5ConventionPlugin"
        }
        register("androidJunit5") {
            id = "abnrepoviewer.android.junit5"
            implementationClass = "AndroidJUnit5ConventionPlugin"
        }
        register("androidRoom") {
            id = "abnrepoviewer.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}