plugins {
    kotlin("multiplatform")
    id("com.android.library")
    `maven-publish`
    signing
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

                //messagedata
                api("ro.dragossusi:messagedata:${Versions.messagedata}")
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