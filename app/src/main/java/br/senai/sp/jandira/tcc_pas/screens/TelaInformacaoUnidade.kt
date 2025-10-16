package br.senai.sp.jandira.tcc_pas.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.model.CampanhaResponse
import br.senai.sp.jandira.tcc_pas.model.UnidadeDeSaudeResponse
import br.senai.sp.jandira.tcc_pas.model.UnidadeResponse
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltroUnidade
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme
import coil.compose.AsyncImage


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
private fun BarraDeNavegacaoInfoUnidadePreview(){
    Tcc_PasTheme {
        BarraDeNavegacaoInfoUnidade(null)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaInformacaoUnidade(paddingValues: PaddingValues, id: Int,  navController: NavHostController) {

    var unidade by remember { mutableStateOf<UnidadeDeSaudeResponse?>(null) }
    val apiUnidade = RetrofitFactoryFiltroUnidade().getUnidadeService()
    var especialidadeSelecionadaExpanded by remember { mutableStateOf(false) }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xffF9FAFB))
                .padding(top = 18.dp)
        ) {

            // Barra de pesquisa
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
                .background(Color.White)
                .padding(paddingValues)
        ) {
            // Hospital Image
            item {
                Box (
                    modifier = Modifier
                    .fillMaxSize()
                    .height(300.dp)
                    .background(Color(0xFF93979F)

//                AsyncImage(
//                    model = hospital.imageResId ?: hospital.imageUrl,
//                    contentDescription = "Foto do ${hospital.nome}",
//                   modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
//                   contentScale = ContentScale.Crop
//
//                )
                    )
                )
            }

            // Hospital Name
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = unidade!!.nome ,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            // General Wait Time
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Text(
                        text = "Tempo geral de espera",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = unidade!!.tempo_espera_geral,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }

            // Hospital Type
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = null,
                        tint =Color.Blue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = hospital.tipo,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = PrimaryBlue
//                    )
                }
            }

            // Divider
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }

            // Address
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.Blue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
//                    Text(
//                        text = unidade!!.local,
//                        fontSize = 14.sp,
//                        color = Color.Blue
//                    )
                }
            }

            // Phone
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = Color.Blue,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = unidade!!.telefone,
                        fontSize = 14.sp,
                        color = Color.Blue
                    )
                }
            }

            // Divider
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }

//            // Specialty Selector
//            item {
//                Spacer(modifier = Modifier.height(16.dp))
//                OutlinedButton(
//                    onClick = { especialidadeSelecionadaExpanded = !especialidadeSelecionadaExpanded },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp),
//                    colors = ButtonDefaults.outlinedButtonColors(
//                        containerColor = Color.White,
//                        contentColor = PrimaryBlue
//                    ),
//                    border = BorderStroke(1.dp, PrimaryBlue)
//                ) {
//                    Text(
//                        text = "Selecione uma especialidade",
//                        modifier = Modifier.weight(1f)
//                    )
//                    Icon(
//                        imageVector = Icons.Default.KeyboardArrowDown,
//                        contentDescription = null
//                    )
//                }
//            }

            // Divider
            item {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray
                )
            }

//            // Specialties List
//            items(hospital.especialidades) { especialidade ->
//                EspecialidadeItem(especialidade = especialidade)
//            }
//
//            // Bottom spacing
//            item {
//                Spacer(modifier = Modifier.height(16.dp))
//            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable private fun HomeInformacaoUnidadePreview() {
    Tcc_PasTheme {
        val navController = rememberNavController()
        HomeInformacaoUnidade(navController = navController, id = 1)
    }
}
