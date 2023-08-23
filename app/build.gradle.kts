import java.util.*

plugins {
    id(plugs.plugins.application.get().pluginId)
    id(plugs.plugins.kotlinAndroid.get().pluginId)
    id(plugs.plugins.kotlinKapt.get().pluginId)
    id(plugs.plugins.androidJunit5.get().pluginId)
    id(plugs.plugins.jacoco.get().pluginId)
    id(plugs.plugins.deployGate.get().pluginId)
}

android {
    namespace = "com.snowdango.violet"
    compileSdk = vers.versions.sdk.get().toInt()
    buildToolsVersion = vers.versions.buildToolsVersion.get()

    val properties = readProperties(file("../local.properties"))

    defaultConfig {
        applicationId = "com.snowdango.violet"

        minSdk = vers.versions.minsdk.get().toInt()
        targetSdk = vers.versions.sdk.get().toInt()

        versionCode = vers.versions.versionCode.get().toInt()
        versionName = vers.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"
    }

    signingConfigs {
        create("release") {
            storeFile = file("../release.jks")
            storePassword = properties.getProperty("release.password")
            keyAlias = properties.getProperty("release.alias")
            keyPassword = properties.getProperty("release.password")
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        create("staging") {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }
    viewBinding {
        enable = true
    }
    dataBinding {
        enable = true
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

deploygate {
    val properties = readProperties(file("../local.properties"))
    appOwnerName = properties.getProperty("deploygate.user")
    apiToken = properties.getProperty("deploygate.token")
    deployments {
        create("release") {
            sourceFile = file("build/outputs/apk/release/app-release.apk")
        }
        create("debug") {
            sourceFile = file("build/outputs/apk/debug/app-debug.apk")
        }
        create("staging") {
            sourceFile = file("build/outputs/apk/staging/app-staging.apk")
        }
    }
}

dependencies {
    // module
    api(project(":model"))

    // constraintlayout
    implementation(libs.bundles.constraintLayout)

    // coil
    implementation(libs.bundles.coil)

    // navigation
    implementation(libs.bundles.navigation)

    // default
    implementation(libs.bundles.androidxDef)

    // compose
    implementation(libs.bundles.compose)

    // flow layout
    implementation(libs.bundles.flowLayout)

    // permission dispatcher
    implementation(libs.bundles.permissionDispacher)
    kapt(libs.bundles.permissionDispatcherProcessor)

    // datastore
    implementation(libs.bundles.datastore)

    // workmanager
    implementation(libs.bundles.workmanager)

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

fun readProperties(propertiesFile: File) = Properties().apply {
    propertiesFile.inputStream().use { fis ->
        load(fis)
    }
}