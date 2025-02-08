plugins {
    alias(libs.plugins.abnrepoviewer.android.library)
    alias(libs.plugins.abnrepoviewer.jvm.ktor)
}

android {
    namespace = "com.elkhami.core.data"
}

dependencies {
    //Koin
    implementation(libs.bundles.koin)
    //Timber
    implementation(libs.timber)

    implementation(projects.core.domain)
}