plugins {
    id("com.android.application").version("8.2.0-alpha09").apply(false)
    id("com.android.library").version("8.2.0-alpha09").apply(false)
    kotlin("android").version("1.8.21").apply(false)
    kotlin("multiplatform").version("1.8.21").apply(false)
}

buildscript {
    dependencies {
        classpath(libs.googleServices)
        classpath(libs.googleCrashlytics)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
