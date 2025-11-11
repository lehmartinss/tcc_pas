package br.senai.sp.jandira.tcc_pas.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.R

@Composable
fun TelaSobre(navHostController: NavHostController) {
    Scaffold(
        bottomBar = {
            BarraDeNavegacaoSobre(navController = navHostController  )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo PAS",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(80.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Sobre",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, 5.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.ConteudoSobre),
                    fontSize = 16.sp,
                    color = Color(0xFF1976D2),
                    textAlign = TextAlign.Justify,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(13.dp))

                Text(
                    text = stringResource(R.string.ConteudoSobre2),
                    fontSize = 16.sp,
                    color = Color(0xFF1976D2),
                    textAlign = TextAlign.Justify,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(13.dp))

                Text(
                    text = stringResource(R.string.ConteudoSobre3),
                    fontSize = 16.sp,
                    color = Color(0xFF1976D2),
                    textAlign = TextAlign.Justify,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(13.dp))

                Text(
                    text = stringResource(R.string.ConteudoSobre4),
                    fontSize = 16.sp,
                    color = Color(0xFF1976D2),
                    textAlign = TextAlign.Justify,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}


@Composable
fun BarraDeNavegacaoSobre(navController: NavHostController) {
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
            onClick = {navController!!.navigate(route = "mapanav")},
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
fun TelaSobrePreview() {
    val navController = rememberNavController()
    TelaSobre(navController)
}