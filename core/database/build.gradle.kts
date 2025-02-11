plugins {
    alias(libs.plugins.abnrepoviewer.android.library)
    alias(libs.plugins.abnrepoviewer.android.room)
}

android {
    namespace = "com.elkhami.core.database"

}
dependencies {
    //Koin
    implementation(libs.bundles.koin)

    //Paging
    implementation(libs.bundles.paging)
    implementation(libs.room.paging)
    ksp(libs.room.compiler)

    implementation(projects.core.domain)
}