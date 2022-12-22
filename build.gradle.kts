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
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.22")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://dl.bintray.com/spekframework/spek-dev")
    }

}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}