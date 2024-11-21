plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
//    kotlin("kapt") version "1.8.0" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
