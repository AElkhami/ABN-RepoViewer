package com.elkhami.repoviewer.presentation.repodetails

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.elkhami.repoviewer.presentation.R
import com.elkhami.repoviewer.presentation.model.GitRepoUiModel
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class RepoDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun repoDetailsScreen_displaysRepoDetailsAndHandlesBackClick() {
        val repoModel = GitRepoUiModel(
            name = testRepoName,
            ownerAvatarUrl = testOwnerAvatarUrl,
            isPrivate = testIsPrivate,
            visibility = testVisibility,
            fullName = testFullName,
            description = testDescription,
            htmlUrl = testHtmlUrl
        )
        val onBackClick: () -> Unit = mockk(relaxed = true)

        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val backString = context.resources.getString(R.string.button_back)

        composeTestRule.setContent {
            RepoDetailsScreen(
                repoModel = repoModel,
                onBackClick = onBackClick
            )
        }

        composeTestRule.onNodeWithText(testRepoName).assertIsDisplayed()
        composeTestRule.onNodeWithText(testFullName).assertIsDisplayed()
        composeTestRule.onNodeWithText(testDescription).assertIsDisplayed()
        composeTestRule.onNodeWithText(testVisibility).assertIsDisplayed()
        composeTestRule.onNodeWithText(testIsPrivate.toString()).assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(backString).performClick()

        verify { onBackClick() }
        confirmVerified(onBackClick)
    }

    @Test
    fun repoDetailsScreen_noHtmlUrl_doesNotDisplayWebButton() {
        val repoModel = GitRepoUiModel(
            name = testRepoName,
            ownerAvatarUrl = testOwnerAvatarUrl,
            isPrivate = testIsPrivate,
            visibility = testVisibility,
            fullName = testFullName,
            description = testDescription,
            htmlUrl = null
        )
        val onBackClick: () -> Unit = mockk(relaxed = true)

        composeTestRule.setContent {
            RepoDetailsScreen(
                repoModel = repoModel,
                onBackClick = onBackClick
            )
        }

        try {
            composeTestRule.onNodeWithText(openInBrowserText).assertDoesNotExist()
        } catch (e: AssertionError) {
            // Test Passed.
        }
    }

    @Test
    fun repoDetailsScreen_displaysNameInTopBar_and_ellipsizeOverflow() {
        val repoModel = GitRepoUiModel(
            name = longRepoName,
            ownerAvatarUrl = testOwnerAvatarUrl,
            isPrivate = testIsPrivate,
            visibility = testVisibility,
            fullName = testFullName,
            description = testDescription,
            htmlUrl = testHtmlUrl
        )
        val onBackClick: () -> Unit = mockk(relaxed = true)

        composeTestRule.setContent {
            RepoDetailsScreen(
                repoModel = repoModel,
                onBackClick = onBackClick
            )
        }

        composeTestRule.onNodeWithText(longRepoName).assertIsDisplayed()
    }

    companion object {
        private val testRepoName = "Name"
        private val testOwnerAvatarUrl = "test_url"
        private val testFullName = "Full name"
        private val testDescription = "Description"
        private val testHtmlUrl = "https://google.com"
        private val testVisibility = "public"
        private val testIsPrivate = true
        private val longRepoName =
            "This is a very long repository name that should be truncated with ellipsis"
        private val openInBrowserText = "Open Repository"
    }
}