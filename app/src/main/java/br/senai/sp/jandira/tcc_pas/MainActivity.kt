package br.senai.sp.jandira.tcc_pas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.screens.AppNavigation
import br.senai.sp.jandira.tcc_pas.screens.HomeCampanha
import br.senai.sp.jandira.tcc_pas.screens.HomeScreen
import br.senai.sp.jandira.tcc_pas.screens.TelaDescricacaoCampanhas
import br.senai.sp.jandira.tcc_pas.screens.TelaLogin
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tcc_PasTheme {

                    AppNavigation()









//                val navController = rememberNavController()
//                NavHost(
//                    navController = navController,
//                    startDestination = "login"
//                ) {
//                    composable("login") {
//                        TelaLogin(navController)
//                    }
//                    composable("home") {
//                        HomeScreen(navController)
//                    }
//                    composable("campanha") {
//                        HomeCampanha(navController)
//                    }
//                }

            }


        }
    }
}
