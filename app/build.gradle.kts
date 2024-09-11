plugins {
    //id("com.android.library")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

// Exclude any undesired versions of animation-android
configurations.all {
    resolutionStrategy {
        // Forces all matching dependencies to use the specified version
        force ("androidx.compose.animation:animation:1.4.0")
        // Exclude animation-android or any other version mismatch
        exclude(group = "androidx.compose.animation", module = "animation-android")
        //exclude (group ("androidx.compose.animation', module: 'animation-android"))
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

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.compose.foundation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    //implementation(libs.androidx.material3)
    //androidx-material3 = { group = "androidx.compose.material3", name = "material3" }
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
    implementation("androidx.compose.animation:animation:1.4.0")

    implementation ("com.google.maps.android:maps-compose:2.11.0")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.maps.android:maps-utils-ktx:3.4.0")

    testImplementation("junit:junit:4.13")
    testImplementation("org.mockito:mockito-core:5.2.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")

    // JUnit 4 for Unit Testing
    testImplementation ("junit:junit:4.13.2")

    // Kotlin Coroutines Test for testing coroutine code
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // MockK for mocking Kotlin classes and objects
    testImplementation ("io.mockk:mockk:1.13.5")

    // Truth for assertions
    testImplementation ("com.google.truth:truth:1.1.5")

    // AndroidX Core Testing for LiveData and ViewModel
    testImplementation ("androidx.arch.core:core-testing:2.1.0")

    // AndroidX Test Core for Android-specific unit testing
    testImplementation ("androidx.test:core:1.4.0")

    // Optional: Mockito (if you prefer Mockito over MockK for mocking)
    testImplementation ("org.mockito:mockito-core:4.5.1")
    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")

    // Optional: Espresso for UI tests (if you plan to add UI instrumentation tests later)
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    testImplementation (libs.turbine)
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.maps.android:maps-compose:2.0.0")
    implementation ("com.google.android.gms:play-services-maps:18.0.0")
    implementation ("com.google.android.libraries.places:places:3.1.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
    
}