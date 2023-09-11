plugins {
    id(plugs.plugins.library.get().pluginId)
    id(plugs.plugins.kotlinAndroid.get().pluginId)
}

android {
    namespace = "com.snowdango.violet.presenter.album"
    compileSdk = vers.versions.sdk.get().toInt()
    buildToolsVersion = vers.versions.buildToolsVersion.get()

    defaultConfig {
        minSdk = vers.versions.minsdk.get().toInt()
        targetSdk = vers.versions.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    api(project(":model"))
    implementation(project(":presenter:common"))

    implementation(libs.bundles.androidxDef)

    // constraintlayout
    implementation(libs.bundles.constraintLayout)

    // coil
    implementation(libs.bundles.coil)

    // compose
    implementation(libs.bundles.compose)

    // paging
    implementation(libs.bundles.paging)

    // koin
    implementation(libs.bundles.koin)

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