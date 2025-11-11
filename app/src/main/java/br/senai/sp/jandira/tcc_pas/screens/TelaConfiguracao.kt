package br.senai.sp.jandira.tcc_pas.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.R


@Composable
fun TelaConfiguracoes(navController: NavHostController) {
    var isDarkTheme by remember { mutableStateOf(false) }
    var caixadeDialogo by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = { BarraDeNavegacaoConfig(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // 🔹 HEADER COM FOTO
            HeaderComFoto()

            // 🔹 ÁREA CINZA ARREDONDADA
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(Color(0xFFF2F2F2))
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Card “Configurações” + Botão desconectar
                HeaderSection(
                    isDarkTheme = isDarkTheme,
                    onThemeToggle = { isDarkTheme = !isDarkTheme },
                    onLogoutClick = { caixadeDialogo = true }
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Lista de opções
                SettingsList()
            }
        }
    }

    // 🔹 Diálogo de confirmação
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
                    onClick = { caixadeDialogo = false },
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
fun HeaderComFoto() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        // Imagem do header (coloque o nome certo do drawable)
        Image(
            painter = painterResource(id = R.drawable.header), // ⬅️ sua imagem aqui
            contentDescription = "Imagem de fundo do header",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Texto sobre a imagem
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x66000000)), // leve escurecimento da imagem
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Configurações",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
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
                text = "Minha Conta",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1e3a8a)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Desconectar
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


//
//@Composable
//fun TelaConfiguracoes(navController: NavHostController) {
//    var isDarkTheme by remember { mutableStateOf(false) }
//    var caixadeDialogo by remember { mutableStateOf(false) }
//
//    Scaffold(
//        bottomBar = { BarraDeNavegacaoConfig(navController) }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color(0xFFF9FAFB))
//                .padding(paddingValues)
//        ) {
//            HeaderSection(
//                isDarkTheme = isDarkTheme,
//                onThemeToggle = { isDarkTheme = !isDarkTheme },
//                onLogoutClick = { caixadeDialogo = true }
//            )
//            SettingsList()
//        }
//    }
//
//    // dialogo de confirmação de desconexão
//    if (caixadeDialogo) {
//        AlertDialog(
//            onDismissRequest = { caixadeDialogo = false },
//            title = {
//                Text(
//                    text = "Tem certeza?",
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF1E4A7A)
//                )
//            },
//            confirmButton = {
//                Button(
//                    onClick = {
//                        caixadeDialogo = false
//                    },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color(0xFFDC2626)
//                    )
//                ) {
//                    Text("Sim")
//                }
//            },
//            dismissButton = {
//                OutlinedButton(
//                    onClick = { caixadeDialogo = false },
//                    colors = ButtonDefaults.outlinedButtonColors(
//                        contentColor = Color(0xFF2196F3)
//                    )
//                ) {
//                    Text("Não")
//                }
//            },
//            containerColor = Color.White,
//            shape = RoundedCornerShape(16.dp)
//        )
//    }
//}
//
//@Composable
//fun HeaderSection(isDarkTheme: Boolean, onThemeToggle: () -> Unit, onLogoutClick: () -> Unit) {
//    Box(
//        modifier = Modifier.fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            // Topo com fundo azul e círculos decorativos sobrepostos
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(140.dp)
//                    .background(Color(0xFF1B5283))
//            ) {}
//
//            Spacer(modifier = Modifier.height(70.dp))
//
//            // Card de Configurações
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp)
//                    .shadow(4.dp, RoundedCornerShape(24.dp)),
//                shape = RoundedCornerShape(24.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.White)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 24.dp, horizontal = 20.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = "Configurações",
//                        style = MaterialTheme.typography.titleLarge,
//                        fontWeight = FontWeight.Bold,
//                        color = Color(0xFF1e3a8a)
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    // Botão Desconectar
//                    OutlinedButton(
//                        onClick = onLogoutClick,
//                        modifier = Modifier
//                            .fillMaxWidth(0.7f)
//                            .height(48.dp),
//                        colors = ButtonDefaults.outlinedButtonColors(
//                            containerColor = Color.White,
//                            contentColor = Color(0xFFdc2626)
//                        ),
//                        border = BorderStroke(2.dp, Color(0xFF2563eb)),
//                        shape = RoundedCornerShape(24.dp)
//                    ) {
//                        Text(
//                            text = "Desconectar",
//                            style = MaterialTheme.typography.bodyLarge,
//                            fontWeight = FontWeight.Bold
//                        )
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//        }
//
//        // Card branco com imagem de perfil - sobreposto
//        Box(
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .offset(y = 80.dp)
//                .size(120.dp)
//                .shadow(8.dp, RoundedCornerShape(24.dp))
//                .clip(RoundedCornerShape(24.dp))
//                .background(Color.White),
//            contentAlignment = Alignment.Center
//        ) {
////            Image(
//////                painter = painterResource(id = R.drawable.profile),
////                contentDescription = "Avatar",
////                modifier = Modifier.size(80.dp)
////            )
//        }
//    }
//}
//
//// ==================== SETTINGS LIST ====================
@Composable
fun SettingsList() {
    var isDarkTheme by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Tema
        SettingsItemSimple(
            title = "Tema 🌙",
            subtitle = null,
            onClick = { isDarkTheme = !isDarkTheme }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Idioma
        SettingsItemSimple(
            title = "Idioma",
            subtitle = "Português",
            onClick = { }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Contato
        SettingsItemSimple(
            title = "Contato",
            subtitle = "pas.suporte@gmail.com",
            onClick = { }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Termos de uso
        SettingsItemSimple(
            title = "Termos de uso",
            subtitle = null,
            onClick = { }
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sobre
        SettingsItemSimple(
            title = "Sobre",
            subtitle = null,
            onClick = { }
        )
    }
}
//
// ==================== COMPONENTE: SETTINGS ITEM SIMPLE (SEM ÍCONES) ====================
@Composable
fun SettingsItemSimple(
    title: String,
    subtitle: String?,
    onClick: () -> Unit
) {
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
            onClick = {navController!!.navigate(route = "Home")},
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Início",
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController!!.navigate(route = "mapa")},
            icon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Mapa",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Mapa",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {navController!!.navigate(route = "perfil")},
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            },
            label = {
                Text(text = "Perfil",
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewConfiguracoesScreen() {
    // Controlador de navegação fake para o Preview
    val navController = rememberNavController()

    TelaConfiguracoes(navController = navController)
}
