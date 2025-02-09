plugins {
    alias(libs.plugins.abnrepoviewer.android.application.compose)
    alias(libs.plugins.abnrepoviewer.jvm.ktor)
}

android {

    namespace = "com.elkhami.abnrepoviewer"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)

    // Timber
    implementation(libs.timber)

    // Koin
    implementation(libs.bundles.koin)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.core.presentation.ui)
    implementation(projects.core.presentation.designsystem)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.repoViewer.presentation)
    implementation(projects.repoViewer.domain)
    implementation(projects.repoViewer.data)
}