package com.elkhami.repoviewer.presentation.repolist

import com.elkhami.core.presentation.ui.UiText

sealed interface RepoListEvent {
    data class Error(val error: UiText): RepoListEvent
}