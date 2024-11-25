plugins {
    alias(libs.plugins.android.application)
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
    // Explicit version for androidx.work to ensure compatibility with compileSdk 34
    implementation("androidx.work:work-runtime:2.8.1")
    implementation("androidx.annotation:annotation:1.2.0")
    implementation ("com.google.android.material:material:1.6.1")
    implementation ("androidx.appcompat:appcompat:1.6.1")

    // Using aliases for dependencies defined in libs.versions.toml
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.barcode.scanning)
    implementation(libs.support.annotations)

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}