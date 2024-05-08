package com.qualtrics.sample_views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.qualtrics.sample_common.CustomQualtricsWrapper
import com.qualtrics.sample_common.QualtricsProjectInfo
import com.qualtrics.sample_common.examples.customSurveyInvitationDialog
import com.qualtrics.sample_common.examples.evaluateInterceptAndDisplay
import com.qualtrics.sample_common.examples.evaluateProjectAndDisplay
import com.qualtrics.sample_common.examples.registerViewVisitWithEmbeddedData
import com.qualtrics.sample_views.databinding.ActivityMainBinding
import com.qualtrics.sample_views.helpers.ActivityResumeCallbacks
import com.qualtrics.sample_views.helpers.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CustomQualtricsWrapper.application = application
        CustomQualtricsWrapper.projectInfo =
            QualtricsProjectInfo(
                projectID = Constants.QUALTRICS_PROJECT_ID,
                brandID = Constants.QUALTRICS_BRAND_ID,
            )

        if (savedInstanceState == null) {
            // This is the first time the activity is created
            setupViewVisitsOnActivityChange()
        }

        RequestNotificationPermissionExample.setupPermissionLauncher(this@MainActivity)
        binding.btnRequestPermission.setOnClickListener {
            RequestNotificationPermissionExample.requestNotificationsPermission(this@MainActivity)
        }
        binding.btnEvalProject.setOnClickListener {
            evaluateProjectAndDisplay(this@MainActivity)
        }
        binding.btnEvalIntercept.setOnClickListener {
            evaluateInterceptAndDisplay(this@MainActivity, Constants.QUALTRICS_INTERCEPT_ID)
        }
        binding.btnCustomDialog.setOnClickListener {
            customSurveyInvitationDialog(this@MainActivity, Constants.QUALTRICS_INTERCEPT_ID)
        }
        binding.btnBottomNav.setOnClickListener {
            startActivity(Intent(this@MainActivity, BottomNavExampleActivity::class.java))
        }
        binding.btnSimpleExample.setOnClickListener {
            startActivity(Intent(this@MainActivity, SimpleExampleActivity::class.java))
        }
    }

    /**
     * This method is not supported on API lower than 29. There are other methods available but we
     * are not going to cover them all in this example.
     */
    private fun setupViewVisitsOnActivityChange() {
        if (Build.VERSION.SDK_INT < 29) {
            Log.d(
                Constants.LOG_TAG,
                "This method for tracking activities not supported on API lower than 29"
            )
            return
        }
        // According to documentation, there is no need to unregister this method
        registerActivityLifecycleCallbacks(ActivityResumeCallbacks { activity ->
            registerViewVisitWithEmbeddedData(
                activity,
                viewName = activity::class.java.simpleName,
                hashMapOf("productName" to "Great Brand"),
            )
            evaluateInterceptAndDisplay(
                this@MainActivity,
                Constants.QUALTRICS_INTERCEPT_ID,
            )
        })
    }
}