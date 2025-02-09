plugins {
    alias(libs.plugins.abnrepoviewer.android.feature.ui)
}

android {
    namespace = "com.elkhami.repoviewer.presentation"
}

dependencies {
    implementation(projects.repoViewer.domain)
    implementation(projects.core.domain)
}