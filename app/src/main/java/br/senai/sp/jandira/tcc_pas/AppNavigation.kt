package br.senai.sp.jandira.tcc_pas

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.senai.sp.jandira.tcc_pas.screens.HomeCampanha
import br.senai.sp.jandira.tcc_pas.screens.HomeScreen
import br.senai.sp.jandira.tcc_pas.screens.TelaDescricacaoCampanhas
import br.senai.sp.jandira.tcc_pas.screens.TelaLogin

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { TelaLogin(navController) }
        composable("home") { HomeScreen(navController) }
        composable(
            "campanha/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            HomeCampanha(navController, id)
        }
    }
}
