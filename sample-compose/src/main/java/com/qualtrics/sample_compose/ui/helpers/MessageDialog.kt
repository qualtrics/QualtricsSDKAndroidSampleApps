package com.qualtrics.sample_compose.ui.helpers

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.qualtrics.sample_compose.ui.theme.QualtricsSDKAndroidSampleTheme

@Composable
fun MessageDialog(
    title: String,
    text: String,
    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(title)
        },
        text = {
            Text(text)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("OK")
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun MessageDialogPreview() {
    QualtricsSDKAndroidSampleTheme {
        MessageDialog(
            title = "This is title",
            text = "This is body of the dialog"
        ) {

        }
    }
}