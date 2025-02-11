package com.elkhami.repoviewer.presentation.repodetails


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.elkhami.core.presentation.components.LabeledText
import com.elkhami.core.presentation.components.UrlImage
import com.elkhami.core.presentation.components.WebButton
import com.elkhami.core.presentation.designsystem.AbnRepoViewerTheme
import com.elkhami.core.presentation.designsystem.LocalDimensions
import com.elkhami.core.presentation.designsystem.LocalPadding
import com.elkhami.core.presentation.designsystem.Padding
import com.elkhami.repoviewer.presentation.R
import com.elkhami.repoviewer.presentation.model.GitRepoUiModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Destination
@Composable
fun RepoDetailsScreenRoot(
    gitRepo: GitRepoUiModel,
    navigator: DestinationsNavigator
) {
    RepoDetailsScreen(
        repoModel = gitRepo,
        onBackClick = {
            navigator.popBackStack()
        }
    )
}

@Composable
private fun RepoDetailsScreen(
    modifier: Modifier = Modifier,
    repoModel: GitRepoUiModel,
    onBackClick: () -> Unit
) {
    val padding = LocalPadding.current
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopBarComposable(
                padding = padding,
                name = repoModel.name.orEmpty(),
                onBackClick = onBackClick
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            DetailsComposable(
                padding = padding,
                imageUrl = repoModel.ownerAvatarUrl.orEmpty(),
                fullName = repoModel.fullName.orEmpty(),
                description = repoModel.description.orEmpty()
            )
            Spacer(modifier = Modifier.weight(1f))
            BottomSectionComposable(
                padding = padding,
                isPrivate = repoModel.isPrivate.toString(),
                visibility = repoModel.visibility.orEmpty(),
                htmlUrl = repoModel.htmlUrl,
                snackbarHostState = snackbarHostState
            )
        }
    }
}

@Composable
private fun TopBarComposable(
    modifier: Modifier = Modifier,
    padding: Padding,
    name: String,
    onBackClick: () -> Unit
) {
    val dimensions = LocalDimensions.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(dimensions.topBarHeight)
            .padding(horizontal = padding.mediumPadding)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .clickable {
                    onBackClick()
                }
        )
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.align(Alignment.Center).fillMaxWidth().padding(horizontal = padding.largePadding),
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun DetailsComposable(
    modifier: Modifier = Modifier,
    padding: Padding,
    fullName: String,
    description: String,
    imageUrl: String
) {
    val dimensions = LocalDimensions.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding.mediumPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UrlImage(
            imageUrl = imageUrl,
            imageSize = dimensions.imageSizeLarge
        )
        Spacer(modifier = Modifier.padding(padding.smallPadding))
        Text(
            text = fullName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(padding.smallPadding))
        Text(
            text = description,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun BottomSectionComposable(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    padding: Padding,
    isPrivate: String,
    visibility: String,
    htmlUrl: String?
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(padding.mediumPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = padding.smallPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = visibility,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
            LabeledText(label = stringResource(R.string.item_private), value = isPrivate)
        }

        val noBrowserMessage = stringResource(R.string.no_browser)

        htmlUrl?.let {
            WebButton(
                url = htmlUrl,
                onActivityNotFound = {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(noBrowserMessage)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.padding(padding.mediumPadding))
    }
}

@Preview(showBackground = true)
@Composable
private fun RepoDetailsScreenPreview() {
    AbnRepoViewerTheme {
        RepoDetailsScreen(
            repoModel = GitRepoUiModel(
                name = "A very very very very long Name",
                fullName = "full name",
                description = "desc",
                visibility = "public",
                isPrivate = false
            ),
            onBackClick = {}
        )
    }
}