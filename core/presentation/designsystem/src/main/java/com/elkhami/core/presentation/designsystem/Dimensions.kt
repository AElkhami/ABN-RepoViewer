package com.elkhami.core.presentation.designsystem

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val imageSize: Dp = 50.dp,
    val imageSizeLarge: Dp = 120.dp,
    val dividerThickness: Dp = 1.dp,
    val topBarHeight: Dp = 56.dp
)

val LocalDimensions = compositionLocalOf { Dimensions() }