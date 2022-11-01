buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
    }
}

allprojects {
    repositories{
        google()
        mavenCentral()
    }
}

task("clean" ,Delete::class){
    delete = setOf(rootProject.buildDir)
}