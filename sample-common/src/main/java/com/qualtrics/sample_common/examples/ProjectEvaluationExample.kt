package com.qualtrics.sample_common.examples

import android.app.Activity
import com.qualtrics.sample_common.CustomQualtricsWrapper
import com.qualtrics.sample_common.helpers.logInterceptFail
import com.qualtrics.sample_common.helpers.logInterceptSuccess

/**
 * This is a basic way to integrate qualtrics into your app. We recommend evaluating the whole
 * project so you don't need to republish your app with every new intercept.
 *
 * @param activity Display method starts `QualtricsPopOverActivity`. We need activity context in
 * order to create an Intent.
 */
fun evaluateProjectAndDisplay(activity: Activity) =
    /*
    * `evaluate` checks if the conditions set in targeting conditions are met, but does not increase
    * any counters. Use `registerViewVisit` or `properties` to set embedded data.
    * @see RegisterViewVisitExample for more details
    */
    CustomQualtricsWrapper.evaluateProject { targetingResults ->
        targetingResults.forEach { (interceptId, result) ->
            if (result.passed()) {
                logInterceptSuccess(interceptId)
                /*
                * `evaluate` checks the targeting logic and returns if the logic has passed.
                * It does not display the prompt so we have to do it manually. Please beware that
                * forgetting to add `QualtricsPopOverActivity` in the manifest will most likely
                * result in an App crash.
                * @see CustomSurveyInvitationDialogExample to see how you can customize the display
                */
                CustomQualtricsWrapper.displayIntercept(activity, interceptId)
            } else {
                logInterceptFail(interceptId)
            }
        }
    }

