buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath(libs.bundles.gradle)
        classpath(libs.bundles.jacoco)
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