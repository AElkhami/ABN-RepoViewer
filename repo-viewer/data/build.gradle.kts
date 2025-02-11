plugins {
    alias(libs.plugins.abnrepoviewer.android.library)
    alias(libs.plugins.abnrepoviewer.jvm.ktor)
    alias(libs.plugins.abnrepoviewer.jvm.junit5)
}

android {
    namespace = "com.elkhami.repoviewer.data"
}

dependencies {
    // Koin
    implementation(libs.bundles.koin)
    //Paging
    implementation(libs.bundles.paging)
    //Room
    implementation(libs.bundles.room)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.repoViewer.domain)
    implementation(projects.core.database)
}