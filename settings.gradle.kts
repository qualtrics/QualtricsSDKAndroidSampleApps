pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            /* Include Qualtrics S3 bucket to download the SDK from */
            url = uri("https://s3-us-west-2.amazonaws.com/si-mobile-sdks/android/")
        }
    }
}

rootProject.name = "QualtricsSDKAndroidSample"
include(":sample-compose")
include(":sample-views")
include(":sample-common")
