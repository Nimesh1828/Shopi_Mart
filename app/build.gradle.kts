plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") version "4.4.2" apply false
}

android {
    namespace = "com.example.qrbarcode"
    compileSdk = 34 // Keeping at 34 for compatibility
    defaultConfig {
        vectorDrawables.useSupportLibrary = true
    }
    defaultConfig {
        applicationId = "com.example.qrbarcode"
        minSdk = 21
        targetSdk = 34 // Matches compileSdk for consistency
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Enable Data Binding and View Binding
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // Work Manager (ensure compatibility with your `compileSdk`)
    implementation("androidx.work:work-runtime:2.9.0")
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:33.8.0"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")


    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries

    // AndroidX Annotations
    implementation("androidx.annotation:annotation:1.9.1")

    // Material Design Components
    implementation("com.google.android.material:material:1.12.0")

    // AppCompat library for backward compatibility
    implementation("androidx.appcompat:appcompat:1.7.0")

    // Google Play Services Vision for barcode scanning
    implementation("com.google.android.gms:play-services-vision:20.1.3")

    // Navigation Components for Fragment and UI
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")

    // ML Kit for barcode scanning
    implementation ("com.google.mlkit:barcode-scanning:17.3.0")

    // CameraX
    val camerax_version = "1.4.0"
    implementation ("androidx.camera:camera-core:${camerax_version}")
    implementation ("androidx.camera:camera-camera2:${camerax_version}")
    implementation ("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation ("androidx.camera:camera-view:${camerax_version}")
    // Using aliases for dependencies defined in libs.versions.toml
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.barcode.scanning)
    implementation(libs.support.annotations)
    implementation(libs.navigation.runtime)
    implementation(libs.navigation.ui)

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}