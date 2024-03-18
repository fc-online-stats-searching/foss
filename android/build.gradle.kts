// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.0.2" apply false
    id("com.android.library") version "8.0.2" apply false

    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("org.jetbrains.kotlin.jvm") version "1.9.0" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.4.0" apply true
}

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
}