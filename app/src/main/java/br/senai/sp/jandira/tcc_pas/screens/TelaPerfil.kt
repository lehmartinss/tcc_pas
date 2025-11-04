package br.senai.sp.jandira.tcc_pas.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import br.senai.sp.jandira.tcc_pas.viewmodel.UserViewModel

@Composable
fun TelaPerfil(navController: NavHostController, userViewModel: UserViewModel) {



    val user = userViewModel.userData

    Log.e("USERDATA", "$user")

    LaunchedEffect(key1 = user) {

        if(user == null || user.nome == null){

            Log.e("USERDATA", "sem usuario logado")

            navController.navigate(route = "login")
        }

    }


    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // HEADER AZUL
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color(0xFF1B5283))
        ) {
            // Logo "PAS"
            Image(
                painter = painterResource(id = br.senai.sp.jandira.tcc_pas.R.drawable.logo),
                contentDescription = "Logo PAS",
                modifier = Modifier
                    .height(90.dp)
                    .width(100.dp)
                    .padding(start = 20.dp, top = 16.dp)
                    .align(Alignment.TopStart)
            )

            // Texto "Meu Perfil"
            Text(
                text = "Meu Perfil",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(y = (-20).dp)
            )

            // CARD BRANCO COM ÍCONE DE PERFIL
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 60.dp)
                    .size(120.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id =  br.senai.sp.jandira.tcc_pas.R.drawable.profile),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(80.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(70.dp))

        // CARD DADOS PESSOAIS
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Dados pessoais",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E4A7A)
                )

                Spacer(modifier = Modifier.height(16.dp))

                DataItem(
                    icon = Icons.Filled.Person,
                    label = "Nome",
                    value = user?.nome ?: "Carregando..."
                )

                Spacer(modifier = Modifier.height(12.dp))

                DataItem(
                    icon = Icons.Filled.Info,
                    label = "CPF",
                    value = user?.cpf ?: "Carregando..."
                )

                Spacer(modifier = Modifier.height(12.dp))

                DataItem(
                    icon = Icons.Filled.LocationOn,
                    label = "Naturalidade",
                    value = user?.naturalidade ?: "Carregando..."
                )

                Spacer(modifier = Modifier.height(12.dp))

                DataItem(
                    icon = Icons.Filled.Star,
                    label = "Nascimento",
                    value = user?.nascimento ?: "Carregando..."
                )

                Spacer(modifier = Modifier.height(12.dp))

                DataItem(
                    icon = Icons.Filled.AccountCircle,
                    label = "Nome da Mãe",
                    value = user?.nome_mae ?: "Carregando..."
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // CARD DADOS DE CADASTRO
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Dados de cadastro",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E4A7A)
                )

                Spacer(modifier = Modifier.height(16.dp))

                DataItem(
                    icon = Icons.Filled.Email,
                    label = "E-mail",
                    value = user?.email ?: "Carregando..."
                )

                Spacer(modifier = Modifier.height(12.dp))

                DataItem(
                    icon = Icons.Filled.Place,
                    label = "Endereço",
                    value = user?.cep ?: "Carregando..."
                )

                Spacer(modifier = Modifier.height(12.dp))

                DataItem(
                    icon = Icons.Filled.Phone,
                    label = "Telefone",
                    value = user?.telefone ?: "Carregando..."
                )
            }


        }

        BarraDeNavegacaoPerfil(navController)
    }
}

@Composable
fun BarraDeNavegacaoPerfil(navController: NavHostController?) {
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

@Composable
fun DataItem(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF1E4A7A),
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFF888888)
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF1E4A7A)
            )
        }
    }
}
