plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.pichurchyk.fitflow.common"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // Core AndroidX
    api(libs.androidx.core.ktx)
    api(libs.androidx.appcompat)

    // UI & Compose
    api(libs.androidx.material)
    api(libs.androidx.compose.material3)
    api(libs.androidx.activity.compose)
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.animation)
    api(libs.androidx.navigation.compose) // Shared navigation
    api(libs.coil.compose)
    api(libs.coil.network.ktor2)

    // Networking (Ktor)
    api(libs.ktor.client.core)
    api(libs.ktor.client.auth)
    api(libs.ktor.client.okhttp)
    api(libs.ktor.client.android)
    api(libs.ktor.client.content.negotiation)
    api(libs.ktor.client.logging)
    api(libs.ktor.client.resources)

    // Dependency Injection (Koin)
    api(platform(libs.koin.bom))
    api(libs.koin.core)
    api(libs.koin.compose)

    // Datastore (Shared Preferences)
    api(libs.androidx.datastore.preferences)

    // Testing (Shared across modules)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
