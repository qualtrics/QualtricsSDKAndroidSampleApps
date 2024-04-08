package com.qualtrics.sample_common.helpers

import android.util.Log
import com.qualtrics.digital.InitializationResult

fun formatInitializationMessage(results: MutableMap<String, InitializationResult>): String =
    buildString {
        append("Qualtrics SDK initialized successfully! [")
        var counter = 0
        results.forEach { (key, value) ->
            append("$key: ${value.passed()}")
            if (counter != results.size) append(", ")
            counter++
        }
        append("]")
    }

fun logInterceptFail(interceptId: String) {
    Log.d(Common.LOG_TAG, "The intercept evaluation for $interceptId went wrong.")
}

fun logInterceptSuccess(interceptId: String) {
    Log.d(Common.LOG_TAG, "The evaluation for $interceptId passed. Displaying creative.")
}