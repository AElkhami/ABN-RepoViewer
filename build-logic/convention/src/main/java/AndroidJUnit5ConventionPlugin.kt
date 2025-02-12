import com.elkhami.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidJUnit5ConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.apply("abnrepoviewer.jvm.junit5")
            pluginManager.apply("de.mannodermaus.android-junit5")

            dependencies {
                "androidTestImplementation"(libs.findLibrary("junit5.api").get())
                "androidTestImplementation"(libs.findLibrary("junit5.params").get())
                "androidTestRuntimeOnly"(libs.findLibrary("junit5.engine").get())
                "androidTestImplementation"(libs.findLibrary("mockk").get())
                "androidTestImplementation"(libs.findLibrary("mockk-android").get())
                "androidTestImplementation"(libs.findLibrary("assertk").get())
                "androidTestImplementation"(libs.findLibrary("coroutines.test").get())
            }
        }
    }
}