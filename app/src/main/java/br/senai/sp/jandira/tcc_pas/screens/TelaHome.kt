package br.senai.sp.jandira.tcc_pas.screens

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.tcc_pas.R



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela() {



    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(Icons.Default.Home, contentDescription = "Início", tint = Color.White)
                    Icon(Icons.Default.LocationOn, contentDescription = "Mapa", tint = Color.White)
                    Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFEFF5FC))
                .verticalScroll(rememberScrollState())
        ) {
            // Card cinza no lugar do mapa
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.LightGray)
            ) {
                // Barra de pesquisa
                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF2196F3) // azul
                    ),
                    title = {
                        // Campo de Busca

                        OutlinedTextField(
                            value = {},
                            onValueChange = {

                            },
                            modifier = Modifier
                                .weight(1f) // Ocupa o espaço restante
                                .padding(horizontal = 8.dp) // Espaçamento entre logo e busca, e busca e avatar
                                .height(48.dp), // Altura ajustada para parecer com o Figma

                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color.Transparent, // Sem borda visível
                                focusedBorderColor = Color.Transparent, // Sem borda visível
                                focusedLabelColor = Color.Transparent, // Ajuste se precisar de label
                                unfocusedLabelColor = Color.Transparent, // Ajuste se precisar de label
                                cursorColor = Color.Black, // Cor do cursor
                                focusedContainerColor = Color.White, // Fundo branco como no Figma
                                unfocusedContainerColor = Color.White
                            ),
                            shape = RoundedCornerShape(24.dp), // Borda arredondada (metade da altura para ser oval)
                            singleLine = true // Garante que fique em uma única linha
                        )
                    }
                )
            }




            // Card informativo com texto
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFB3E5FC)) // azul claro
            ) {
                Text(
                    text = "Encontre a unidade de saúde mais próxima:\nclique no mapa para mais informações",
                    modifier = Modifier
                        .padding(16.dp),
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Título "Informações"
            Text(
                text = "Informações",
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Banner estático
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 16.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo), // substitua pelo seu recurso
                    contentDescription = "Campanha de Vacinação",
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(80.dp)) // espaço para a BottomBar
        }
    }
}



@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TelaPreview() {
    Tela()
}

