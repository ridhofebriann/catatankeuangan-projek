// File: build.gradle.kts (Project: catatankeuangan)
// Lokasi: C:\Users\ridho\AndroidStudioProjects\catatankeuangan\build.gradle.kts
// =================================================================================

// Blok 'plugins' ini mendeklarasikan plugin-plugin yang tersedia untuk proyek
// beserta versinya, yang diambil dari Version Catalog (libs).
// 'apply false' berarti plugin hanya didaftarkan, belum diterapkan.
plugins {
    // 1. Mendaftarkan Plugin Aplikasi Android.
    alias(libs.plugins.android.application) apply false

    // 2. [WAJIB] Mendaftarkan Plugin Google Services untuk Firebase.
    id("com.google.gms.google-services") version "4.4.1" apply false
}
