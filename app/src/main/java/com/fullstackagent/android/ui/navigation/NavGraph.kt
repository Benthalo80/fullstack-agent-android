package com.fullstackagent.android.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.fullstackagent.android.ui.screens.AboutScreen
import com.fullstackagent.android.ui.screens.BattleScreen
import com.fullstackagent.android.ui.screens.HomeScreen
import com.fullstackagent.android.ui.screens.LeaderboardScreen

@Composable
fun NavGraph(repoProvider: () -> com.fullstackagent.android.data.repo.ArenaRepository) {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onOpenLeaderboard = { arena, cat -> nav.navigate("leaderboard/$arena/$cat") },
                onOpenBattle = { nav.navigate("battle/models/website") },
                onOpenAbout = { nav.navigate("about") }
            )
        }
        composable(
            "leaderboard/{arena}/{category}",
            arguments = listOf(navArgument("arena") { type = NavType.StringType }, navArgument("category") { type = NavType.StringType })
        ) { backStack ->
            LeaderboardScreen(
                arena = backStack.arguments?.getString("arena") ?: "models",
                category = backStack.arguments?.getString("category") ?: "website",
                repo = repoProvider(),
                onBack = { nav.popBackStack() }
            )
        }
        composable("battle/{arena}/{category}") { BattleScreen(onBack = { nav.popBackStack() }) }
        composable("about") { AboutScreen(onBack = { nav.popBackStack() }) }
    }
}
