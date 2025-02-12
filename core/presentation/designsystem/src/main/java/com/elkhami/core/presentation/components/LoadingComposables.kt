package com.elkhami.core.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.elkhami.core.presentation.designsystem.Padding
import com.elkhami.core.presentation.designsystem.R

@Composable
fun ShowLoading(padding: Padding) {
    CircularProgressIndicator()
    Spacer(modifier = Modifier.padding(padding.tinyPadding))
    Text(
        stringResource(R.string.loading),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyMedium
    )
}