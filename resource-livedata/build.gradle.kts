plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    `maven-publish`
    signing
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 1
        versionName = Versions.app

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}")
    implementation("androidx.core:core-ktx:1.5.0")
    implementation("androidx.appcompat:appcompat:1.3.0")

    api(project(":resource"))

    api("ro.dragossusi:livedata-common:${Versions.commons}")

    //logs
    implementation("com.jakewharton.timber:timber:4.7.1")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.2")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.3.0")

}


afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            create<MavenPublication>("release") {
                // Applies the component for the release build variant.
                from(components["release"])

                // You can then customize attributes of the publication as shown below.
                groupId = "ro.dragossusi"
                artifactId = "resource-livedata"
                version = Versions.app

                pom {
                    name.set("Resource")
                    description.set("Android objects to observe loading completion and error")
                    url.set("http://www.example.com/library")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("dragossusi")
                            name.set("Dragos Rachieru")
                            email.set("rachierudragos97@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/dragossusi/Resource.git")
                        developerConnection.set("scm:git:ssh://github.com/dragossusi/Resource.git")
                        url.set("https://github.com/dragossusi/Resource/")
                    }
                }
            }
        }
        repositories {
            maven {
                name = "sonatype"
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = project.property("sonatype.username").toString()
                    password = project.property("sonatype.password").toString()
                }
            }
        }
    }

    signing {
        sign(publishing.publications["release"])
    }
}