package com.qualtrics.sample_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.qualtrics.sample_common.CustomQualtricsWrapper
import com.qualtrics.sample_common.QualtricsProjectInfo
import com.qualtrics.sample_common.examples.evaluateInterceptAndDisplay
import com.qualtrics.sample_common.examples.registerViewVisitWithEmbeddedData
import com.qualtrics.sample_compose.helpers.Constants
import com.qualtrics.sample_compose.ui.SampleApp
import com.qualtrics.sample_compose.ui.theme.QualtricsSDKAndroidSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CustomQualtricsWrapper.application = application
        CustomQualtricsWrapper.projectInfo =
            QualtricsProjectInfo(
                projectID = Constants.QUALTRICS_PROJECT_ID,
                brandID = Constants.QUALTRICS_BRAND_ID,
            )

        setContent {
            QualtricsSDKAndroidSampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SampleApp(
                        onPageChange = { pageName ->
                            Log.d(Constants.LOG_TAG, "View changed to $pageName")
                            registerViewVisitWithEmbeddedData(
                                activity = this@MainActivity,
                                viewName = pageName,
                                embeddedData = hashMapOf("productName" to "Great Brand")
                            )
                            evaluateInterceptAndDisplay(
                                this@MainActivity,
                                Constants.QUALTRICS_INTERCEPT_ID,
                            )
                        }
                    )
                }
            }
        }
    }


}

