package br.senai.sp.jandira.tcc_pas

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.senai.sp.jandira.tcc_pas.model.UnidadeDeSaude
import br.senai.sp.jandira.tcc_pas.screens.HomeCampanha
import br.senai.sp.jandira.tcc_pas.screens.HomeInformacaoUnidade
import br.senai.sp.jandira.tcc_pas.screens.HomeMapa

//import br.senai.sp.jandira.tcc_pas.screens.HomeMapa
import br.senai.sp.jandira.tcc_pas.screens.HomeScreen
import br.senai.sp.jandira.tcc_pas.screens.TelaConfiguracoes
import br.senai.sp.jandira.tcc_pas.screens.TelaDescricacaoCampanhas
import br.senai.sp.jandira.tcc_pas.screens.TelaLogin
import br.senai.sp.jandira.tcc_pas.screens.TelaMapa
import br.senai.sp.jandira.tcc_pas.screens.TelaMapaNavBar
import br.senai.sp.jandira.tcc_pas.screens.TelaPerfil
import br.senai.sp.jandira.tcc_pas.viewmodel.UserViewModel
import br.senai.sp.jandira.tcc_pas.screens.TelaSobre
import br.senai.sp.jandira.tcc_pas.screens.TelaTermosDeUso
import com.google.gson.reflect.TypeToken

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val userViewModel: UserViewModel = viewModel()



    NavHost(navController = navController, startDestination = "perfil") {
        composable("login") { TelaLogin(navController, userViewModel) }
        composable("home") { HomeScreen(navController) }
        composable("mapa") { TelaMapa(navController) }
        composable("mapanav") { TelaMapaNavBar(navController) }
        composable("perfil") { TelaPerfil(navController, userViewModel) }
        composable("config") { TelaConfiguracoes(navController, userViewModel) }
        composable(
            "campanha/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            HomeCampanha(navController, id)
        }
//        composable("mapafiltrado") { HomeMapa (navController) }
        composable("mapafiltrado") {
            val unidadesFiltradas = navController.currentBackStackEntry
                ?.savedStateHandle
                ?.get<List<UnidadeDeSaude>>("unidadesFiltradas") ?: emptyList()

            TelaMapa(navController, unidadesFiltradas)
        }
        composable(
            "unidadePublica/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0
            HomeInformacaoUnidade(navController, id)
        }
        composable("termos") { TelaTermosDeUso(navController) }
        composable("sobre") { TelaSobre(navController) }
    }
}
