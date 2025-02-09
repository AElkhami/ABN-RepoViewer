plugins {
    alias(libs.plugins.abnrepoviewer.android.feature.ui)
    alias(libs.plugins.abnrepoviewer.jvm.junit5)
}

android {
    namespace = "com.elkhami.repoviewer.presentation"
}

dependencies {
    implementation(projects.repoViewer.domain)
    implementation(projects.core.domain)
}