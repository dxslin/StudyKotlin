plugins {
    `kotlin-dsl`

}

repositories {
    jcenter()
}

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    }
}

gradlePlugin {
    plugins {
        create("VersionPlugin") {
            id = "com.slin.version.plugin"
            implementationClass = "com.slin.version.plugin.VersionPlugin"
        }
    }
}