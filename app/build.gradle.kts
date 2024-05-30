import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.gabit"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.gabit"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "KAKAO_API_KEY", gradleLocalProperties(rootDir).getProperty("KAKAO_API_KEY"))
        buildConfigField("String", "SERVER_BASE_URL", gradleLocalProperties(rootDir).getProperty("SERVER_BASE_URL"))
        buildConfigField("String", "POSTMAN_SERVER_BASE_URL", gradleLocalProperties(rootDir).getProperty("POSTMAN_SERVER_BASE_URL"))
        addManifestPlaceholders(mapOf("KAKAO_REDIRECT_URI" to gradleLocalProperties(rootDir).getProperty("KAKAO_REDIRECT_URI")))
    }

    buildFeatures {
        buildConfig = true
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

    buildFeatures {
        dataBinding = true
        viewBinding  = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("junit:junit:4.12")
    testImplementation("junit:junit:4.13.2")

    // AndroidX 테스트 라이브러리 (최신 버전으로 통일)
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:runner:1.4.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.navigation:navigation-fragment:2.3.5")
    implementation("androidx.navigation:navigation-ui:2.3.5")
    implementation("com.kakao.sdk:v2-all:2.20.1")
    implementation("com.squareup.okhttp3:mockwebserver:4.9.3")

    debugImplementation("androidx.fragment:fragment-testing:1.3.6")
}

