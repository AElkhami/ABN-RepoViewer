plugins {
    alias(libs.plugins.abnrepoviewer.android.feature.ui)
    alias(libs.plugins.abnrepoviewer.jvm.junit5)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.elkhami.repoviewer.presentation"
}

dependencies {
    implementation(projects.repoViewer.domain)
    implementation(projects.core.domain)

    //Coil
    implementation(libs.coil.compose)
    //Paging
    implementation(libs.bundles.paging)
    //Serialization
    implementation(libs.serialization)
}