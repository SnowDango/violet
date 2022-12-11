plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("de.mannodermaus.android-junit5")
}

android {
    namespace = "com.snowdango.violet.repository"
    compileSdk = libs.versions.sdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minsdk.get().toInt()
        targetSdk = libs.versions.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"

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

    // android junit5
    androidTestImplementation(libs.bundles.androidJunit5)
    androidTestRuntimeOnly(libs.bundles.androidJunit5Runner)

    //assertion
    testImplementation(libs.bundles.kotlinTest)

    // spek
    testImplementation(libs.bundles.spekTest)
    testRuntimeOnly(libs.bundles.spekTestRuntime)

    // mockk
    testImplementation(libs.bundles.mockkTest)
}