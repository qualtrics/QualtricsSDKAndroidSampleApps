package com.qualtrics.sample_common.examples

import android.app.Activity
import com.qualtrics.sample_common.CustomQualtricsWrapper

/**
 * Example on how you register visit on your views. Hook this method to your navigation to register
 * screen change. See usages of this method for examples in different architectures.
 */
fun registerViewVisitWithEmbeddedData(
    activity: Activity,
    viewName: String,
    // embedded data can be String, Int or DateTime. We will use String for simplicity
    embeddedData: Map<String, String>,
) {
    /*
    * This registers view visit. View name is using to determine unique views but you can't base
    * Targeting Logic on the name itself. Using the name will require setting it with embedded data.
     */
    CustomQualtricsWrapper.registerViewVisit(viewName)
    /*
    * Add your properties here. An example use case would be passing the product name that was
    * clicked. Then you can configure the Targeting Logic to only display intercepts when the
    * product name contains desired phrase.
    * */
    embeddedData.forEach { (key, value) ->
        CustomQualtricsWrapper.properties.setString(key, value)
    }
}