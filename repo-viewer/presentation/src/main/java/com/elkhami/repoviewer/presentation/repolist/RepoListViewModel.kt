package com.elkhami.repoviewer.presentation.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import androidx.paging.map
import com.elkhami.core.database.entity.GitRepoEntity
import com.elkhami.repoviewer.presentation.mapper.toGitRepoUiModel
import kotlinx.coroutines.flow.map


class RepoListViewModel(
    pager: Pager<Int, GitRepoEntity>
) : ViewModel() {

    val gitRepoPagingFlow = pager.flow.cachedIn(viewModelScope).map { pagingList ->
        pagingList.map {
            it.toGitRepoUiModel()
        }
    }.cachedIn(viewModelScope)
}
