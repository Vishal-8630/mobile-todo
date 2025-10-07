plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Serialization Plugin
    kotlin("plugin.serialization") version "2.0.21"

    // Hilt and KAPT
    id("kotlin-kapt") // Apply the Kapt plugin
    id("com.google.dagger.hilt.android") // Apply the Hilt plugin

    // Firebase
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.to_doapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.to_doapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "SENDGRID_API_KEY", "\"${project.properties["SENDGRID_API_KEY"] ?: ""}\"")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.lint)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation and Serialization
    implementation("androidx.navigation:navigation-compose:2.9.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")

    // Hilt and KAPT
    implementation("com.google.dagger:hilt-android:2.56.2")
    kapt("com.google.dagger:hilt-compiler:2.56.2")
    // Optional: for ViewModel support
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:34.1.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    // Icons
    implementation("androidx.compose.material:material-icons-extended")

    // Suspend and Await
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.7.3")

    // Sending mails
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
}