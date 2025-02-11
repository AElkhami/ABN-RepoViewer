package com.elkhami.core.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
    val dimensions = LocalDimensions.current
    val padding = LocalPadding.current

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding.mediumPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            UrlImage(
                imageUrl = imageUrl,
                imageSize = dimensions.imageSize
            )
            Spacer(modifier = Modifier.padding(padding.smallPadding))

            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = name,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = visibility,
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = padding.smallPadding),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(
                    modifier = Modifier.padding(
                        horizontal = padding.smallPadding,
                        vertical = padding.verySmallPadding
                    )
                )

                LabeledText(label = stringResource(R.string.item_private), value = isPrivate)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {
    RepoItem(
        name = "terraform-aws-fargate, that's is very very very long text",
        isPrivate = "false",
        visibility = "public",
        imageUrl = ""
    )
}
