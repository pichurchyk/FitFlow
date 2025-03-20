import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.pichurchyk.fitflow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pichurchyk.fitflow"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val googleWebClientId: String = gradleLocalProperties(rootDir).getProperty("GOOGLE_WEB_CLIENT_ID")

        getByName("debug") {
            buildConfigField("String", "GOOGLE_WEB_CLIENT_ID", "\"$googleWebClientId\"")
        }

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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":nutrition"))
    implementation(project(":auth"))
    implementation(project(":supabase"))
    implementation(project(":profile"))

    // Lifecycle & Core
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Firebase (App-Specific)
    implementation(libs.firebase.crashlytics)

    // Authentication (Google Play Services)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services)
    implementation(libs.google.id)

    // Supabase Auth (App-Specific)
    implementation(platform(libs.supabase.bom))
    implementation(libs.supabase.auth)

    // Debugging & Serialization (App-Specific)
    debugImplementation(libs.kotlinx.serialization.json)
}
