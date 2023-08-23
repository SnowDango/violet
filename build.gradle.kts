buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath(libs.bundles.gradle)
        classpath(libs.bundles.jacoco)
        classpath(libs.bundles.deployGate)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://dl.bintray.com/spekframework/spek-dev")
    }

}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>()
    .configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}