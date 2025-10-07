package br.senai.sp.jandira.tcc_pas.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.model.CampanhaResponse
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryCampanha
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeCampanha(navController: NavHostController, id: Int) {
    Scaffold(
        bottomBar = { BarraDeNavegacaoCampanha(navController) }
    ) { paddingValues ->
        TelaDescricacaoCampanhas(paddingValues,id, navController)
    }
}

@Composable
fun BarraDeNavegacaoCampanha(navController: NavHostController?) {
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
private fun BarraDeNavegacaoCampanhaPreview(){
    Tcc_PasTheme {
        BarraDeNavegacaoCampanha(null)
    }
}


@Composable
fun ExpandableSection(
    title: String,
    expanded: Boolean,
    onExpandChange: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable { onExpandChange() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF298BE6)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEAF4FF)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }


            if (expanded) {
                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Spacer(Modifier.height(8.dp))
                content()
            }
        }
    }
}


// FUNCAO PARA PERSONALIZAR TEXTO
@Composable
fun InfoText(label: String, value: String) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color(0xFF0E3367),  fontWeight = FontWeight.Bold)) {
                append("$label: ")
            }
            withStyle(style = SpanStyle(color = Color(0xffF9FAFB ), fontWeight = FontWeight.SemiBold)) {
                append(value)
            }
        },
        fontSize = 14.sp,
        modifier = Modifier.padding(vertical = 2.dp)
    )
}


@Composable
fun TelaDescricacaoCampanhas(paddingValues: PaddingValues, id: Int,  navController: NavHostController) {

    var expandedSection by remember { mutableStateOf<String?>(null) }
    var campanha by remember { mutableStateOf<CampanhaResponse?>(null) }
    var carregando by remember { mutableStateOf(true) }

    val apiCampanha = RetrofitFactoryCampanha().getCampanhaService()

    // Buscar dados da campanha
    LaunchedEffect(id) {
        try {
            val response = withContext(Dispatchers.IO) { apiCampanha.getCampanha(id) }
            if (response.isSuccessful) {
                campanha = response.body()
            }
        } finally {
            carregando = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffF9FAFB))
            .padding(top = 18.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(10f)
        ) {
            BarraDePesquisaComFiltros(navController = navController)
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 70.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 80.dp) // garante espaço para o último botão
        ) {
            item {
                Text(
                    text = "Informações",
                    color = Color(0xff1E5FA3),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .padding(top = 5.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {

                    AsyncImage(
                        model = campanha?.foto,
                        contentDescription = "Imagem da campanha",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                campanha?.descricao?.let {
                    Text(
                        text = it,
                        color = Color(0xff094175),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                ExpandableSection(
                    title = "Informações",
                    expanded = expandedSection == "informacoes",
                    onExpandChange = {
                        expandedSection =
                            if (expandedSection == "informacoes") null else "informacoes"
                    },
                    content = {
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            campanha?.publico_alvo?.let { InfoText("Público alvo", it) }
                            campanha?.tipo?.let { InfoText("Tipo", it) }
                            campanha?.tipo_unidade_disponivel?.let {
                                InfoText(
                                    "Tipo unidade disponível",
                                    it
                                )
                            }
                            campanha?.dias_horario?.let { InfoText("Dias/Horário", it) }
                            campanha?.observacoes?.let { InfoText("Observações", it) }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                ExpandableSection(
                    title = "Localização",
                    expanded = expandedSection == "localizacao",
                    onExpandChange = {
                        expandedSection =
                            if (expandedSection == "localizacao") null else "localizacao"
                    },
                    content = {
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            campanha?.cidades?.forEach { cidadeItem ->
                                // Nome da cidade (preto)
                                InfoText("Cidade", cidadeItem.cidade)

                                // Unidades (azul)
                                cidadeItem.unidades_disponiveis.forEach { unidade ->
                                    Text(
                                        text = "- $unidade",
                                        color = Color(0xffF9FAFB),
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(start = 16.dp, bottom = 2.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(6.dp))
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                ExpandableSection(
                    title = "Datas da Campanha",
                    expanded = expandedSection == "datas",
                    onExpandChange = {
                        expandedSection = if (expandedSection == "datas") null else "datas"
                    },
                    content = {
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            campanha?.data_inicio?.let { InfoText("Início", it) }
                            campanha?.data_termino?.let { InfoText("Término", it) }
                        }
                    }
                )
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable private fun HomeCampanhaPreview() {
    Tcc_PasTheme {
        val navController = rememberNavController()
        HomeCampanha(navController = navController, id = 1)
    }
}


