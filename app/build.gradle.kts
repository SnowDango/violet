plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.snowdango.violet"
    compileSdk = 33
    buildToolsVersion = "33.0.0"

    defaultConfig {
        applicationId = "com.snowdango.violet"
        minSdk = 28
        targetSdk = 33
        versionCode = 1
        versionName = "0.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // module
    implementation(project(":repository"))

    // default
    implementation(libs.bundles.androidxDef)

    // compose
    implementation(libs.bundles.compose)

    // debug
    debugImplementation(libs.bundles.composeDebug)

    // junit5
    testImplementation(libs.bundles.junit5Test)
    testRuntimeOnly(libs.bundles.junit5TestRuntime)

    // android test
    androidTestImplementation(libs.bundles.androidxTest)
}