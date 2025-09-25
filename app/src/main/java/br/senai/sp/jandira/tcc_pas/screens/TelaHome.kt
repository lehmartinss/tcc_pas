package br.senai.sp.jandira.tcc_pas.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.AppNavigation
import br.senai.sp.jandira.tcc_pas.R
import br.senai.sp.jandira.tcc_pas.model.CampanhaResponse
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryCampanha
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import coil.compose.AsyncImage


@Composable
fun HomeScreen(navController: NavHostController) {

    // Retrofit da API de campanhas
    val apiCampanha = RetrofitFactoryCampanha().getCampanhaService()
    var campanhas by remember { mutableStateOf<List<CampanhaResponse>>(emptyList()) }
    var carregando by remember { mutableStateOf(true) }

    //  CARREGA E LISTA AS CAMPANHAS

    LaunchedEffect(Unit) {
        try {
            val response = withContext(Dispatchers.IO) { apiCampanha.listarCampanhas() }
            if (response.isSuccessful) {
                response.body()?.let { campanhas = it }
            }
        } catch (e: Exception) {
            Log.e("TelaHome", "Erro ao buscar campanhas: ${e.message}")
        } finally {
            carregando = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Fundo do mapa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(326.dp)
                .background(Color(0xFF93979F))
        )

        // Barra superior azul
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 10.dp, vertical = 12.dp)
                .align(Alignment.TopCenter)
                .zIndex(2f)
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
                    text = stringResource(R.string.pesquisa),
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

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter)
                .zIndex(1f),
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFB4D7F2))
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = stringResource(R.string.cardInfo),
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(1.dp))
                            Text(
                                text = stringResource(R.string.cardInfo2),
                                textAlign = TextAlign.Center,
                                color = Color(0xFF1E5FA3),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = stringResource(R.string.informacaoHome),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(40.dp))

                if (carregando) {
                    Text(
                        "Carregando campanhas...",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    val lazyListState = rememberLazyListState()
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        flingBehavior = rememberSnapFlingBehavior(lazyListState),
                        state = lazyListState
                    ) {
                        items(campanhas) { campanha ->
                            Card(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .height(180.dp)
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        navController.navigate("campanha/${campanha.id}")
                                    },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                AsyncImage(
                                    model = campanha.foto,
                                    contentDescription = "Imagem da campanha",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Indicador de bolinhas
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        campanhas.forEachIndexed { index, _ ->
                            val isSelected = lazyListState.firstVisibleItemIndex == index
                            Box(
                                modifier = Modifier
                                    .size(if (isSelected) 12.dp else 8.dp)
                                    .padding(2.dp)
                                    .background(
                                        if (isSelected) Color(0xFF298BE6) else Color.LightGray,
                                        shape = CircleShape
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}




@Composable
fun BarraDeNavegacao(navController: NavHostController?) {
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

@Preview
@Composable
private fun BarraDeNavegacaoPreview(){
    Tcc_PasTheme {
        BarraDeNavegacao(null)
    }
}



@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HomeScreenPreview() {
    Tcc_PasTheme {
        HomeScreen(navController = rememberNavController())    }
}






