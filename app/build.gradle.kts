plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

configurations.all {
    resolutionStrategy {
        force ("androidx.compose.animation:animation:1.4.0")
        exclude(group = "androidx.compose.animation", module = "animation-android")
    }
}

android {
    namespace = "com.lbnkosi.weatherapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lbnkosi.weatherapp"
        minSdk = 29
        targetSdk = 34
        versionCode = 100
        versionName = "1.0.0"

        vectorDrawables {
            useSupportLibrary = true
        }

        testInstrumentationRunner = "com.lbnkosi.weatherapp.Runner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"
            excludes += "META-INF/DEPENDENCIES"
        }
    }
}

dependencies {

    implementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.core.testing)
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.compose.foundation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)


    implementation(libs.androidx.navigation.compose)

    implementation(libs.androidx.compose.foundation)

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.androidx.compose.material3.windowSizeClass)

    implementation(libs.androidx.constraintlayout.compose)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    //Moshi
    implementation(libs.converter.moshi)

    //OkHttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //Stetho - https://github.com/facebookarchive/stetho
    implementation(libs.stetho)
    implementation(libs.stetho.okhttp3)

    //StreamSupport - https://github.com/stefan-zobel/streamsupport
    implementation(libs.streamsupport)

    //Multidex
    implementation(libs.androidx.multidex)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.room.ktx)

    debugImplementation(libs.debug.db)

    //Accompanist
    implementation(libs.accompanist.insets)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.placeholder)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.navigation.animation)

    implementation(libs.androidx.tracing.tracing)
    implementation(libs.androidx.tracing.ktx)

    implementation(libs.lottie.compose)
    implementation(libs.shimmer.compose)

    //Splash
    implementation(libs.androidx.core.splashscreen)

    // Force using only the correct version of the animation library
    implementation(libs.androidx.animation)

    implementation (libs.maps.compose)
    implementation (libs.play.services.maps)
    implementation (libs.maps.utils.ktx)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)

    // JUnit 4 for Unit Testing
    testImplementation (libs.junit)

    // Kotlin Coroutines Test for testing coroutine code
    testImplementation (libs.kotlinx.coroutines.test)

    // MockK for mocking Kotlin classes and objects
    testImplementation (libs.mockk)

    // Truth for assertions
    testImplementation (libs.truth)

    // AndroidX Core Testing for LiveData and ViewModel
    testImplementation (libs.androidx.core.testing)

    // AndroidX Test Core for Android-specific unit testing
    testImplementation (libs.androidx.core)

    // Optional: Mockito (if you prefer Mockito over MockK for mocking)
    testImplementation (libs.mockito.core.v451)
    testImplementation (libs.mockito.kotlin.v400)

    // Optional: Espresso for UI tests (if you plan to add UI instrumentation tests later)
    androidTestImplementation (libs.androidx.espresso.core.v350)
    androidTestImplementation (libs.androidx.junit.v113)

    testImplementation (libs.kotlinx.coroutines.test)

    testImplementation (libs.turbine)
    implementation (libs.play.services.location)
    implementation (libs.maps.compose.v200)
    implementation (libs.play.services.maps.v1800)
    implementation (libs.places)

    implementation (libs.kotlinx.coroutines.play.services)

    testImplementation (libs.mockwebserver)

    testImplementation (libs.dagger.hilt.android.testing)
    kaptTest (libs.hilt.android.compiler)

    // Hilt testing dependencies (for unit and Android tests)
    testImplementation (libs.dagger.hilt.android.testing)
    kaptTest (libs.hilt.compiler)
    androidTestImplementation (libs.dagger.hilt.android.testing)
    kaptAndroidTest (libs.hilt.compiler)

    testImplementation ("org.robolectric:robolectric:4.7.3")
}