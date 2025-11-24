package br.senai.sp.jandira.tcc_pas.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.R
import br.senai.sp.jandira.tcc_pas.viewmodel.UserViewModel
import coil.compose.AsyncImage

@Composable
fun TelaConfiguracoes(navController: NavHostController, userViewModel: UserViewModel) {
    var isDarkTheme by remember { mutableStateOf(false) }
    var caixadeDialogo by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BarraDeNavegacaoConfig(navController) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEAEAEA))
            ) {
                HeaderComFoto(userViewModel)

                Spacer(modifier = Modifier.height(40.dp))

                HeaderSection(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { isDarkTheme = !isDarkTheme },
                    onLogoutClick = { caixadeDialogo = true }
                )

                Spacer(modifier = Modifier.height(16.dp))

                SettingsList(navController)
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .offset(y = 98.dp)
                    .size(130.dp)
                    .zIndex(10f)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = Color.Black.copy(alpha = 0.8f),
                        spotColor = Color.Black.copy(alpha = 0.7f)
                    )
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    AsyncImage(
                        model = userViewModel.userData?.foto_perfil ?: "Carregando...",
                        contentDescription = "Foto do usuário",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }

    // 🔹 DIALOG
    if (caixadeDialogo) {
        AlertDialog(
            onDismissRequest = { caixadeDialogo = false },
            title = {
                Text(
                    text = "Tem certeza?",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E4A7A)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        caixadeDialogo = false
                        userViewModel.clearUser()
                        navController.navigate("login")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
                ) {
                    Text("Sim")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { caixadeDialogo = false },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF2196F3))
                ) {
                    Text("Não")
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }
}


@Composable
fun HeaderComFoto(userViewModel: UserViewModel) {
    val user = userViewModel.userData

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFF1B5283))
    )
}

@Composable
fun HeaderSection(isDarkTheme: Boolean, onThemeToggle: () -> Unit, onLogoutClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(4.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Configurações",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1e3a8a)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(48.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFFdc2626)
                ),
                border = BorderStroke(2.dp, Color(0xFF2563eb)),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Desconectar",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun SettingsList(navController: NavController) {
    var isDarkTheme by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()

            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {

        SettingsItemSimple("Contato", "pas.suporte@gmail.com") {}
        Spacer(modifier = Modifier.height(24.dp))

        // 👉 Navegar para Termos
        SettingsItemSimple("Termos de uso", null) {
            navController.navigate("termos")
        }
        Spacer(modifier = Modifier.height(24.dp))

        // 👉 Navegar para Sobre
        SettingsItemSimple("Sobre", null) {
            navController.navigate("sobre")
        }
    }
}


@Composable
fun SettingsItemSimple(title: String, subtitle: String?, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1e3a8a)
        )

        if (subtitle != null) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                color = Color(0xFF9ca3af)
            )
        }
    }
}

@Composable
fun BarraDeNavegacaoConfig(navController: NavHostController?) {
    NavigationBar(
        containerColor = Color(0xFF298BE6)
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController!!.navigate(route = "Home") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Início", color = MaterialTheme.colorScheme.onPrimary)
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController!!.navigate(route = "mapa") },
            icon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Mapa",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Mapa", color = MaterialTheme.colorScheme.onPrimary)
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController!!.navigate(route = "perfil") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Perfil", color = MaterialTheme.colorScheme.onPrimary)
            }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaConfiguracoesPreview() {
    val navController = rememberNavController()
    val fakeUserViewModel = object : UserViewModel() {
        init {
            userData = br.senai.sp.jandira.tcc_pas.model.LoginResponse(
                nome = "Letícia Martins",
                cpf = "12345678900",
                naturalidade = "São Paulo",
                nascimento = "15042002",
                nome_mae = "Maria Martins",
                email = "leticia@email.com",
                cep = "06600-000",
                telefone = "11999999999",
                foto_perfil = null
            )
        }
    }

    MaterialTheme {
        TelaConfiguracoes(navController = navController, userViewModel = fakeUserViewModel)
    }
}
