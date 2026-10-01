// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}

subprojects {
    if (tasks.findByName("prepareKotlinBuildScriptModel") == null) {
        tasks.register("prepareKotlinBuildScriptModel") {
            description = "Placeholder task for IntelliJ / Android Studio Kotlin DSL script model sync"
        }
    }
}