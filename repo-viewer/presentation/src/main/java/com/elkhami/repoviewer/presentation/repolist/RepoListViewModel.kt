package com.elkhami.repoviewer.presentation.repolist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class RepoListViewModel: ViewModel() {
    var state by mutableStateOf(RepoListState())
        private set
}
