package com.qualtrics.sample_common

import android.app.Activity
import android.app.Application
import android.util.Log
import com.qualtrics.digital.IQualtricsCallback
import com.qualtrics.digital.IQualtricsProjectEvaluationCallback
import com.qualtrics.digital.IQualtricsProjectInitializationCallback
import com.qualtrics.digital.Properties
import com.qualtrics.digital.Qualtrics
import com.qualtrics.sample_common.helpers.Common
import com.qualtrics.sample_common.helpers.formatInitializationMessage

/**
 * Object created to simplify usage of Qualtrics class. Methods that evaluate logic should not be
 * called before `initialize` method finishes the request. All `initialize` methods are async
 * methods that are working on callbacks. If you plan to call `evaluate` from a different class
 * then `initialize` this is one of the ways it can be achieved safely.
 *
 * Set application context and projectInfo before calling any of the methods.
 */
object CustomQualtricsWrapper {
    // usually keeping context in static field is a bad practice but we are keeping application
    // context so it lives as long as app itself anyways
    lateinit var application: Application
    lateinit var projectInfo: QualtricsProjectInfo

    private var isProjectInitialized = false
    private val qualtrics by lazy { Qualtrics.instance() }

    // properties holds the same context that was passed during initialize call. In this instance
    // it will be application context so it is ok to keep it static
    val properties: Properties by lazy { qualtrics.properties }

    /**
     * `evaluate` checks if the conditions set in targeting conditions are met, but does not increase
     * any counters. Use `registerViewVisit` or `properties` to set embedded data.
     * @see RegisterViewVisitExample for more details
     */
    fun evaluateProject(callback: IQualtricsProjectEvaluationCallback) =
        ensureProjectIsInitialized {
            qualtrics.evaluateProject(callback)
        }

    /**
     * `evaluate` checks if the conditions set in targeting conditions are met, but does not increase
     * any counters. Use `registerViewVisit` or `properties` to set embedded data.
     * @see RegisterViewVisitExample for more details
     */
    fun evaluateIntercept(interceptId: String, callback: IQualtricsCallback) =
        ensureProjectIsInitialized {
            qualtrics.evaluateIntercept(interceptId, callback)
        }

    fun registerViewVisit(viewName: String) = ensureProjectIsInitialized {
        qualtrics.registerViewVisit(viewName)
    }

    /**
     * `evaluate` checks the targeting logic and returns if the logic has passed.
     * It does not display the prompt so we have to do it manually. Please beware that
     * forgetting to add `QualtricsPopOverActivity` in the manifest will most likely
     * result in an App crash.
     * @see CustomSurveyInvitationDialogExample to see how you can customize the display
     */
    fun displayIntercept(activity: Activity, interceptId: String) = ensureProjectIsInitialized {
        /*
        * displayIntercept takes general context, but in some cases it starts an Intent so we
        * need to pass activity context here
        */
        qualtrics.displayIntercept(activity, interceptId)
    }

    /**
     * This displays the target (usually a survey in a WebView). Please beware that
     * forgetting to add `QualtricsSurveyActivity` in the manifest will most likely
     * result in an App crash.
     */
    fun displayTarget(activity: Activity, targetUrl: String) = ensureProjectIsInitialized {
        /*
            * displayTarget takes general context, but it starts an Intent so we
            * need to pass activity context here
            */
        qualtrics.displayTarget(activity, targetUrl)
    }

    private fun initializeProject(callback: IQualtricsProjectInitializationCallback) =
        qualtrics.initializeProject(
            projectInfo.brandID,
            projectInfo.projectID,
            application,
        ) { results ->
            Log.d(Common.LOG_TAG, formatInitializationMessage(results))
            isProjectInitialized = true
            callback.run(results)
        }

    private fun ensureProjectIsInitialized(run: () -> Unit) {
        ensureFieldsAreSet()
        if (!isProjectInitialized) {
            Log.d(Common.LOG_TAG, "Project not initialized. Initializing…")
            initializeProject { run() }
        } else run()
    }

    private fun ensureFieldsAreSet() {
        if (!::application.isInitialized || !::projectInfo.isInitialized) {
            throw IllegalStateException(
                "QualtricsWrapper is not initialized properly. " +
                        "Set application and projectInfo first before calling any methods."
            )
        }
    }
}