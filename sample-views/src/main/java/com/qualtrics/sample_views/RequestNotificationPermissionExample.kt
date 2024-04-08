package com.qualtrics.sample_views

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.qualtrics.sample_common.R
import com.qualtrics.sample_common.helpers.Common

/**
 * Since Android API 33, the app needs to request notification permission at runtime. Qualtrics SDK
 * does not handle this for app developers since you may want to request this permission for other
 * use cases too and you might want to customize the permission prompt.
 *
 * This example comes directly from android documentation. Check the most up to date version:
 * https://developer.android.com/training/permissions/requesting
 *
 * Be sure to add uses-permission POST_NOTIFICATIONS to your manifest
 */
object RequestNotificationPermissionExample {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    /**
     * Needs to be called before activity started event.
     * Be sure to add uses-permission POST_NOTIFICATIONS to your manifest
     */
    fun setupPermissionLauncher(activity: AppCompatActivity) {
        requestPermissionLauncher =
            activity.registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted ->
                val promptText = if (isGranted) {
                    activity.getString(R.string.notification_permission_success)
                } else {
                    activity.getString(R.string.notification_permission_fail)
                }
                promptResult(activity, promptText)
            }
    }

    /**
     * Example how to request notification permission at runtime. See the class description.
     */
    fun requestNotificationsPermission(activity: AppCompatActivity) {
        when {
            Build.VERSION.SDK_INT < 33 -> {
                val title = activity.getString(R.string.api_too_low_title)
                val text = activity.getString(R.string.api_too_low_text)
                promptResult(activity, "$title. $text")
            }

            ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED -> {
                promptResult(activity, activity.getString(R.string.notification_already_granted))
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            ) -> {
                showExplanationAlertDialog(activity) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }

            else -> {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun promptResult(activity: AppCompatActivity, text: String) {
        Log.d(Common.LOG_TAG, text)
        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
    }

    private fun showExplanationAlertDialog(activity: Activity, onAllow: () -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.notification_explanation_title)
        builder.setMessage(R.string.notification_explanation_text)
        builder.setPositiveButton(R.string.notification_explanation_ok) { _, _ ->
            onAllow()
        }
        builder.setNegativeButton(R.string.notification_explanation_cancel) { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }
}
