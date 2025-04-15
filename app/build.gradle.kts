plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "uz.urinboydev.oliytaliminfo"
    compileSdk = 35

    defaultConfig {
        applicationId = "uz.urinboydev.oliytaliminfo"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.1"

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
    buildFeatures {
        viewBinding = true
        //noinspection DataBindingWithoutKapt
        dataBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //noinspection UseTomlInstead
    implementation ("com.google.code.gson:gson:2.10.1")
    //noinspection GradleDependency UseTomlInstead
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    //noinspection GradleDependency UseTomlInstead
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    //noinspection GradleDependency UseTomlInstead
    implementation ("androidx.activity:activity-ktx:1.7.1")

}