plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.ai_news_summary"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.ai_news_summary"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Room数据库
    implementation("androidx.room:room-runtime:2.6.1")
    implementation(libs.activity)
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.7.0")

    // Navigation
    implementation("androidx.navigation:navigation-fragment:2.7.5")
    implementation("androidx.navigation:navigation-ui:2.7.5")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    //CardView
    implementation("androidx.cardview:cardview:1.0.0")

    // Glide图片加载
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // 网络请求
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
}