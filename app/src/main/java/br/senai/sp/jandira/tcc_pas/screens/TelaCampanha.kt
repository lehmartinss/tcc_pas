package br.senai.sp.jandira.tcc_pas.screens

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
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


//
//
//@Composable
//
//fun TelaDescricacaoCampanhas(){
//    Box(modifier = Modifier.fillMaxSize()
//        .background(Color(0xffF9FAFB))
//        .padding(top = 18.dp)
//    ){
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
//            Row (
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
//                    text = "Procure por uma unidade",
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
//
//        Column (
//            modifier = Modifier
//                .fillMaxSize()
//                //.background(color = Color.Cyan)
//                .padding( start = 20.dp, end = 20.dp, top = 80.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Informações",
//                color = Color(0xff1E5FA3),
//                textAlign = TextAlign.Center,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//            )
//            Spacer(modifier = Modifier.height(15.dp))
//            Card (
//                modifier = Modifier
//                    //.background(Color.Green)
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .padding(top = 5.dp),
//                RoundedCornerShape(20.dp),
//                //colors = CardDefaults.cardColors(contentColor = Color.Green)
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.logo),
//                    contentDescription = "",
//                    modifier = Modifier.fillMaxSize(),
//
//                    )
//            }
//
//
//            Text(
//                text = "A poliomielite, também conhecida como paralisia infantil, é uma doença contagiosa que pode causar sequelas permanentes e não tem cura. A vacinação é a única forma de prevenção. Durante o período da campanha, todas as crianças menores de 5 anos devem ser levadas às Unidades Básicas de Saúde para receber a dose. Além disso, os pais ou responsáveis podem aproveitar o momento para apresentar a caderneta de vacinação e atualizar outras vacinas em atraso. Vacinar é rápido, seguro e gratuito, e garante proteção para toda a vida.",
//                color = Color(0xff094175),
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 15.sp,
//                modifier = Modifier
//                    .padding(top = 12.dp)
//                    .align(Alignment.CenterHorizontally)
//
//            )
//
//            Row (
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp)
//            ){
//                Text(
//                    text = "Data início:",
//                    color = Color(0xff094175),
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 15.sp,
//                )
//                Text(
//                    text = " 25/10/2025"
//                )
//            }
//            Row (
//                modifier = Modifier
//                    .fillMaxWidth()
//            ){
//                Text(
//                    text = "Data término:",
//                    color = Color(0xff094175),
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 15.sp,
//                )
//                Text(
//                    text = " 10/12/2025"
//                )
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "Público Alvo:",
//                    color = Color(0xff094175),
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 15.sp,
//                )
//                Text(
//                    text = " Crianças de 1 a 5 anos"
//                )
//            }
//
//            Row (
//                modifier = Modifier
//                    .fillMaxWidth()
//            ){
//                Text(
//                    text = "Tipo de unidade disponível:",
//                    color = Color(0xff094175),
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 15.sp,
//                )
//                Text(
//                    text = " UBS e postos de saúde"
//                )
//            }
//
//            Row (
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "Observações:",
//                    color = Color(0xff094175),
//                    fontWeight = FontWeight.SemiBold,
//                    fontSize = 15.sp,
//                )
//                Text(
//                    text = " Levar documento com foto e carteirinha de vacinação."
//                )
//            }
//
//            Text(
//                text = "Cidades disponíveis:",
//                color = Color(0xff094175),
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 15.sp,
//                modifier = Modifier
//                    .padding(top = 8.dp, bottom = 8.dp)
//            )
//            Text(
//                text = "Jandira",
//                color = Color(0xff094175),
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 15.sp,
//            )
//
//            //INICIO DO COMPONENTE CARD DE UNIDADES
//
//            HorizontalDivider(
//                color = Color(0xff8CC6FF)
//            )
//
//            Card (
//                modifier = Modifier
//                    .fillMaxWidth()
//                    //.background(Color.Gray)
//                    .padding(8.dp),
//                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
//            ){
//                Row {
//                    Column {
//                        Text(
//                            text = "UBS Eunice",
//                            fontSize = 18.sp,
//                            modifier = Modifier
//                                .padding(bottom = 8.dp),
//                            color = Color(0xff135DB2),
//                            fontWeight = FontWeight.Bold
//                        )
//                        Row {
//                            Icon(
//                                imageVector = Icons.Default.Info,
//                                contentDescription = "Relógio",
//                                modifier = Modifier
//                                    .padding(top = 5.dp, end = 5.dp),
//                                tint = Color(0xff0B2A46)
//                            )
//                            Column () {
//                                Text(
//                                    text = "Tempo de espera",
//                                    fontSize = 15.sp
//                                )
//                                Text(
//                                    text = "15 minutos",
//                                    color = Color(0xff135DB2),
//                                    fontWeight = FontWeight.Bold,
//                                    fontSize = 13.sp
//                                )
//                            }
//
//                        }
//                    }
//                    Button(
//                        onClick = {},
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color(0XFF135DB2)
//                        ),
//                        modifier = Modifier
//                            .padding(start = 70.dp)
//                            .align(Alignment.CenterVertically)
//                    ) {
//                        Text(
//                            text = "Saiba mais",
//                            color = Color.White
//                        )
//                    }
//                }
//            }
//            HorizontalDivider(
//                color = Color(0xff8CC6FF)
//            )
//
//            //FINALIZAÇÃO DO COMPONENTE
//
//
//        }
//    }
//}
//
//
//@Preview(showSystemUi = true)
//@Composable
//private fun TelaProfilePreview() {
//    TelaDescricacaoCampanhas()
//
//}

@Composable
fun HomeCampanha(navController: NavHostController?){

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BarraDeNavegacaoCampanha(navController)
        },
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
                    composable(route = "Home"){ TelaDescricacaoCampanhas(paddingValues) }

                }
            }
        }
    )
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
                Spacer(Modifier.height(8.dp))
                content()
            }
        }
    }
}

@Composable
fun TelaDescricacaoCampanhas(paddingValues: PaddingValues) {

    var expandedSection by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffF9FAFB))
            .padding(top = 18.dp)
    ) {
        // TOPO AZUL
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
            Row (
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

        // CONTEÚDO
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 80.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Informações",
                color = Color(0xff1E5FA3),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 5.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }

            // TEXTO PRINCIPAL DA CAMPANHA
            Text(
                text = "A poliomielite, também conhecida como paralisia infantil, é uma doença contagiosa que pode causar sequelas permanentes e não tem cura. A vacinação é a única forma de prevenção. Durante o período da campanha, todas as crianças menores de 5 anos devem ser levadas às Unidades Básicas de Saúde para receber a dose. Além disso, os pais ou responsáveis podem aproveitar o momento para apresentar a caderneta de vacinação e atualizar outras vacinas em atraso. Vacinar é rápido, seguro e gratuito, e garante proteção para toda a vida.",
                color = Color(0xff094175),
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.height(15.dp))

            // SEÇÃO EXPANDÍVEL
            ExpandableSection(
                title = "Datas da Campanha",
                expanded = expandedSection == "datas",
                onExpandChange = {
                    expandedSection = if (expandedSection == "datas") null else "datas"
                }
            ) {
                Column {
                    Text("Início: 25/10/2025")
                    Text("Término: 10/12/2025")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            ExpandableSection(
                title = "Datas da Campanha",
                expanded = expandedSection == "datas",
                onExpandChange = {
                    expandedSection = if (expandedSection == "datas") null else "datas"
                }
            ) {
                Column {
                    Text("Início: 25/10/2025")
                    Text("Término: 10/12/2025")
                }
            }
        }
    }
}



@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomecampanhaPreview(){
    Tcc_PasTheme {
        HomeCampanha(null)
    }
}