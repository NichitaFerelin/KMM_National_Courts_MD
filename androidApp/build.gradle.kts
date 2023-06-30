plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.ferelin.instantejustice.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.ferelin.instantejustice.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 2
        versionName = "0.1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeKotlinCompiler.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.jsoup)
    implementation(libs.ktorAndroid)
    implementation(libs.koinCore)
    implementation(libs.koinCompose)
    implementation(libs.koinAndroid)
    implementation(libs.lifecycle)
    implementation(libs.pagination)
    implementation(libs.browser)

    implementation(platform(libs.firebaseBom))
    implementation(libs.firebaseAnalytics)
    implementation(libs.firebaseCrashlytics)

    val composeBom = platform(libs.composeBom)
    implementation(composeBom)
    implementation(libs.bundles.compose)
}