package com.elkhami.core.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core.presentation.designsystem.LocalPadding

@Composable
fun LabeledText(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    val padding = LocalPadding.current

    Row(modifier = modifier) {
        Text(text = label,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.padding(padding.tinyPadding))
        Text(
            text = value,
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LabeledTextPreview() {
    LabeledText(
        label = "label",
        value = "value"
    )
}