package com.qualtrics.sample_common.examples

import android.app.Activity
import com.qualtrics.sample_common.CustomQualtricsWrapper
import com.qualtrics.sample_common.helpers.logInterceptFail
import com.qualtrics.sample_common.helpers.logInterceptSuccess

/**
 * You can evaluate a single intercept using this method. This is useful for testing one intercept,
 * but we recommend evaluating the whole project so you don't need to republish your app with every
 * new intercept.
 *
 * @param activity Display method starts `QualtricsPopOverActivity`. We need activity context in
 * order to create an Intent.
 */
fun evaluateInterceptAndDisplay(activity: Activity, interceptId: String) =
    /*
    * `evaluate` checks if the conditions set in targeting conditions are met, but does not increase
    * any counters. Use `registerViewVisit` or `properties` to set embedded data.
    * @see RegisterViewVisitExample for more details
    */
    CustomQualtricsWrapper.evaluateIntercept(interceptId) { targetingResult ->
        if (targetingResult.passed()) {
            logInterceptSuccess(interceptId)
            /*
            * `evaluate` checks the targeting logic and returns if the logic has passed.
            * It does not display the prompt so we have to do it manually. This will crash the app
            * if we didn't add QualtricsPopOverActivity in the manifest
            * @see CustomSurveyInvitationDialogExample to see how you can customize the display
            */
            CustomQualtricsWrapper.displayIntercept(activity, interceptId)
        } else {
            logInterceptFail(interceptId)
        }
    }


