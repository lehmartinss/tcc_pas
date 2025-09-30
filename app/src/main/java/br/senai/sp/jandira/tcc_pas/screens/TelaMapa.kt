package br.senai.sp.jandira.tcc_pas.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.R
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme


@Composable
fun HomeMapa(navController: NavHostController) {
    Scaffold(
        bottomBar = { BarraDeNavegacaoMapa(navController) }
    ) { paddingValues ->
        TelaMapa(paddingValues)
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true)
@Composable
fun HomeMapaPreview() {
    val navController = rememberNavController()
    Tcc_PasTheme {
        HomeMapa(navController)
    }
}

@Composable
fun BarraDeNavegacaoMapa(navController: NavHostController?) {
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
private fun BarraDeNavegacaoMapaPreview(){
    Tcc_PasTheme {
        BarraDeNavegacaoMapa(null)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaMapa(paddingValues: PaddingValues) {
    val estadoSheet = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    // Configurações da tela
    val configuracao = LocalConfiguration.current
    val alturaTela = configuracao.screenHeightDp.dp
    val alturaBarraNavegacao = 56.dp // altura da barra de navegação inferior
    val alturaInicialSheet = alturaTela / 2
    val alturaMaximaSheet = alturaTela - alturaBarraNavegacao

    Box(modifier = Modifier.fillMaxSize()) {
        // Fundo do mapa sempre visível
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF93979F))
        )

        // Barra de pesquisa fixa no topo
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
                    text = "Procure por uma unidade...",
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

        // ModalBottomSheet customizado
        ModalBottomSheet(
            onDismissRequest = { /* opcional: não fecha totalmente */ },
            sheetState = estadoSheet,
            containerColor = Color.White,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    min = alturaInicialSheet,          // inicia acima da barra de navegação
                    max = alturaMaximaSheet           // expande até encostar na barra de navegação
                )
                .padding(bottom = alturaBarraNavegacao) // deixa o sheet encostado na barra de navegação
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                item {
                    CartaoUnidade(
                        nomeUnidade = "Hospital Municipal de Barueri Dr. Francisco Moran",
                        tempoEspera = "5 minutos"
                    )
                }
                item {
                    Spacer(Modifier.height(12.dp))
                    CartaoUnidade(
                        nomeUnidade = "Hospital Nove de Julho",
                        tempoEspera = "20 minutos"
                    )
                }
            }
        }
    }

    // Garante que o sheet abra na metade da tela inicialmente
    LaunchedEffect(estadoSheet) {
        estadoSheet.expand()
    }
}

@Composable
fun CartaoUnidade(nomeUnidade: String, tempoEspera: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEAF2FB), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Text(
            text = nomeUnidade,
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF123B6D)
        )
        Spacer(Modifier.height(8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = "Ícone de localização",
                    tint = Color(0xFF123B6D)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = "Tempo de espera: $tempoEspera",
                    color = Color(0xFF123B6D)
                )
            }
            Button(
                onClick = { /* ação de saber mais */ },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF298BE6))
            ) {
                Text("Saber mais", color = Color.White)
            }
        }
    }
}






//@Composable
//fun TelaMapa(paddingValues: PaddingValues){
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        // Fundo do mapa
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(326.dp)
//                .background(Color(0xFF93979F))
//        )
//
//        // Barra superior azul
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)
//                .padding(horizontal = 10.dp, vertical = 12.dp)
//                .align(Alignment.TopCenter)
//                .zIndex(2f)
//                .background(Color(0xFF298BE6), RoundedCornerShape(40)),
//            contentAlignment = Alignment.Center
//        ) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Icon(
//                    imageVector = Icons.Default.KeyboardArrowDown,
//                    contentDescription = "Seta",
//                    tint = Color.White,
//                    modifier = Modifier.padding(start = 12.dp)
//                )
//                Text(
//                    text = stringResource(R.string.pesquisa),
//                    color = Color.White,
//                    modifier = Modifier.weight(1f),
//                    textAlign = TextAlign.Center
//                )
//                Icon(
//                    imageVector = Icons.Default.Search,
//                    contentDescription = "Pesquisar",
//                    tint = Color.White,
//                    modifier = Modifier.padding(end = 12.dp)
//                )
//            }
//        }
//    }
//}



