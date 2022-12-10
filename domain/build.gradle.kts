plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.snowdango.violet.domain"
    compileSdk = 33

    defaultConfig {
        minSdk = 28
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    // room
    implementation(libs.bundles.room)
    kapt(libs.bundles.roomKapt)

    // datetime
    implementation(libs.bundles.datetime)

    // junit5
    testImplementation(libs.bundles.junit5Test)
    testRuntimeOnly(libs.bundles.junit5TestRuntime)


    // android test
    androidTestImplementation(libs.bundles.androidxTest)
}