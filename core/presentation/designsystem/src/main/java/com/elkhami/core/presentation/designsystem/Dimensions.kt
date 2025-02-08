package com.elkhami.core.presentation.designsystem

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val imageSize: Dp = 50.dp
)

val LocalDimensions = compositionLocalOf { Dimensions() }