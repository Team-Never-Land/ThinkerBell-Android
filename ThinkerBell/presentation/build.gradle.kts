import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    alias(libs.plugins.googleKsp)
    alias(libs.plugins.hilt)
}

val properties = gradleLocalProperties(rootDir, providers)

android {
    namespace = "com.neverland.thinkerbell"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.neverland.thinkerbell"
        minSdk = 29
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        versionName = "0.0.2"
        versionCode = if (project.hasProperty("versionCode")) {
            project.property("versionCode").toString().toInt()
        } else {
            2
        }
    }

    applicationVariants.all {
        outputs.all {
            val outputImpl = this as com.android.build.gradle.internal.api.ApkVariantOutputImpl
            val newFileName = "thinkerbell-${name}.apk"
            outputImpl.outputFileName = newFileName
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = properties["SIGNED_KEY_ALIAS"] as String?
            keyPassword = properties["SIGNED_KEY_PASSWORD"] as String?
            storeFile = properties["SIGNED_STORE_FILE"]?.let { file(it) }
            storePassword = properties["SIGNED_STORE_PASSWORD"] as String?
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
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
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)

    implementation(libs.glide)

    // Fcm
    implementation(libs.firebase.messaging)

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)

    implementation(libs.flow.layout)

    // Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
}