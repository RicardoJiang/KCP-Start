plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.zj.debuglog.kotlin-plugin")
}

debugLog {
    enabled = true
    annotations = listOf("com.zj.kcp_start.DebugLog")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.zj.kcp_start"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
  implementation("androidx.appcompat:appcompat:1.2.0")
}
