plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.powerswitch"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.powerswitch"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // 不引入 appcompat，使用系统 Activity
}
