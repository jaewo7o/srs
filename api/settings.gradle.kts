rootProject.name = "srs-api"


pluginManagement {
    val kotlinPluginVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinPluginVersion
        kotlin("kapt") version kotlinPluginVersion
        kotlin("plugin.jpa") version kotlinPluginVersion
        kotlin("plugin.spring") version kotlinPluginVersion
    }
}