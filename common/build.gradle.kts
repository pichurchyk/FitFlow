plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    api(libs.ktor.client.core)
    api(libs.ktor.client.auth)
    api(libs.ktor.client.okhttp)
    api(libs.ktor.client.android)
    api(libs.ktor.client.content.negotiation)
    api(libs.ktor.client.logging)
    api(libs.ktor.client.resources)

    api(platform(libs.koin.bom))
    api(libs.koin.core)
    api(libs.koin.compose)

    api(libs.androidx.datastore.preferences)
}