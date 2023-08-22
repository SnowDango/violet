plugins {
    id(plugs.plugins.library.get().pluginId)
    id(plugs.plugins.kotlinAndroid.get().pluginId)
    id(plugs.plugins.kotlinKapt.get().pluginId)
    id(plugs.plugins.androidJunit5.get().pluginId)
    id(plugs.plugins.jacoco.get().pluginId)
}

android {
    namespace = "com.snowdango.violet.repository"
    compileSdk = vers.versions.sdk.get().toInt()

    defaultConfig {
        minSdk = vers.versions.minsdk.get().toInt()
        targetSdk = vers.versions.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        create("staging") {
            isMinifyEnabled = true
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    // domain
    api(project(":domain"))

    // datastore
    implementation(libs.bundles.datastore)

    // room
    kapt(libs.bundles.roomKapt)

    // timber
    api(libs.bundles.timber)
    // koin
    api(libs.bundles.koin)

    // moshi
    implementation(libs.bundles.moshi)
    // retrofit
    implementation(libs.bundles.retrofit)
    // okhttp
    implementation(libs.bundles.okhttp)

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