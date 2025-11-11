package br.senai.sp.jandira.tcc_pas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.screens.HomeCampanha
import br.senai.sp.jandira.tcc_pas.screens.HomeScreen
import br.senai.sp.jandira.tcc_pas.screens.TelaDescricacaoCampanhas
import br.senai.sp.jandira.tcc_pas.screens.TelaLogin
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme
import br.senai.sp.jandira.tcc_pas.viewmodel.UserViewModel
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {

    // importação da viewmodel pra chamar dados do usuário cadastrado
    private val userSessionViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val ctx = applicationContext
        Configuration.getInstance().userAgentValue = "PAS-TCC/1.0 (nicolasalmeidasantos@outlook.com.br)"

        setContent {
            Tcc_PasTheme {
                    AppNavigation()

            }
        }
    }
}
