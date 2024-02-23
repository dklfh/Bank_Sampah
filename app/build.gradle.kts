plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        getByName("debug") {
            storeFile =
                file("C:\\Users\\Lenovo\\AndroidStudioProjects\\Bank_Sampah\\MyKeystore\\keystore.jks")
            storePassword = "SulihYandeGungDevara"
            keyAlias = "key0"
            keyPassword = "SulihYandeGungDevara"
        }
    }
    namespace = "com.example.banksampah"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.banksampah"
        minSdk = 25
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        signingConfig = signingConfigs.getByName("debug")
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
    buildFeatures{
        viewBinding=true
        dataBinding=true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("com.google.android.material:material:1.11.0")
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")
    implementation ("de.hdodenhof:circleimageview:3.0.0")
    implementation ("androidx.drawerlayout:drawerlayout:1.2.0")

//    Firebase
    implementation ("com.google.firebase:firebase-database-ktx:20.3.0")
    implementation ("com.google.firebase:firebase-bom:32.7.2")
    implementation("com.google.firebase:firebase-analytics-ktx:21.5.1")
    implementation ("com.google.android.gms:play-services-auth:21.0.0")
    implementation("com.google.firebase:firebase-auth-ktx:22.3.1")
    implementation ("com.google.firebase:firebase-database:20.3.0")

    //Progress Dialog Library
    implementation ("com.jpardogo.googleprogressbar:library:1.2.0")

    implementation ("androidx.recyclerview:recyclerview:1.3.2")

    //PDF
    implementation ("com.itextpdf:itext7-core:7.1.16")

//    Data local
    implementation ("com.google.code.gson:gson:2.10.1")
}