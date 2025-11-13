package br.senai.sp.jandira.tcc_pas.screens

import android.adservices.ondevicepersonalization.UserData
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.R
import br.senai.sp.jandira.tcc_pas.model.LoginResponse
import br.senai.sp.jandira.tcc_pas.screens.DataItem
import br.senai.sp.jandira.tcc_pas.viewmodel.UserViewModel
import coil.compose.AsyncImage


fun formatarCpf(cpf: String?): String {
    if (cpf.isNullOrBlank()) return "Carregando..."
    val apenasNumeros = cpf.filter { it.isDigit() }

    return if (apenasNumeros.length == 11) {
        "${apenasNumeros.substring(0, 3)}.${apenasNumeros.substring(3, 6)}.${apenasNumeros.substring(6, 9)}-${apenasNumeros.substring(9, 11)}"
    } else {
        cpf // Se não tiver 11 dígitos, mostra como veio
    }
}


fun formatarTelefone(telefone: String?): String {
    if (telefone.isNullOrBlank()) return "Carregando..."
    val apenasNumeros = telefone.filter { it.isDigit() }

    return when (apenasNumeros.length) {
        10 -> { // Ex: 1199999999
            "(${apenasNumeros.substring(0, 2)}) ${apenasNumeros.substring(2, 6)}-${apenasNumeros.substring(6)}"
        }
        11 -> { // Ex: 11999999999
            "(${apenasNumeros.substring(0, 2)}) ${apenasNumeros.substring(2, 7)}-${apenasNumeros.substring(7)}"
        }
        else -> telefone // Se não tiver 10 ou 11 dígitos, mostra como veio
    }
}



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

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        bottomBar = { BarraDeNavegacaoPerfil(navController) }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            // HEADER AZUL
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF1B5283))
            ) {


                Column(modifier = Modifier.height(500.dp).width(100.dp).padding(start = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    // Logo "PAS"
                    Image(
                        painter = painterResource(id = br.senai.sp.jandira.tcc_pas.R.drawable.logo),
                        contentDescription = "Logo PAS",
                        modifier = Modifier
                            .height(90.dp)
                            .width(90.dp)
                    )

                    Image(
                        painter = painterResource(id = br.senai.sp.jandira.tcc_pas.R.drawable.settings),
                        contentDescription = "Configurações",
                        modifier = Modifier.height(60.dp).width(40.dp)
                            .clickable{navController.navigate("config")}
                    )
                }


                Text(
                    text = "Meu Perfil",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.Center)

                )
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .offset(y = 60.dp)
                        .size(120.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.White),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    shape = RoundedCornerShape(16.dp)
                ){
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AsyncImage(
                            model = user?.foto_perfil ?: "Carregando...",
                            contentDescription = "Foto da unidade",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(70.dp))

            // card
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
                        drawableId = R.drawable.nome,
                        label = "Nome",
                        value = user?.nome ?: "Carregando..."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DataItem(
                        drawableId = R.drawable.cpf,
                        label = "CPF",
                        value = formatarCpf(user?.cpf)
                    )


                    Spacer(modifier = Modifier.height(12.dp))

                    DataItem(
                        drawableId = R.drawable.nacionalidade,
                        label = "Naturalidade",
                        value = user?.naturalidade ?: "Carregando..."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DataItem(
                        drawableId = R.drawable.nascimento,
                        label = "Data de Nascimento",
                        value = user?.nascimento ?: "Carregando..."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DataItem(
                        drawableId = R.drawable.mae,
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
                        drawableId = R.drawable.email,
                        label = "E-mail",
                        value = user?.email ?: "Carregando..."
                    )

                    Divider(
                        color = Color(0x66B0C4DE),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    DataItem(
                        drawableId = R.drawable.endereco,
                        label = "Endereço",
                        value = user?.cep ?: "Carregando..."
                    )

                    Divider(
                        color = Color(0x66B0C4DE),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )


                    Spacer(modifier = Modifier.height(12.dp))

                    DataItem(
                        drawableId = R.drawable.telefone,
                        label = "Telefone",
                        value = formatarTelefone(user?.telefone)
                    )

                    Divider(
                        color = Color(0x66B0C4DE),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )

                }

            }
            Spacer(modifier = Modifier.height(10.dp))
        }
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
fun DataItem(drawableId: Int, label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = drawableId),
            contentDescription = label,
            modifier = Modifier
                .size(30.dp)
                .padding(end = 8.dp)
        )

        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color(0xFF7FBEF8)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaPerfilPreview() {
    val navController = rememberNavController()

    // Mock simples para o preview
    val userViewModel = object : UserViewModel() {
        init {
            userData = LoginResponse(
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

    TelaPerfil(navController = navController, userViewModel = userViewModel)
}
