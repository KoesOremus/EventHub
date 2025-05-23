plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.event_hub"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.event_hub"
        minSdk = 35
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val mapsApiKey: String = project.findProperty("MAPS_API_KEY") as? String ?: ""
        manifestPlaceholders["mapsApiKey"] = mapsApiKey
    }

    buildFeatures {
        buildConfig = true
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    implementation(libs.google.maps)
    implementation(libs.google.location)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}