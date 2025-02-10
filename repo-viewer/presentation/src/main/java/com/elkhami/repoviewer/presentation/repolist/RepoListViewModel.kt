package com.elkhami.repoviewer.presentation.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.elkhami.repoviewer.domain.GitRepoModel

import com.elkhami.repoviewer.presentation.mode.GitRepoUiModel
import kotlinx.coroutines.flow.map


class RepoListViewModel(
    pager: Pager<Int, GitRepoModel>
) : ViewModel() {

    val gitRepoPagingFlow = pager.flow.cachedIn(viewModelScope).map { pagingList ->
        pagingList.map {
            GitRepoUiModel(
                name = it.name,
                fullName = it.fullName,
                ownerAvatarUrl = it.ownerAvatarUrl,
                isPrivate = it.isPrivate,
                visibility = it.visibility,
                description = it.description,
                id = it.id,
                htmlUrl = it.htmlUrl
            )
        }
    }
}
