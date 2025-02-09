package com.elkhami.abnrepoviewer

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.elkhami.repoviewer.presentation.repolist.RepoListScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "repoViewer"
    ) {
        repoListGraph(navController)
    }
}


private fun NavGraphBuilder.repoListGraph(navController: NavHostController){
    navigation(
        startDestination = "repoList",
        route = "repoViewer"
    ){
        composable(route = "repoList"){
            RepoListScreenRoot (onRepoClick = {
                //to-do handle on repo click
            })
        }
    }
}