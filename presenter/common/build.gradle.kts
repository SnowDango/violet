plugins {
    id(plugs.plugins.library.get().pluginId)
    id(plugs.plugins.kotlinAndroid.get().pluginId)
}

android {
    namespace = "com.snowdango.violet.presenter.common"
    compileSdk = vers.versions.sdk.get().toInt()
    buildToolsVersion = vers.versions.buildToolsVersion.get()

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
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        create("staging") {
            isMinifyEnabled = true
        }
        debug {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    api(project(":domain"))

    implementation(libs.bundles.androidxDef)

    // constraintlayout
    implementation(libs.bundles.constraintLayout)

    // compose
    implementation(libs.bundles.compose)

    // navigation
    implementation(libs.bundles.navigation)

    // koin
    implementation(libs.bundles.koin)

    // coil
    implementation(libs.bundles.coil)

    // debug
    debugImplementation(libs.bundles.composeDebug)

    // junit5
    testImplementation(libs.bundles.junit5Test)
    testRuntimeOnly(libs.bundles.junit5TestRuntime)

    // android test
    androidTestImplementation(libs.bundles.androidxTest)

    // android junit5
    androidTestImplementation(libs.bundles.androidJunit5)
    androidTestRuntimeOnly(libs.bundles.androidJunit5Runner)
}