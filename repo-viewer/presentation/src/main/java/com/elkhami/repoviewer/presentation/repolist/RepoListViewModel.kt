package com.elkhami.repoviewer.presentation.repolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.elkhami.repoviewer.domain.GitRepoModel


class RepoListViewModel(
    pager: Pager<Int, GitRepoModel>
) : ViewModel() {

    val gitRepoPagingFlow = pager.flow.cachedIn(viewModelScope)
}
