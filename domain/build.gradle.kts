plugins {
    id(plugs.plugins.library.get().pluginId)
    id(plugs.plugins.kotlinAndroid.get().pluginId)
    id(plugs.plugins.kotlinKapt.get().pluginId)
}

android {
    namespace = "com.snowdango.violet.domain"
    compileSdk = vers.versions.sdk.get().toInt()

    defaultConfig {
        minSdk = vers.versions.minsdk.get().toInt()
        targetSdk = vers.versions.sdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
}

dependencies {

    // room
    api(libs.bundles.room)
    kapt(libs.bundles.roomKapt)

    implementation(libs.bundles.moshi)

    // datetime
    api(libs.bundles.datetime)

    // junit5
    testImplementation(libs.bundles.junit5Test)
    testRuntimeOnly(libs.bundles.junit5TestRuntime)

    // android test
    androidTestImplementation(libs.bundles.androidxTest)
}