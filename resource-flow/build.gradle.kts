plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
}
kotlin {

    if (Features.isAndroidEnabled) {
        android {
            publishLibraryVariants("release", "debug")
        }
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
        if (Features.isAndroidEnabled) {
            val androidMain by getting {
                dependencies {
                }
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


publishing {
    publications {
        publications.withType<MavenPublication> {
            pom {
                name.set("Resource.kt")
                description.set("Android objects to observe loading completion and error")
                url.set("http://www.example.com/library")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
            }
        }
    }
}

if (Features.isAndroidEnabled) {
    apply(plugin = "install-android")
}

apply<PublishPlugin>()