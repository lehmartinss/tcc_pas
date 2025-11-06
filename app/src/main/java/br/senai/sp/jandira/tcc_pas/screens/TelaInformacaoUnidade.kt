package br.senai.sp.jandira.tcc_pas.screens

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.R
import br.senai.sp.jandira.tcc_pas.model.CampanhaResponse
import br.senai.sp.jandira.tcc_pas.model.UnidadeDeSaude
import br.senai.sp.jandira.tcc_pas.model.UnidadeDeSaudeResponse
import br.senai.sp.jandira.tcc_pas.model.UnidadeResponse
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltrar
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltroUnidade
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme
import coil.compose.AsyncImage
import org.w3c.dom.Text


@Composable
fun HomeInformacaoUnidade(navController: NavHostController, id: Int) {
    Scaffold(
        bottomBar = { BarraDeNavegacaoInfoUnidade(navController) }
    ) { paddingValues ->
        TelaInformacaoUnidade(paddingValues,id, navController)
    }
}

@Composable
fun BarraDeNavegacaoInfoUnidade(navController: NavHostController?) {
    NavigationBar(
        containerColor = Color(0xFF298BE6)
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {navController!!.navigate(route = "home")},
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
private fun BarraDeNavegacaoInfoUnidadePreview(){
    Tcc_PasTheme {
        BarraDeNavegacaoInfoUnidade(null)
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInformacaoUnidade(
    paddingValues: PaddingValues,
    id: Int,
    navController: NavHostController
) {
    var unidade by remember { mutableStateOf<UnidadeDeSaude?>(null) } // Estado da unidade
    val apiUnidade = RetrofitFactoryFiltrar().getUnidadesService()
    var expandir by remember { mutableStateOf(false) }

    LaunchedEffect(id) {
        try {
            val response = apiUnidade.getUnidadePorId(id)
            if (response.isSuccessful) {
                val unidadeResponse = response.body()?.unidadesDeSaude?.firstOrNull()

                if (unidadeResponse != null) {
                    unidade = unidadeResponse
                    Log.d("INFO_UNIDADE", "✅ Unidade carregada: ${unidade?.nome}")
                } else {
                    Log.e("INFO_UNIDADE", "❌ Unidade não encontrada.")
                }
            } else {
                Log.e("INFO_UNIDADE", "❌ Erro ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("INFO_UNIDADE", "🚨 Erro ao buscar unidade: ${e.message}")
        }
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffF9FAFB))
            .padding(paddingValues)
            .navigationBarsPadding() // respeita a barra de navegação
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Card(
                modifier = Modifier
                    .size(width = 350.dp, height = 220.dp)
                    .border(1.5.dp, Color(0xFF1E5FA3), RoundedCornerShape(12.dp)) // 🔹 Borda fina azul
                    .shadow(
                        elevation = 20.dp, // 🔹 Intensidade da sombra (aumente se quiser mais destaque)
                        shape = RoundedCornerShape(12.dp),
                        clip = false, // 🔹 Mantém a sombra para fora da borda
                        ambientColor = Color.Black.copy(alpha = 0.25f),
                        spotColor = Color.Black.copy(alpha = 0.35f)
                    ),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AsyncImage(
                        model = unidade?.foto,
                        contentDescription = "Foto da unidade",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }

        // nome
        item {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = unidade?.nome ?: "-",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1E5FA3),
                textAlign = TextAlign.Start
            )
        }
        // tempo de espera
        item {
            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = "Tempo de espera",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF0B2A46),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = unidade?.tempo_espera_geral ?: "Tempo de espera não disponível ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E5FA3),
                textAlign = TextAlign.Center
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = Color(0xFF7FBEF8)
            )
        }
        // 🔹 Tipo da unidade
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tipo),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF0B2A46))
                    )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                  text = unidade?.categoria?.categoria?.get(0)?.nome ?: "Nenhuma categoria disponível",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color(0xFF1E5FA3)
                )
            }
        }
        // endereco
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.endereco),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF0B2A46))
                )
                Spacer(modifier = Modifier.width(10.dp))
                val endereco = unidade?.local?.endereco?.get(0)
                val enderecoTexto = if (endereco != null) {
                    "${endereco.logradouro} - ${endereco.estado} , ${endereco.cidade}"
                } else {
                    "Nenhum endereço disponível"
                }

                Text(
                    text = enderecoTexto,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E5FA3)
                )
            }
        }
        // 🔹 Telefone
        item {
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.telefone),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF0B2A46))
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = unidade?.telefone ?: "Nenhum telefone disponível",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E5FA3)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(26.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { expandir = !expandir },
                colors = CardDefaults.cardColors(containerColor = Color(0xFF298BE6)),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Tempo de espera das especialidades",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Icon(
                            imageVector = if (expandir) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                AnimatedVisibility(visible = expandir) {
                    Column(
                        modifier = Modifier
                        .padding(top = 10.dp)
                            .verticalScroll(rememberScrollState())
                            .navigationBarsPadding()
                    ) {
                        val lista = unidade?.especialidades?.especialidades ?: emptyList()
                        if (lista.isEmpty()) {
                            Text(
                                text = "Nenhuma especialidade disponível",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                        } else {
                            lista.forEachIndexed { index, especialidade ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    // 🔹 Nome da especialidade
                                    Text(
                                        text = especialidade.nome,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp, // maior destaque
                                        color = Color.White
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    // 🔹 Ícone + tempo de espera
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.AccessTime, // ícone de relógio
                                            contentDescription = "Tempo de espera",
                                            tint = Color(0xff1E5FA3), // azul claro para contraste
                                            modifier = Modifier.size(20.dp)
                                        )

                                        Spacer(modifier = Modifier.width(6.dp))

                                        Text(
                                            text = "Tempo de espera:",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFFEAF2FB)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = especialidade.tempo_espera ?: "-",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFFEAF2FB)
                                        )
                                    }

                                    // 🔹 Linha divisória (exceto no último)
                                    if (index < lista.lastIndex) {
                                        Spacer(modifier = Modifier.height(12.dp))
                                        Divider(
                                            color = Color(0x66B0C4DE), // com leve transparência (~40%)
                                            thickness = 1.dp,
                                            modifier = Modifier.padding(horizontal = 5.dp)
                                        )
                                        Spacer(modifier = Modifier.height(12.dp))
                                    }
                                }
                            }

                        }
                    }
                }

            }

        }
    }
}



//        //@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
//        @Composable
//        fun HomeInformacaoUnidadePreview() {
//            Tcc_PasTheme {
//                val navController = rememberNavController()
//                HomeInformacaoUnidade(navController = navController, id = 5)
//            }
//        }



