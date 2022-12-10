plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.snowdango.violet.repository"
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
    // domain
    implementation(project(":domain"))

    // room
    implementation(libs.bundles.room)
    kapt(libs.bundles.roomKapt)

    // datetime
    implementation(libs.bundles.datetime)

    // coroutine test
    testImplementation(libs.bundles.coroutinesTest)

    // junit
    testRuntimeOnly(libs.bundles.junit5TestRuntime)
    testImplementation(libs.bundles.junit5Test)

    // android test
    androidTestImplementation(libs.bundles.androidxTest)

    //assertion
    testImplementation(libs.bundles.kotlinTest)

    // spek
    testImplementation(libs.bundles.spekTest)
    testRuntimeOnly(libs.bundles.spekTestRuntime)

    // mockk
    testImplementation(libs.bundles.mockkTest)
}