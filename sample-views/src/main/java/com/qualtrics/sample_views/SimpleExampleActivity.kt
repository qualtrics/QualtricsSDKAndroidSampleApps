package com.qualtrics.sample_views

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.qualtrics.digital.Qualtrics
import com.qualtrics.sample_common.helpers.formatInitializationMessage
import com.qualtrics.sample_common.helpers.logInterceptFail
import com.qualtrics.sample_common.helpers.logInterceptSuccess
import com.qualtrics.sample_views.databinding.ActivitySimpleExampleBinding
import com.qualtrics.sample_views.helpers.Constants

/**
 * This Activity is the example of the most basic setup using Qualtrics SDK. It's the best for
 * testing initial setup or as a base for sending minimal reproduction to the support team.
 * Refer to full examples for our guidance and recommendations.
 */
class SimpleExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimpleExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpleExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Note that Qualtrics is using single instance so if you initialized it before for
        // different IDs, they will already loaded here
        Qualtrics.instance().initializeProject(
            Constants.QUALTRICS_BRAND_ID,
            Constants.QUALTRICS_PROJECT_ID,
            Constants.QUALTRICS_EXT_REF_ID,
            this@SimpleExampleActivity
        ) {
            Log.d(Constants.LOG_TAG, formatInitializationMessage(it))
        }

        RequestNotificationPermissionExample.setupPermissionLauncher(this@SimpleExampleActivity)
        binding.btnRequestPermission.setOnClickListener {
            RequestNotificationPermissionExample.requestNotificationsPermission(this@SimpleExampleActivity)
        }
        // Check the logs. If buttons below are clicked before initialization is complete,
        // you will get an error
        binding.btnEvalProject.setOnClickListener {
            Qualtrics.instance().evaluateProject {
                it.forEach { (interceptId, result) ->
                    if (result.passed()) {
                        logInterceptSuccess(interceptId)
                        Qualtrics.instance().displayIntercept(
                            this@SimpleExampleActivity,
                            interceptId
                        )
                    } else {
                        logInterceptFail(interceptId)
                    }
                }
            }
        }
        binding.btnEvalIntercept.setOnClickListener {
            Qualtrics.instance().evaluateIntercept(Constants.QUALTRICS_INTERCEPT_ID) {
                if (it.passed()) {
                    logInterceptSuccess(it.interceptID)
                    Qualtrics.instance().displayIntercept(
                        this@SimpleExampleActivity,
                        Constants.QUALTRICS_INTERCEPT_ID
                    )
                } else {
                    logInterceptFail(it.interceptID)
                }
            }
        }
    }
}