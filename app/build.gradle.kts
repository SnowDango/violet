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
            storePassword = System.getenv("Key_Pass")
            keyAlias = System.getenv("Key_Alias")
            keyPassword = System.getenv("Key_Pass")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

deploygate {
    userName = System.getenv("DeployGate_UserName")
    token = System.getenv("DeployGate_Token")
    apks {
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
    implementation(project(":usecase"))

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

    // android junit5
    androidTestImplementation(libs.bundles.androidJunit5)
    androidTestRuntimeOnly(libs.bundles.androidJunit5Runner)
}