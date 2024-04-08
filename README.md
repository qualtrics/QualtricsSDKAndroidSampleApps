# QualtricsSDK Sample Apps
In this repository you will find sample Apps with
[Qualtrics Android SDK](https://api.qualtrics.com/2330b91086280-getting-started-with-mobile-app-sdk-on-android).

## Structure

* Basic kotlin implementation example can be find in [SampleExampleActivity.kt](https://github.com/qualtrics/QualtricsSDKAndroidSampleApps/blob/main/sample-views/src/main/java/com/qualtrics/sample_views/SimpleExampleActivity.kt)
* Advanced examples are divided into technology-specific directories
  * [sample-views](https://github.com/qualtrics/QualtricsSDKAndroidSampleApps/tree/main/sample-views)
folder contains examples for **Android View** approach including navigation between Activities and
Fragments.
  * [sample-compose](https://github.com/qualtrics/QualtricsSDKAndroidSampleApps/tree/main/sample-compose)
folder contains examples for **Jetpack Compose** approach including navigation between screens
using [Navigation Compose](https://developer.android.com/guide/navigation)
  * To avoid duplication, some common code has been extracted to
[sample-common](https://github.com/qualtrics/QualtricsSDKAndroidSampleApps/tree/main/sample-common) directory.

## How to run

* Clone repository and open root directory in Android Studio.
* Change Your Qualtrics Project IDs in file `helpers/Constants.kt`. There is a separate one for each 
of the apps.
* Run one of the prepared configurations: `sample-views` or `sample-compose`

## Setting up the Intercept

We suggest testing the example applications using the Intercept with Display/Targeting Logic set up to
```
If View Count Total Views Greater Than or Equal to 1
```
That way, the intercept will show with every call of `registerViewVisit` making it easy to test.

You can check out the Qualtrics Documentation for more information on how to set up the
[Display Logic](https://www.qualtrics.com/support/website-app-feedback/common-use-cases/mobile-app-feedback-project/?parent=p002049#SettingUptheIntercept)


## Use-cases

The examples included in this repository demonstrate the following use-cases:

* Including the library in your gradle files
* Initializing Project
* Evaluating Project Display Logic
* Evaluating Single Intercept Display Logic
* Building a custom dialog for survey invitation
* Hooking into navigation events to register View Visit with every screen change
* Requesting for Notifications permission for Android 14 and above

## Issues / Support

For help on the Qualtrics SDK, you will want to reach out to our support team via our
[Support Portal](https://www.qualtrics.com/support/). If you do not have a login, please work with
your brand admin to file a support ticket.

**We do not take support requests or community PRs through GitHub.**
