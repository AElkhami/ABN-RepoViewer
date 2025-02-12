plugins {
    alias(libs.plugins.abnrepoviewer.android.feature.ui)
    alias(libs.plugins.abnrepoviewer.jvm.junit5)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.abnrepoviewer.android.junit5)
}

android {
    namespace = "com.elkhami.repoviewer.presentation"
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.database)

    //Coil
    implementation(libs.coil.compose)
    //Paging
    implementation(libs.bundles.paging)
    //Serialization
    implementation(libs.serialization)
}