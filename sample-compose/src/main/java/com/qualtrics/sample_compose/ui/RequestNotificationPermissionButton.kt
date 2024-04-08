package com.qualtrics.sample_compose.ui

import android.Manifest
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.qualtrics.sample_common.R
import com.qualtrics.sample_common.helpers.Common
import com.qualtrics.sample_compose.ui.helpers.MessageDialog

/**
 * Since Android API 33, the app needs to request notification permission at runtime. Qualtrics SDK
 * does not handle this for app developers since you may want to request this permission for other
 * use cases too and you might want to customize the permission prompt.
 *
 * This example is using google accompanist experimental permission API. Check the most up to date version:
 * https://google.github.io/accompanist/permissions/
 *
 * Keep in mind that this API is Experimental.
 * Be sure to add uses-permission POST_NOTIFICATIONS to your manifest
 */
@Composable
fun RequestNotificationPermissionButton(content: @Composable RowScope.() -> Unit) {
    if (Build.VERSION.SDK_INT < 33) {
        ApiBelow33Button { content() }
    } else {
        Api33Button { content() }
    }
}

private enum class PermissionGrantedPromptState {
    HIDDEN, GRANTED, DENIED
}

@Composable
@RequiresApi(api = 33)
@OptIn(ExperimentalPermissionsApi::class)
private fun Api33Button(content: @Composable RowScope.() -> Unit) {
    val notificationsPermissionState =
        rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    var openRationaleDialog by remember { mutableStateOf(false) }
    var permissionGrantedPromptState by remember { mutableStateOf(PermissionGrantedPromptState.HIDDEN) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGrantedPromptState = if (isGranted) {
            PermissionGrantedPromptState.GRANTED
        } else {
            PermissionGrantedPromptState.DENIED
        }
    }

    Box {
        Button(onClick = {
            if (!notificationsPermissionState.status.isGranted && notificationsPermissionState.status.shouldShowRationale) {
                openRationaleDialog = true
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }) { content() }
        if (openRationaleDialog) {
            RationaleDialog(onDismissRequest = { openRationaleDialog = false }) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
        if (permissionGrantedPromptState != PermissionGrantedPromptState.HIDDEN) {
            val promptText = when (permissionGrantedPromptState) {
                PermissionGrantedPromptState.GRANTED -> stringResource(id = R.string.notification_permission_success)
                PermissionGrantedPromptState.DENIED -> stringResource(id = R.string.notification_permission_fail)
                else -> ""
            }
            val promptTitle = stringResource(id = R.string.notification_permission_title)
            MessageDialog(title = promptTitle, text = promptText) {
                permissionGrantedPromptState = PermissionGrantedPromptState.HIDDEN
            }
        }
    }
}

@Composable
private fun RationaleDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(stringResource(id = R.string.notification_explanation_title))
        },
        text = {
            Text(stringResource(id = R.string.notification_explanation_text))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(id = R.string.notification_explanation_ok))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(id = R.string.notification_explanation_cancel))
            }
        }
    )
}

@Composable
private fun ApiBelow33Button(content: @Composable RowScope.() -> Unit) {
    var openPromptDialog by remember { mutableStateOf(false) }
    Box {
        Button(onClick = { openPromptDialog = true }) { content() }
        if (openPromptDialog) {
            val promptTitle = stringResource(id = R.string.api_too_low_title)
            val promptText = stringResource(id = R.string.api_too_low_text)
            Log.d(Common.LOG_TAG, "$promptTitle $promptText")

            MessageDialog(title = promptTitle, text = promptText) {
                openPromptDialog = false
            }
        }
    }
}
