plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
}
kotlin {
    /* Targets configuration omitted.
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    android {
        publishLibraryVariants("release", "debug")
    }
    jvm()
    js(IR)

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":resource"))

                //logs
                implementation("io.github.aakira:napier:${Versions.napier}")

                //internal
                implementation("ro.dragossusi:viewmodel:${Versions.viewmodel}")

                //kotlinx
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jsMain by getting {
            dependencies {
            }
        }
    }
}

if (Features.isAndroidEnabled) {
    apply(plugin = "install-android")
}

apply<PublishPlugin>()