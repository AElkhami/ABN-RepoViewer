import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import com.elkhami.convention.addUiLayerDependency

class AndroidFeatureUiConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        target.run{
            pluginManager.run {
                apply("abnrepoviewer.android.library.compose")
            }

            dependencies {
                addUiLayerDependency(target)
            }
        }
    }
}