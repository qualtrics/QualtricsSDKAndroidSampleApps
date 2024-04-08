@file:OptIn(ExperimentalMaterial3Api::class)

package com.qualtrics.sample_compose.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.qualtrics.sample_compose.ui.theme.QualtricsSDKAndroidSampleTheme

@Composable
fun SecondaryScreen(onUpClick: () -> Unit = {}) {
    Scaffold(
        topBar = {
            SecondaryScreenTopBar(onUpClick = onUpClick)
        },
    ) {padding ->
        Column {
            Text(modifier = Modifier.padding(padding),
                text = "Secondary Screen Content")
        }
    }
}

@Composable
private fun SecondaryScreenTopBar(
    onUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text("Secondary Screen")
        },
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onUpClick) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun SecondaryScreenPreview() {
    QualtricsSDKAndroidSampleTheme {
        SecondaryScreen()
    }
}
