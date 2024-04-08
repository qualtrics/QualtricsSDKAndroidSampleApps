package com.qualtrics.sample_compose.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qualtrics.sample_common.examples.customSurveyInvitationDialog
import com.qualtrics.sample_common.examples.evaluateInterceptAndDisplay
import com.qualtrics.sample_common.examples.evaluateProjectAndDisplay
import com.qualtrics.sample_compose.helpers.Constants
import com.qualtrics.sample_compose.helpers.findActivity
import com.qualtrics.sample_compose.ui.theme.QualtricsSDKAndroidSampleTheme

@Composable
fun MainScreen(
    onSecondaryScreenClicked: () -> Unit = {}
) {
    val activity = LocalContext.current.findActivity()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            // Needed for notifications to work in API 33 and above
            RequestNotificationPermissionButton {
                Text(text = "Request Notification Permission")
            }
            Button(onClick = {
                evaluateProjectAndDisplay(activity)
            }) {
                Text(text = "Evaluate Project")
            }
            Button(onClick = {
                evaluateInterceptAndDisplay(activity, Constants.QUALTRICS_INTERCEPT_ID)
            }) {
                Text(text = "Evaluate Intercept")
            }
            Button(onClick = {
                // As of 20th March 2024, Dialog are still experimental API in Jetpack Compose.
                // We will use the existing method directly from the common module.
                customSurveyInvitationDialog(activity, Constants.QUALTRICS_INTERCEPT_ID)
            }) {
                Text(text = "Custom Dialog")
            }
            Button(onClick = {
                // Navigate to secondary screen to test how registerViewVisit works during navigation
                onSecondaryScreenClicked()
            }) {
                Text(text = "Screen Change")
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    QualtricsSDKAndroidSampleTheme {
        MainScreen()
    }
}