package br.senai.sp.jandira.tcc_pas

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.screens.HomeScreen
import br.senai.sp.jandira.tcc_pas.screens.TelaDescricacaoCampanhas
import br.senai.sp.jandira.tcc_pas.screens.TelaLogin

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            TelaLogin(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
        composable("campanha/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
            id?.let { TelaDescricacaoCampanhas(it) }
        }
    }
}