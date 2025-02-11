package com.elkhami.core.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.elkhami.core.presentation.components.ComposableConstants.CURVE_RADIUS

@Composable
fun UrlImage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    imageSize: Dp
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .apply {
                transformations(RoundedCornersTransformation(CURVE_RADIUS))
            }
            .build(),
        contentDescription = null,
        modifier = modifier.size(imageSize),
        contentScale = ContentScale.Crop
    )
}