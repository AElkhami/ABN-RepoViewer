plugins {
    alias(libs.plugins.abnrepoviewer.android.feature.ui)
}

android {
    namespace = "com.elkhami.repoviewer.presentation"
}

dependencies {

    implementation(projects.core.domain)
}