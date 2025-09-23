package br.senai.sp.jandira.tcc_pas.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.R
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme
import kotlinx.coroutines.Dispatchers


@Composable
fun HomeScreen(modifier: Modifier = Modifier){

    var navController = rememberNavController()

    Scaffold(
//        topBar = {
//            BarraDeTitulo()
//        },
        bottomBar = {
            BarraDeNavegacao(navController)
        },
//        floatingActionButton = {
//            BotaoFlutuante(navController)
//        },
        content = { paddingValues ->
            Column (
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ){
                NavHost(
                    navController = navController,
                    startDestination = "Home"
                ) {
                    composable(route = "Home"){ TelaHome(paddingValues) }

                }
            }
        }
    )
}

@Composable
fun BarraDeNavegacao(navController: NavHostController?) {
    NavigationBar(
        containerColor = Color(0xFF298BE6)
//        contentColor = MaterialTheme.colorScheme.onPrimary
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
                Text(text = "Home",
                    color = MaterialTheme.colorScheme.onPrimary)
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
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
            onClick = {},
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

@Preview
@Composable
private fun BarraDeNavegacaoPreview(){
    Tcc_PasTheme {
        BarraDeNavegacao(null)
    }
}


@Composable
fun TelaHome(paddingValues: PaddingValues) {

//    //CRIAR UMA INSTANCIA DO RETROFITFACTORY
//    val clienteApi = RetrofitFactory().getClienteService()
//
//    // CRIAR UMA VARIAVEL DE ESTADO PARA ARMAZENAR A LISTA DE CLIENTES DA API
//    var clientes by  remember {
//        mutableStateOf(listOf<Cliente>())
//    }

//    LaunchedEffect(Dispatchers.IO) {
//        clientes = clienteApi.exibirTodos().await()
//    }
//    Column (
//        modifier = Modifier
//            .fillMaxSize()
//    ){
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(45.dp)
//                .background(Color(color = 0xFFDE1313))
//                .padding(horizontal = 12.dp),
//            contentAlignment = Alignment.CenterStart
//
//        ){
//            Row (verticalAlignment = Alignment.CenterVertically){
//                Icon(
//                    imageVector = Icons.Default.Search,
//                    contentDescription = "Pesquisar",
//                    tint = Color.Black
//                )
//                Spacer(modifier = Modifier.width(width = 8.dp))
//                Text(text = "pesquisar", color = Color.Black)
//            }
//        }
//
//        Spacer(modifier = Modifier.height(height = 16.dp))
//
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(70.dp),
//            shape = RoundedCornerShape(12.dp),
//            colors = CardDefaults.cardColors(contentColor = Color.Black)
//
//        ) {
//            Box(contentAlignment = Alignment.CenterStart, modifier = Modifier.fillMaxSize()){
//                Text(
//                    text = "Mapa",
//                    color = Color.Red,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 16.sp,
//                    modifier = Modifier
//                        .padding(start = 16.dp)
//                )
//            }
//
//        }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White) // só pra destacar melhor
//    ) {
//        Spacer(modifier = Modifier.height(20.dp)) // deixa a barra mais pra baixo
//
//        // Barra de pesquisa arredondada
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(40.dp)
//                .padding(horizontal = 10.dp)
//                .background(Color(0xFF298BE6), RoundedCornerShape(40)), // arredondada
//            contentAlignment = Alignment.Center
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Icon(
//                    imageVector = Icons.Default.KeyboardArrowDown, // seta para baixo
//                    contentDescription = "Seta",
//                    tint = Color.White,
//                    modifier = Modifier.padding(start = 12.dp)
//                )
//
//                Text(
//                    text = "Procure por uma unidade",
//                    color = Color.White,
//                    modifier = Modifier.weight(1f),
//                    textAlign = TextAlign.Center
//                )
//
//                Icon(
//                    imageVector = Icons.Default.Search, // ícone de pesquisa
//                    contentDescription = "Pesquisar",
//                    tint = Color.White,
//                    modifier = Modifier.padding(end = 12.dp)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Card maior com "Mapa" centralizado
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(120.dp), // maior
//            shape = RoundedCornerShape(16.dp),
//        ) {
//            Box(
//                modifier = Modifier.fillMaxSize()
//                    .background(Color(0xFFD7B4B4)),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "Mapa",
//                    color = Color.Red,
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 20.sp
//                )
//            }
//        }
//
//
//    }


//    LazyColumn (
//            contentPadding = PaddingValues(bottom = 70 .dp)
//        ){
////            items(clientes){cliente ->
////                ClienteCard(cliente, clienteApi)
////            }
//        }
//    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Fundo (mapa simulado em rosa claro)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(326.dp) // altura do "mapa"
                .background(Color(0xFFFFC0CB)) // rosa claro
        )

        // Barra de pesquisa arredondada (sobre o mapa)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 10.dp, vertical = 12.dp)
                .align(Alignment.TopCenter)
                .zIndex(2f) // garante que fica acima de tudo
                .background(Color(0xFF298BE6), RoundedCornerShape(40)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Seta",
                    tint = Color.White,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    text = "Procure por uma unidade",
                    color = Color.White,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Pesquisar",
                    tint = Color.White,
                    modifier = Modifier.padding(end = 12.dp)
                )
            }
        }

        // Card vermelho principal que sobrepõe o mapa
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f) // ocupa metade da tela
                .align(Alignment.BottomCenter)
                .zIndex(1f),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Primeiro card de informação
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFB4D7F2))
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "Encontre a unidade de saúde mais próxima:\nclique no mapa para mais informações",
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Informações",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White, // contraste com fundo vermelho
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Segundo card de conteúdo (exemplo de campanha)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFFFA726)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Campanha de Vacinação Contra a Influenza 2025",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeScreenPreview(){
    Tcc_PasTheme {
        HomeScreen( )
    }
}