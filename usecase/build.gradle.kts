import de.mannodermaus.gradle.plugins.junit5.junitPlatform

plugins {
    id(plugs.plugins.library.get().pluginId)
    id(plugs.plugins.kotlinAndroid.get().pluginId)
    id(plugs.plugins.kotlinKapt.get().pluginId)
    id(plugs.plugins.androidJunit5.get().pluginId)
}

android {
    namespace = "com.snowdango.usecase"
    compileSdk = 33

    defaultConfig {
        minSdk = 28
        targetSdk = 33

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
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    testOptions {
        junitPlatform {
            filters {
                includeEngines("spek2")
            }
        }
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    // repository
    implementation(project(":repository"))
    // domain
    implementation(project(":domain"))

    // room
    implementation(libs.bundles.room)
    kapt(libs.bundles.roomKapt)

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
}