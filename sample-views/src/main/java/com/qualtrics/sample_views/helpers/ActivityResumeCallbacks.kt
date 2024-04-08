package com.qualtrics.sample_views.helpers

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Helper class for clarity of presentation of the example in the MainActivity.
 */
class ActivityResumeCallbacks(
    val callback: (activity: Activity) -> Unit,
) : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        // left empty by design
    }

    override fun onActivityStarted(activity: Activity) = callback(activity)

    override fun onActivityResumed(activity: Activity) {
        // left empty by design
    }

    override fun onActivityPaused(activity: Activity) {
        // left empty by design
    }

    override fun onActivityStopped(activity: Activity) {
        // left empty by design
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        // left empty by design
    }

    override fun onActivityDestroyed(activity: Activity) {
        // left empty by design
    }
}