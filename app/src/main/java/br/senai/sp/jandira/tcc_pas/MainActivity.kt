package br.senai.sp.jandira.tcc_pas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.screens.TelaHome
import br.senai.sp.jandira.tcc_pas.screens.TelaLogin
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Tcc_PasTheme {
                var navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        TelaLogin(navController)
                    }
                    composable("home") {
                        TelaHome(navController)
                    }
                }

            }


        }
    }
}
