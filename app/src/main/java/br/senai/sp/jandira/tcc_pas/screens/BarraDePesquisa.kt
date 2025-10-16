package br.senai.sp.jandira.tcc_pas.screens

import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import br.senai.sp.jandira.tcc_pas.R
import br.senai.sp.jandira.tcc_pas.model.Especialidade
import br.senai.sp.jandira.tcc_pas.model.Filtros
import br.senai.sp.jandira.tcc_pas.model.ItemComFoto
import br.senai.sp.jandira.tcc_pas.model.Unidade
import br.senai.sp.jandira.tcc_pas.model.UnidadeDeSaude
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltrar
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltrarPorPesquisa
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltroEspecialidade
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltroUnidade
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowRow
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.orEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraDePesquisaComFiltros(navController: NavHostController) {

    // menu quando o usuario clica na seta
    var expandirMenu by remember { mutableStateOf(false) }

    // menu quando o usuario vai digitar
    var textoPesquisa by remember { mutableStateOf("") }
    var campoFocado by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var especialidadeSelecionada by remember { mutableStateOf<String?>(null) }
    var unidadeSelecionada by remember { mutableStateOf<String?>(null) }
    var disponibilidadeSelecionada by remember { mutableStateOf<String?>(null) }
    var especialidades by remember { mutableStateOf<List<Especialidade>>(emptyList()) }
    var unidades by remember { mutableStateOf<List<Unidade>>(emptyList()) }
    val scope = rememberCoroutineScope()

    // api de filtrar por filtros
    val filtroService = remember { RetrofitFactoryFiltroEspecialidade().getFiltroService() }
    val unidadeService = remember { RetrofitFactoryFiltroUnidade().getUnidadeService() }
    val apiFiltrar = remember { RetrofitFactoryFiltrar().getUnidadesService() }

    // api de pesquisar por nome
    val pesquisaService = remember { RetrofitFactoryFiltrarPorPesquisa().getPesquisaService() }


    var sugestoes by remember { mutableStateOf<List<String>>(emptyList()) }
    var todasSugestoes by remember { mutableStateOf<List<String>>(emptyList()) }

    // 🔹 Buscar sugestões conforme digita
    LaunchedEffect(textoPesquisa) {
        if (textoPesquisa.isNotBlank()) {
            if (todasSugestoes.isEmpty()) {
                try {
                    val response = withContext(Dispatchers.IO) { pesquisaService.pesquisarUnidade("") }
                    if (response.isSuccessful && response.body() != null) {
                        todasSugestoes = response.body()!!.unidadesDeSaude.map { it.nome }
                    }
                } catch (_: Exception) { todasSugestoes = emptyList() }
            }
            sugestoes = todasSugestoes.filter { it.contains(textoPesquisa, ignoreCase = true) }
        } else {
            sugestoes = emptyList()
        }
    }

    // 🔹 Buscar filtros ao abrir o menu
    LaunchedEffect(expandirMenu) {
        if (expandirMenu) {
            if (especialidades.isEmpty()) {
                try {
                    val response = withContext(Dispatchers.IO) { filtroService.listarEspecialidade() }
                    if (response.isSuccessful) especialidades = response.body()?.especialidades.orEmpty()
                } catch (_: Exception) {}
            }
            if (unidades.isEmpty()) {
                try {
                    val response = withContext(Dispatchers.IO) { unidadeService.listarUnidades() }
                    if (response.isSuccessful) unidades = response.body()?.categorias.orEmpty()
                } catch (_: Exception) {}
            }
        }
    }

    // Box principal para capturar cliques fora da barra
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                campoFocado = false
                keyboardController?.hide()
                focusManager.clearFocus()
            }
    ) {
        // 🔵 Barra azul de pesquisa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 10.dp, vertical = 12.dp)
                .background(Color(0xFF298BE6), RoundedCornerShape(38))
                .zIndex(10f),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Ícone de seta
                Icon(
                    imageVector = if (expandirMenu) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Abrir/Fechar filtros",
                    tint = Color.White,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            expandirMenu = !expandirMenu
                            if (expandirMenu) keyboardController?.hide()
                        }
                )

                // Campo de pesquisa + filtros selecionados
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val selecionados = listOfNotNull(
                        especialidadeSelecionada,
                        unidadeSelecionada,
                        disponibilidadeSelecionada
                    )

                    selecionados.forEach { item ->
                        val onRemove: () -> Unit = {
                            when (item) {
                                especialidadeSelecionada -> especialidadeSelecionada = null
                                unidadeSelecionada -> unidadeSelecionada = null
                                disponibilidadeSelecionada -> disponibilidadeSelecionada = null
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .background(Color(0xFF7FBEF8), RoundedCornerShape(12.dp))
                                .padding(horizontal = 6.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = item,
                                color = Color.Black,
                                fontSize = 12.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Remover filtro",
                                tint = Color.Black,
                                modifier = Modifier
                                    .size(14.dp)
                                    .clickable { onRemove() }
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                campoFocado = true
                                focusRequester.requestFocus()
                            }
                    ) {
                        BasicTextField(
                            value = textoPesquisa,
                            onValueChange = { textoPesquisa = it },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(
                                color = Color.White,
                                fontSize = 16.sp
                            ),
                            cursorBrush = if (campoFocado) SolidColor(Color(0xFF1E5FA3)) else SolidColor(Color.Transparent),
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester)
                                .onFocusChanged { campoFocado = it.isFocused }
                        )

                        // Placeholder condicional
                        if (textoPesquisa.isEmpty() && !campoFocado &&
                            especialidadeSelecionada == null &&
                            unidadeSelecionada == null &&
                            disponibilidadeSelecionada == null
                        ) {
                            Text(
                                text = "Procure por uma unidade",
                                color = Color.White.copy(alpha = 0.5f),
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                // Ícone de pesquisa
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Pesquisar",
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            if (textoPesquisa.isNotBlank()) {
                                scope.launch(Dispatchers.IO) {
                                    try {
                                        val response = pesquisaService.pesquisarUnidade(textoPesquisa)
                                        if (response.isSuccessful && response.body() != null) {
                                            val todasUnidades = response.body()!!.unidadesDeSaude
                                            val unidadesFiltradas = todasUnidades.filter {
                                                it.nome.contains(textoPesquisa, ignoreCase = true)
                                            }
                                            withContext(Dispatchers.Main) {
                                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                                    "unidadesFiltradas", unidadesFiltradas
                                                )
                                                navController.navigate("mapafiltrado") { launchSingleTop = true }
                                            }
                                        }
                                    } catch (_: Exception) {}
                                }
                            }
                        }
                )
            }
        }

        // 🔽 Menu de sugestões (autocomplete)
        AnimatedVisibility(visible = textoPesquisa.isNotBlank() && sugestoes.isNotEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .zIndex(12f)
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .border(1.dp, Color.LightGray)
                    .padding(vertical = 4.dp)
            ) {
                Column {
                    sugestoes.forEach { nome ->
                        Text(
                            text = nome,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    textoPesquisa = nome
                                    sugestoes = emptyList()
                                    keyboardController?.hide()
                                }
                                .padding(12.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }

        // ⚙️ Menu de filtros
        AnimatedVisibility(visible = expandirMenu) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .zIndex(9f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(top = 70.dp, bottom = 32.dp)
                ) {
                    FiltroSingleSelectComFoto(
                        titulo = "Especialidades",
                        lista = especialidades.map { ItemComFoto(it.nome, it.foto_claro) },
                        selecionado = especialidadeSelecionada,
                        onSelect = { especialidadeSelecionada = it },
                        icone = R.drawable.especialidade
                    )

                    FiltroSingleSelectComFoto(
                        titulo = "Unidades Públicas",
                        lista = unidades.map { ItemComFoto(it.nome, it.foto_claro) },
                        selecionado = unidadeSelecionada,
                        onSelect = { unidadeSelecionada = it },
                        icone = R.drawable.unidades
                    )

                    FiltroSingleSelect(
                        titulo = "Atendimento 24h",
                        lista = listOf("Sim", "Não"),
                        selecionado = disponibilidadeSelecionada,
                        onSelect = { disponibilidadeSelecionada = it },
                        icone = R.drawable.atendimento
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            val filtros = Filtros(
                                categoria = unidadeSelecionada,
                                especialidade = especialidadeSelecionada,
                                disponibilidade24h = disponibilidadeParaInt(disponibilidadeSelecionada)
                            )

                            scope.launch(Dispatchers.IO) {
                                val response = apiFiltrar.filtrarUnidades(filtros)
                                if (response.isSuccessful && response.body() != null) {
                                    val todasUnidades = response.body()!!.unidadesDeSaude
                                    val unidadesFiltradas = todasUnidades.filter { unidade ->
                                        val categoriaOk = filtros.categoria?.let { selCategoria ->
                                            unidade.categoria.categoria?.any { cat -> cat.nome == selCategoria } ?: false
                                        } ?: true
                                        val especialidadeOk = filtros.especialidade?.let { selEspecialidade ->
                                            unidade.especialidades.especialidades.any { esp -> esp.nome == selEspecialidade }
                                        } ?: true
                                        val disponibilidadeOk = filtros.disponibilidade24h?.let { selDisp ->
                                            unidade.disponibilidade_24h == selDisp
                                        } ?: true
                                        categoriaOk && especialidadeOk && disponibilidadeOk
                                    }

                                    withContext(Dispatchers.Main) {
                                        navController.navigate("mapafiltrado") { launchSingleTop = true }
                                        navController.getBackStackEntry("mapafiltrado")
                                            .savedStateHandle["unidadesFiltradas"] = unidadesFiltradas
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(horizontal = 24.dp)
                            .height(45.dp)
                            .width(100.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1E5FA3),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "Filtrar",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
    }
}




//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BarraDePesquisaComFiltros(navController: NavHostController) {
//    var expandirMenu by remember { mutableStateOf(false) }
//    var textoPesquisa by remember { mutableStateOf("") }
//    var campoFocado by remember { mutableStateOf(false) }
//
//    var especialidadeSelecionada by remember { mutableStateOf<String?>(null) }
//    var unidadeSelecionada by remember { mutableStateOf<String?>(null) }
//    var disponibilidadeSelecionada by remember { mutableStateOf<String?>(null) }
//
//    var especialidades by remember { mutableStateOf<List<Especialidade>>(emptyList()) }
//    var unidades by remember { mutableStateOf<List<Unidade>>(emptyList()) }
//
//    val scope = rememberCoroutineScope()
//    val filtroService = remember { RetrofitFactoryFiltroEspecialidade().getFiltroService() }
//    val unidadeService = remember { RetrofitFactoryFiltroUnidade().getUnidadeService() }
//    val apiFiltrar = remember { RetrofitFactoryFiltrar().getUnidadesService() }
//    val pesquisaService = remember { RetrofitFactoryFiltrarPorPesquisa().getPesquisaService() }
//    val keyboardController = LocalSoftwareKeyboardController.current
//
//    var sugestoes by remember { mutableStateOf<List<String>>(emptyList()) }
//
//    var todasSugestoes by remember { mutableStateOf<List<String>>(emptyList()) } // todas unidades da API
//
//
//
//// 🔹 Buscar sugestões conforme digita (autocomplete)
//    LaunchedEffect(textoPesquisa) {
//        if (textoPesquisa.isNotBlank()) {
//            // 🔹 Busca na API apenas se ainda não carregou todas
//            if (todasSugestoes.isEmpty()) {
//                try {
//                    val response = withContext(Dispatchers.IO) { pesquisaService.pesquisarUnidade("") } // traz todas
//                    if (response.isSuccessful && response.body() != null) {
//                        todasSugestoes = response.body()!!.unidadesDeSaude.map { it.nome }
//                    }
//                } catch (_: Exception) {
//                    todasSugestoes = emptyList()
//                }
//            }
//
//            // 🔹 Filtrar localmente conforme o que o usuário digita
//            sugestoes = todasSugestoes.filter { it.contains(textoPesquisa, ignoreCase = true) }
//
//        } else {
//            sugestoes = emptyList()
//        }
//    }
//
//    // 🔹 Buscar filtros ao abrir o menu
//    LaunchedEffect(expandirMenu) {
//        if (expandirMenu) {
//            if (especialidades.isEmpty()) {
//                try {
//                    val response = withContext(Dispatchers.IO) { filtroService.listarEspecialidade() }
//                    if (response.isSuccessful) especialidades = response.body()?.especialidades.orEmpty()
//                } catch (_: Exception) {}
//            }
//            if (unidades.isEmpty()) {
//                try {
//                    val response = withContext(Dispatchers.IO) { unidadeService.listarUnidades() }
//                    if (response.isSuccessful) unidades = response.body()?.categorias.orEmpty()
//                } catch (_: Exception) {}
//            }
//        }
//    }
//
//    // 🔹 Buscar sugestões conforme digita (autocomplete)
//    LaunchedEffect(textoPesquisa) {
//        if (textoPesquisa.isNotBlank()) {
//            delay(300)
//            try {
//                val response = withContext(Dispatchers.IO) { pesquisaService.pesquisarUnidade(textoPesquisa) }
//                if (response.isSuccessful) {
//                    sugestoes = response.body()?.unidadesDeSaude?.map { it.nome } ?: emptyList()
//                }
//            } catch (_: Exception) {
//                sugestoes = emptyList()
//            }
//        } else {
//            sugestoes = emptyList()
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(modifier = Modifier.fillMaxSize()) {
//            // 🔵 Barra azul
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp)
//                    .padding(horizontal = 10.dp, vertical = 12.dp)
//                    .background(Color(0xFF298BE6), RoundedCornerShape(38))
//                    .zIndex(10f),
//                contentAlignment = Alignment.CenterStart
//            ) {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 12.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    // Seta para abrir filtros
//                    Icon(
//                        imageVector = if (expandirMenu) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                        contentDescription = "Abrir/Fechar filtros",
//                        tint = Color.White,
//                        modifier = Modifier
//                            .size(28.dp)
//                            .clickable {
//                                expandirMenu = !expandirMenu
//                                if (expandirMenu) keyboardController?.hide()
//                            }
//                    )
//
//                    // Filtros selecionados + campo de texto
//                    Row(
//                        modifier = Modifier
//                            .weight(1f)
//                            .padding(horizontal = 8.dp)
//                            .horizontalScroll(rememberScrollState()),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.spacedBy(6.dp)
//                    ) {
//                        val selecionados = listOfNotNull(
//                            especialidadeSelecionada,
//                            unidadeSelecionada,
//                            disponibilidadeSelecionada
//                        )
//
//                        // Mostrar filtros como botões
//                        selecionados.forEach { item ->
//                            Text(
//                                text = item,
//                                color = Color.Black,
//                                fontSize = 12.sp,
//                                maxLines = 1,
//                                overflow = TextOverflow.Ellipsis,
//                                modifier = Modifier
//                                    .background(Color(0xFF7FBEF8), RoundedCornerShape(12.dp))
//                                    .padding(horizontal = 6.dp, vertical = 4.dp)
//                            )
//                        }
//
//                        // Campo de texto
//                        Box(modifier = Modifier.weight(1f)) {
//                            BasicTextField(
//                                value = textoPesquisa,
//                                onValueChange = { textoPesquisa = it },
//                                singleLine = true,
//                                textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 16.sp),
//                                cursorBrush = SolidColor(Color.Black),
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .onFocusChanged { campoFocado = it.isFocused }
//                            )
//
//                            if (textoPesquisa.isEmpty() && !campoFocado) {
//                                Text(
//                                    text = "Procure por uma unidade",
//                                    color = Color.Black.copy(alpha = 0.5f),
//                                    fontSize = 16.sp
//                                )
//                            }
//                        }
//                    }
//
//                    // Ícone de lupa: filtrar pelo texto digitado
//                    Icon(
//                        imageVector = Icons.Default.Search,
//                        contentDescription = "Pesquisar",
//                        tint = Color.White,
//                        modifier = Modifier
//                            .size(24.dp)
//                            .clickable {
//                                if (textoPesquisa.isNotBlank()) {
//                                    Log.d("BarraPesquisa", "Texto digitado: $textoPesquisa")
//
//                                    scope.launch(Dispatchers.IO) {
//                                        try {
//                                            // Buscar unidades da API usando o nome digitado
//                                            val response = pesquisaService.pesquisarUnidade(textoPesquisa)
//
//                                            if (response.isSuccessful && response.body() != null) {
//                                                val todasUnidades = response.body()!!.unidadesDeSaude
//                                                Log.d("BarraPesquisa", "Unidades recebidas da API: ${todasUnidades.map { it.nome }}")
//
//                                                // Filtrar por similaridade (substring)
//                                                val unidadesFiltradas = todasUnidades.filter {
//                                                    it.nome.contains(textoPesquisa, ignoreCase = true)
//                                                }
//
//                                                Log.d("BarraPesquisa", "Unidades filtradas: ${unidadesFiltradas.map { it.nome }}")
//
//                                                // Navegar para a tela de mapa com as unidades filtradas
//                                                withContext(Dispatchers.Main) {
//                                                    navController.currentBackStackEntry?.savedStateHandle?.set(
//                                                        "unidadesFiltradas",
//                                                        unidadesFiltradas
//                                                    )
//                                                    navController.navigate("mapafiltrado") { launchSingleTop = true }
//                                                }
//                                            } else {
//                                                Log.e("BarraPesquisa", "Falha na API: ${response.errorBody()?.string()}")
//                                            }
//                                        } catch (e: Exception) {
//                                            Log.e("BarraPesquisa", "Erro ao buscar unidades: ${e.message}")
//                                        }
//                                    }
//                                }
//                            }
//                    )
//
//
//
//                }
//            }
//
//            // 🔽 Menu de sugestões autocomplete (apenas sugestões)
//            AnimatedVisibility(visible = textoPesquisa.isNotBlank() && sugestoes.isNotEmpty()) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 10.dp)
//                        .zIndex(12f)
//                        .background(Color.White, RoundedCornerShape(8.dp))
//                        .border(1.dp, Color.LightGray)
//                        .padding(vertical = 4.dp)
//                ) {
//                    Column {
//                        sugestoes.forEach { nome ->
//                            Text(
//                                text = nome,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .clickable {
//                                        textoPesquisa = nome
//                                        sugestoes = emptyList()
//                                        keyboardController?.hide()
//                                    }
//                                    .padding(12.dp),
//                                color = Color.Black
//                            )
//                        }
//                    }
//                }
//            }
//
//            // ⚙️ Menu de filtros completo (abre só ao clicar na seta)
//            AnimatedVisibility(visible = expandirMenu) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color.White)
//                        .zIndex(8f)
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .verticalScroll(rememberScrollState())
//                            .padding(top = 72.dp, bottom = 32.dp)
//                    ) {
//                        FiltroSingleSelectComFoto(
//                            titulo = "Especialidades",
//                            lista = especialidades.map { ItemComFoto(it.nome, it.foto_claro) },
//                            selecionado = especialidadeSelecionada,
//                            onSelect = { especialidadeSelecionada = it }
//                        )
//
//                        FiltroSingleSelectComFoto(
//                            titulo = "Unidades Públicas",
//                            lista = unidades.map { ItemComFoto(it.nome, it.foto_claro) },
//                            selecionado = unidadeSelecionada,
//                            onSelect = { unidadeSelecionada = it }
//                        )
//
//                        FiltroSingleSelect(
//                            titulo = "Atendimento 24h",
//                            lista = listOf("Sim", "Não"),
//                            selecionado = disponibilidadeSelecionada,
//                            onSelect = { disponibilidadeSelecionada = it }
//                        )
//
//                        Spacer(modifier = Modifier.height(24.dp))
//
//                        Button(
//                        onClick = {
//                            val filtros = Filtros(
//                                categoria = unidadeSelecionada,
//                                especialidade = especialidadeSelecionada,
//                                disponibilidade24h = disponibilidadeParaInt(disponibilidadeSelecionada)
//                            )
//
//                            Log.d("Filtro", "Filtros selecionados: categoria=${filtros.categoria}, especialidade=${filtros.especialidade}, disponibilidade=${filtros.disponibilidade24h}")
//
//                            CoroutineScope(Dispatchers.IO).launch {
//                                val response = apiFiltrar.filtrarUnidades(filtros)
//
//                                if (response.isSuccessful && response.body() != null) {
//                                    val todasUnidades = response.body()!!.unidadesDeSaude
//                                    Log.d("Filtro", "Unidades recebidas da API: ${todasUnidades.map { it.nome }}")
//
//                                    val unidadesFiltradas = todasUnidades.filter { unidade ->
//                                        val categoriaOk = filtros.categoria?.let { selCategoria ->
//                                            unidade.categoria.categoria?.any { cat -> cat.nome == selCategoria } ?: false
//                                        } ?: true
//
//                                        val especialidadeOk = filtros.especialidade?.let { selEspecialidade ->
//                                            unidade.especialidades.especialidades.any { esp -> esp.nome == selEspecialidade }
//                                        } ?: true
//
//                                        val disponibilidadeOk = filtros.disponibilidade24h?.let { selDisp ->
//                                            unidade.disponibilidade_24h == selDisp
//                                        } ?: true
//
//                                        categoriaOk && especialidadeOk && disponibilidadeOk
//                                    }
//
//                                    Log.d("Filtro", "Unidades filtradas: ${unidadesFiltradas.map { it.nome }}")
//
//                                    withContext(Dispatchers.Main) {
//                                        navController.currentBackStackEntry?.let { currentEntry ->
//                                            navController.navigate("mapafiltrado") {
//                                                launchSingleTop = true
//                                            }
//                                            navController.getBackStackEntry("mapafiltrado")
//                                                .savedStateHandle["unidadesFiltradas"] = unidadesFiltradas
//                                        }
//                                    }
//                                } else {
//                                    Log.e("Filtro", "Falha na API: ${response.errorBody()?.string()}")
//                                }
//                            }
//                        }
//                    ) {
//                        Text(text = "Filtrar")
//                    }
//                    }
//                }
//            }
//        }
//    }
//}
//





// ESTA 100% FUNCIONAL

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun BarraDePesquisaComFiltros(navController: NavHostController) {
//
//    var expandirMenu by remember { mutableStateOf(false) }
//    var especialidadeSelecionada by remember { mutableStateOf<String?>(null) }
//    var unidadeSelecionada by remember { mutableStateOf<String?>(null) }
//    var disponibilidadeSelecionada by remember { mutableStateOf<String?>(null) }
//
//    var especialidades by remember { mutableStateOf<List<Especialidade>>(emptyList()) }
//    var unidades by remember { mutableStateOf<List<Unidade>>(emptyList()) }
//    var unidadesDoFiltro by remember { mutableStateOf<List<UnidadeDeSaude>>(emptyList()) }
//
//    //  API DE ESPECIALIDADES E UNIDADES PUBLICA
//    val filtroService = remember { RetrofitFactoryFiltroEspecialidade().getFiltroService() }
//    val unidadeService = remember { RetrofitFactoryFiltroUnidade().getUnidadeService() }
//    val apiFiltrar = remember { RetrofitFactoryFiltrar().getUnidadesService() }
//
//    val scope = rememberCoroutineScope()
//
//    LaunchedEffect(expandirMenu) {
//        if (expandirMenu) {
//            if (especialidades.isEmpty()) {
//                try {
//                    val response =
//                        withContext(Dispatchers.IO) { filtroService.listarEspecialidade() }
//                    if (response.isSuccessful) especialidades =
//                        response.body()?.especialidades.orEmpty()
//                } catch (_: Exception) {
//                }
//            }
//            if (unidades.isEmpty()) {
//                try {
//                    val response = withContext(Dispatchers.IO) { unidadeService.listarUnidades() }
//                    if (response.isSuccessful) unidades = response.body()?.categorias.orEmpty()
//                } catch (_: Exception) {
//                }
//            }
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(56.dp)
//                .padding(horizontal = 10.dp, vertical = 12.dp)
//                .zIndex(10f)
//                .background(Color(0xFF298BE6), RoundedCornerShape(38))
//                .clickable { expandirMenu = !expandirMenu },
//            contentAlignment = Alignment.CenterStart
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 12.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Icon(
//                    imageVector = if (expandirMenu) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
//                    contentDescription = "Abrir/Fechar menu",
//                    tint = Color.White,
//                    modifier = Modifier.size(24.dp)
//                )
//
//                Row(
//                    modifier = Modifier
//                        .weight(1f)
//                        .padding(horizontal = 5.dp)
//                        .horizontalScroll(rememberScrollState()),
//                    horizontalArrangement = Arrangement.spacedBy(6.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    val selecionados = listOfNotNull(
//                        especialidadeSelecionada,
//                        unidadeSelecionada,
//                        disponibilidadeSelecionada
//                    )
//
//                    if (selecionados.isEmpty()) {
//                        Text(
//                            text = "Procure por uma unidade",
//                            color = Color.White,
//                            style = MaterialTheme.typography.bodyMedium,
//                            maxLines = 1,
//                            overflow = TextOverflow.Ellipsis
//                        )
//                    } else {
//                        selecionados.forEach { item ->
//                            Text(
//                                text = item,
//                                color = Color.Black,
//                                fontSize = 12.sp,
//                                maxLines = 1,
//                                overflow = TextOverflow.Ellipsis,
//                                modifier = Modifier
//                                    .background(Color(0xFF7FBEF8), RoundedCornerShape(12.dp))
//                                    .padding(horizontal = 5.dp, vertical = 4.dp)
//                            )
//                        }
//                    }
//                }
//
//                Icon(
//                    imageVector = Icons.Default.Search,
//                    contentDescription = "Pesquisar",
//                    tint = Color.White,
//                    modifier = Modifier.size(20.dp)
//                )
//            }
//        }
//
//        AnimatedVisibility(visible = expandirMenu) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Color.White)
//                    .zIndex(9f)
//            ) {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .verticalScroll(rememberScrollState())
//                        .padding(top = 72.dp, bottom = 32.dp)
//                ) {
//
//
//                    FiltroSingleSelectComFoto(
//                        titulo = "Especialidades",
//                        lista = especialidades.map { ItemComFoto(it.nome, it.foto_claro) },
//                        selecionado = especialidadeSelecionada,
//                        onSelect = { especialidadeSelecionada = it }
//                    )
//
//                    FiltroSingleSelectComFoto(
//                        titulo = "Unidades Públicas",
//                        lista = unidades.map { ItemComFoto(it.nome, it.foto_claro) },
//                        selecionado = unidadeSelecionada,
//                        onSelect = { unidadeSelecionada = it }
//                    )
//
//
//                    FiltroSingleSelect(
//                        titulo = "Atendimento 24h",
//                        lista = listOf("Sim", "Não"),
//                        selecionado = disponibilidadeSelecionada,
//                        onSelect = { disponibilidadeSelecionada = it }
//                    )
//
//                    Spacer(modifier = Modifier.height(24.dp))
//
//                    Button(
//                        onClick = {
//                            val filtros = Filtros(
//                                categoria = unidadeSelecionada,
//                                especialidade = especialidadeSelecionada,
//                                disponibilidade24h = disponibilidadeParaInt(disponibilidadeSelecionada)
//                            )
//
//                            Log.d("Filtro", "Filtros selecionados: categoria=${filtros.categoria}, especialidade=${filtros.especialidade}, disponibilidade=${filtros.disponibilidade24h}")
//
//                            CoroutineScope(Dispatchers.IO).launch {
//                                val response = apiFiltrar.filtrarUnidades(filtros)
//
//                                if (response.isSuccessful && response.body() != null) {
//                                    val todasUnidades = response.body()!!.unidadesDeSaude
//                                    Log.d("Filtro", "Unidades recebidas da API: ${todasUnidades.map { it.nome }}")
//
//                                    val unidadesFiltradas = todasUnidades.filter { unidade ->
//                                        val categoriaOk = filtros.categoria?.let { selCategoria ->
//                                            unidade.categoria.categoria?.any { cat -> cat.nome == selCategoria } ?: false
//                                        } ?: true
//
//                                        val especialidadeOk = filtros.especialidade?.let { selEspecialidade ->
//                                            unidade.especialidades.especialidades.any { esp -> esp.nome == selEspecialidade }
//                                        } ?: true
//
//                                        val disponibilidadeOk = filtros.disponibilidade24h?.let { selDisp ->
//                                            unidade.disponibilidade_24h == selDisp
//                                        } ?: true
//
//                                        categoriaOk && especialidadeOk && disponibilidadeOk
//                                    }
//
//                                    Log.d("Filtro", "Unidades filtradas: ${unidadesFiltradas.map { it.nome }}")
//
//                                    withContext(Dispatchers.Main) {
//                                        navController.currentBackStackEntry?.let { currentEntry ->
//                                            navController.navigate("mapafiltrado") {
//                                                launchSingleTop = true
//                                            }
//                                            navController.getBackStackEntry("mapafiltrado")
//                                                .savedStateHandle["unidadesFiltradas"] = unidadesFiltradas
//                                        }
//                                    }
//                                } else {
//                                    Log.e("Filtro", "Falha na API: ${response.errorBody()?.string()}")
//                                }
//                            }
//                        }
//                    ) {
//                        Text(text = "Filtrar")
//                    }
//                }
//            }
//        }
//    }
//}

fun disponibilidadeParaInt(valor: String?): Int? {
    return when (valor) {
        "Sim" -> 1
        "Não" -> 0
        else -> null
    }
}

@Composable
fun FiltroSingleSelectComFoto(
    titulo: String,
    lista: List<ItemComFoto>,
    selecionado: String?,
    onSelect: (String?) -> Unit,
    icone: Int
) {
    var mostrar by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp, end = 16.dp, bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(icone),
                contentDescription = titulo,
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { mostrar = !mostrar }) {
                Icon(
                    imageVector = if (mostrar) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }

        if (mostrar) {
            lista.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(if (selecionado == item.nome) null else item.nome) }
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    // Foto à esquerda
                    item.fotoClaro?.let { fotoUrl ->
                        AsyncImage(
                            model = fotoUrl,
                            contentDescription = item.nome,
                            modifier = Modifier
                                .size(15.dp)
                                .clip(RoundedCornerShape(6.dp))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Text(
                        text = item.nome,
                        color = if (selecionado == item.nome) Color(0xFF7FBEF8) else Color.Black,
                        fontWeight = if (selecionado == item.nome) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}


@Composable
fun FiltroSingleSelect(
    titulo: String,
    lista: List<String>,
    selecionado: String?,
    onSelect: (String?) -> Unit,
    icone: Int
) {
    var mostrar by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {

        // 🔹 Cabeçalho (com imagem e seta)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp, end = 16.dp, bottom = 4.dp)
        ) {
            Image(
                painter = painterResource(id = icone),
                contentDescription = titulo,
                modifier = Modifier
                    .size(25.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(8.dp))
            Text(titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { mostrar = !mostrar }) {
                Icon(
                    imageVector = if (mostrar) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null
                )
            }
        }

        // 🔹 Lista de opções
        if (mostrar) {
            lista.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(if (selecionado == item) null else item) }
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    // 🖼️ Define imagem com base no item
                    val imagem = when (item) {
                        "Sim" -> R.drawable.sim
                        "Não" -> R.drawable.nao
                        else -> null
                    }

                    // Mostra imagem se existir
                    imagem?.let {
                        Image(
                            painter = painterResource(id = imagem),
                            contentDescription = item,
                            modifier = Modifier
                                .size(15.dp)
                                .clip(RoundedCornerShape(6.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                    }

                    Text(
                        text = item,
                        color = if (selecionado == item) Color(0xFF7FBEF8) else Color.Black,
                        fontWeight = if (selecionado == item) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
