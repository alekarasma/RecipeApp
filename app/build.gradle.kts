plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.hilt.android)
    id("kotlin-kapt")
}

android {
    namespace = "com.example.recipeapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.recipeapp"
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
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/gradle/incremental.annotation.processors"
        }
    }
}

dependencies {
    implementation(libs.converter.kotlinx.serialization)
    implementation(libs.kotlinx.serialization)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.core)
    implementation(libs.gson)

    implementation(libs.androidx.splashscreen)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.timber)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.serialization)
    implementation(libs.coil)
    implementation(libs.coil.compose)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.testing.androidx.junit)
    androidTestImplementation(libs.testing.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    testImplementation(libs.testing.junit)
    testImplementation(libs.mockk)
    testImplementation (libs.kotlinx.coroutines.test)
    testImplementation (libs.turbine)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
