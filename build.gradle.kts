buildscript {
    repositories {
        google() // Ensure this is included
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.2.2") // Use the latest version
        classpath ("com.google.gms:google-services:4.4.0") // Use the latest version
    }
}

