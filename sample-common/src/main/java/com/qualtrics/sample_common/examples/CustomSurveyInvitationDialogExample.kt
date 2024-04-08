package com.qualtrics.sample_common.examples

import android.app.Activity
import android.app.AlertDialog
import com.qualtrics.sample_common.CustomQualtricsWrapper

/**
 * This is an example how you can customize the survey invitation dialog. This is an alternative to
 * using `displayIntercept` method.
 */
fun customSurveyInvitationDialog(activity: Activity, interceptId: String) =
    /*
    * `evaluate` checks if the conditions set in targeting conditions are met, but does not increase
    * any counters. Use `registerViewVisit` or `properties` to set embedded data.
    * @see RegisterViewVisitExample for more details
    */
    CustomQualtricsWrapper.evaluateIntercept(interceptId) {
        if (it.passed()) {
            /*
            * We need to record that we displayed the dialog manually. This is needed for any logic
            * that depends on remembering if we showed the prompt to the user.
             */
            it.recordImpression()

            val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
            builder.setTitle("Give Feedback")
            builder.setMessage("Would you like to take a brief survey?")
            builder.setPositiveButton("Yes") { dialogInterface, _ ->
                it.recordClick()
                /*
                * This displays the target (usually a survey in a WebView). Please beware that
                * forgetting to add `QualtricsSurveyActivity` in the manifest will most likely
                * result in an App crash.
                */
                CustomQualtricsWrapper.displayTarget(activity, it.surveyUrl)
                dialogInterface.dismiss()
            }
            builder.setNegativeButton("No thanks") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }

            val alert: AlertDialog = builder.create()
            alert.show()
        }
    }