package com.elkhami.core.presentation.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Composable
fun WebButton(
    modifier: Modifier = Modifier,
    url: String,
    onActivityNotFound: () -> Unit
) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        addCategory(Intent.CATEGORY_BROWSABLE)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    OutlinedButton(modifier = modifier.fillMaxWidth(), onClick = {
        try {
            context.startActivity(intent)
        } catch (e: Exception) {
            onActivityNotFound()
        }
    }) {
        Text("Open Repository")
    }
}

@Preview
@Composable
fun WebButtonPreview() {
    WebButton(
        url = "",
        onActivityNotFound = {}
    )
}