package com.elkhami.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.elkhami.core.presentation.designsystem.LocalDimensions
import com.elkhami.core.presentation.designsystem.LocalPadding
import com.elkhami.core.presentation.designsystem.R

@Composable
fun RepoItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    name: String,
    isPrivate: String,
    visibility: String
) {
    val dimens = LocalDimensions.current
    val padding = LocalPadding.current

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding.mediumPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .apply {
                        transformations(CircleCropTransformation())
                    }
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(dimens.imageSize),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.padding(padding.smallPadding))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
            ) {
                Text(name, color = Color.Gray)
                Spacer(modifier = Modifier.padding(padding.smallPadding))
                Row {
                    Text(stringResource(R.string.item_private))
                    Text(isPrivate)
                }
            }
            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .align(Alignment.Top)
            ) {
                Text(text = visibility, color = Color.Gray)
            }
        }
        HorizontalDivider(
            Modifier.padding(
                start = padding.mediumPadding,
                end = padding.mediumPadding
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {
    RepoItem(
        name = "terraform-aws-fargate",
        isPrivate = "false",
        visibility = "public",
        imageUrl = ""
    )
}
