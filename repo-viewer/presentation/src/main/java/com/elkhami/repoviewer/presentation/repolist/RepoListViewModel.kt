package com.elkhami.repoviewer.presentation.repolist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elkhami.core.presentation.ui.asUiText
import com.elkhami.domain.util.Result
import com.elkhami.repoviewer.domain.RepoListRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RepoListViewModel(
    private val repository: RepoListRepository
) : ViewModel() {

    var state by mutableStateOf(RepoListState())
        private set

    private val eventChannel = Channel<RepoListEvent>()
    val events = eventChannel.receiveAsFlow()

    fun getRepoList(page: Int) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            val result = repository.getGitRepos(page)

            state = state.copy(isLoading = false)

            when (result) {
                is Result.Error -> eventChannel.send(RepoListEvent.Error(result.error.asUiText()))
                is Result.Success -> state = state.copy(repoList = result.data)
            }
        }
    }
}
