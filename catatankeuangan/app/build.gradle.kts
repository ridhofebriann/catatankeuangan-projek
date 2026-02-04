// File: app/build.gradle.kts (Module: :app)
// Lokasi: C:\Users\ridho\AndroidStudioProjects\catatankeuangan\app\build.gradle.kts
// =================================================================================

plugins {
    // 1. Menerapkan Plugin Aplikasi Android yang sudah didaftarkan di level proyek.
    alias(libs.plugins.android.application)

    // 2. [PERBAIKAN UTAMA] Menerapkan Plugin Google Services ke modul aplikasi ini.
    //    Ini akan mengaktifkan inisialisasi Firebase secara otomatis.
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.catatankeuangan"
    // Gunakan versi SDK yang stabil. Saat ini 34 adalah versi stabil terbaru.
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.catatankeuangan"
        // Turunkan minSdk untuk menjangkau lebih banyak perangkat (misal: 24 untuk Android 7.0).
        minSdk = 31
        // Target SDK harus sama dengan compileSdk.
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
    compileOptions {
        // Java 1.8 adalah standar paling umum dan kompatibel untuk Android.
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    // === DEPENDENSI DARI VERSION CATALOG (LIBS) ===
    implementation(libs.appcompat)
    implementation(libs.material) // Library ini sudah mencakup "com.google.android.material"
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // === DEPENDENSI YANG DITAMBAHKAN SECARA MANUAL ===
    // Pastikan tidak ada duplikasi dengan yang sudah ada di libs.
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.gms:play-services-location:21.3.0") // Versi terbaru

    // === DEPENDENSI FIREBASE ===
    // 1. Impor Firebase BoM (Bill of Materials) untuk menyamakan semua versi library Firebase.
    implementation(platform("com.google.firebase:firebase-bom:33.0.0")) // Versi BoM terbaru

    // 2. Tambahkan library Firebase yang Anda butuhkan (tanpa menyebutkan versi).
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-messaging")
    // === DEPENDENSI UNTUK TESTING ===
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
