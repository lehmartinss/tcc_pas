package br.senai.sp.jandira.tcc_pas.screens

import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import br.senai.sp.jandira.tcc_pas.model.Especialidade
import br.senai.sp.jandira.tcc_pas.model.Filtros
import br.senai.sp.jandira.tcc_pas.model.Unidade
import br.senai.sp.jandira.tcc_pas.model.UnidadeDeSaude
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltrar
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltroEspecialidade
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltroUnidade
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme
import com.google.accompanist.flowlayout.FlowRow
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.collections.orEmpty



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraDePesquisaComFiltros(navController: NavHostController) {
    var expandirMenu by remember { mutableStateOf(false) }

    // Estados de seleção dos filtros
    var especialidadeSelecionada by remember { mutableStateOf<String?>(null) }
    var unidadeSelecionada by remember { mutableStateOf<String?>(null) }
    var disponibilidadeSelecionada by remember { mutableStateOf<String?>(null) }

    // Estados de carregamento e dados da API
    var especialidades by remember { mutableStateOf<List<Especialidade>>(emptyList()) }
    var unidades by remember { mutableStateOf<List<Unidade>>(emptyList()) }
    var unidadesDoFiltro by remember { mutableStateOf<List<UnidadeDeSaude>>(emptyList()) }

    // Serviços de API
    val filtroService = remember { RetrofitFactoryFiltroEspecialidade().getFiltroService() }
    val unidadeService = remember { RetrofitFactoryFiltroUnidade().getUnidadeService() }

    val apiFiltrar = remember { RetrofitFactoryFiltrar().getUnidadesService() }
    val scope = rememberCoroutineScope() // <--- necessário para chamar API no onClick

    // Carregar especialidades e unidades quando abrir o menu
    LaunchedEffect(expandirMenu) {
        if (expandirMenu) {
            if (especialidades.isEmpty()) {
                try {
                    val response =
                        withContext(Dispatchers.IO) { filtroService.listarEspecialidade() }
                    if (response.isSuccessful) especialidades =
                        response.body()?.especialidades.orEmpty()
                } catch (_: Exception) {
                }
            }
            if (unidades.isEmpty()) {
                try {
                    val response = withContext(Dispatchers.IO) { unidadeService.listarUnidades() }
                    if (response.isSuccessful) unidades = response.body()?.categorias.orEmpty()
                } catch (_: Exception) {
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 🔹 Barra de pesquisa
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 10.dp, vertical = 12.dp)
                .zIndex(10f)
                .background(Color(0xFF298BE6), RoundedCornerShape(38))
                .clickable { expandirMenu = !expandirMenu },
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = if (expandirMenu) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Abrir/Fechar menu",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val selecionados = listOfNotNull(
                        especialidadeSelecionada,
                        unidadeSelecionada,
                        disponibilidadeSelecionada
                    )

                    if (selecionados.isEmpty()) {
                        Text(
                            text = "Procure por uma unidade",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    } else {
                        selecionados.forEach { item ->
                            Text(
                                text = item,
                                color = Color.Black,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .background(Color(0xFF7FBEF8), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 5.dp, vertical = 4.dp)
                            )
                        }
                    }
                }

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Pesquisar",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        // 🔹 Menu expandido
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
                        .padding(top = 72.dp, bottom = 32.dp)
                ) {
                    FiltroSingleSelect(
                        titulo = "Especialidades",
                        lista = especialidades.map { it.nome },
                        selecionado = especialidadeSelecionada,
                        onSelect = { especialidadeSelecionada = it }
                    )

                    FiltroSingleSelect(
                        titulo = "Unidades Públicas",
                        lista = unidades.map { it.nome },
                        selecionado = unidadeSelecionada,
                        onSelect = { unidadeSelecionada = it }
                    )

                    FiltroSingleSelect(
                        titulo = "Atendimento 24h",
                        lista = listOf("Sim", "Não"),
                        selecionado = disponibilidadeSelecionada,
                        onSelect = { disponibilidadeSelecionada = it }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

//                    // 🔹 Botão Filtrar
//                    BotaoFiltrar(
//                        especialidade = especialidadeSelecionada,
//                        unidade = unidadeSelecionada,
//                        modifier = Modifier
//                            .align(Alignment.End)
//                            .padding(end = 16.dp)
//                    ) { resultado ->
//                        unidadesDoFiltro = resultado
//                        expandirMenu = false
//                    }
//
//
//                    // Navegar para TelaMapa passando resultados
//                    if (resultado.isNotEmpty()) {
//                        val unidadesJson = Uri.encode(Gson().toJson(resultado))
//                        navController.navigate("tela_mapa/$unidadesJson")
//                    } else {
//                        // Opcional: mostrar alerta "Nenhum resultado"
//                    }

                    Button(
                        onClick = {
                            val filtros = Filtros(
                                categoria = unidadeSelecionada,       // exemplo: "Hospital Geral"
                                especialidade = especialidadeSelecionada // exemplo: "Ortopedia"
                            )

                            Log.d("Filtro", "Filtros selecionados: categoria=${filtros.categoria}, especialidade=${filtros.especialidade}")

                            CoroutineScope(Dispatchers.IO).launch {
                                val response = apiFiltrar.filtrarUnidades(filtros)

                                if (response.isSuccessful && response.body() != null) {
                                    val todasUnidades = response.body()!!.unidadesDeSaude
                                    Log.d("Filtro", "Unidades recebidas da API: ${todasUnidades.map { it.nome }}")

                                    // Filtragem local baseada nos filtros
                                    val unidadesFiltradas = todasUnidades.filter { unidade ->
                                        val categoriaOk = filtros.categoria?.let { selCategoria ->
                                            unidade.categoria.categoria?.any { cat -> cat.nome == selCategoria } ?: false
                                        } ?: true

                                        val especialidadeOk = filtros.especialidade?.let { selEspecialidade ->
                                            unidade.especialidades.especialidades.any { esp -> esp.nome == selEspecialidade }
                                        } ?: true

                                        categoriaOk && especialidadeOk
                                    }

                                    Log.d("Filtro", "Unidades filtradas: ${unidadesFiltradas.map { it.nome }}")

                                    withContext(Dispatchers.Main) {
                                        // Passa as unidades filtradas para a tela mapa
                                        navController.currentBackStackEntry?.savedStateHandle?.set(
                                            "unidadesFiltradas",
                                            unidadesFiltradas
                                        )
                                        navController.navigate("mapafiltrado")
                                    }
                                } else {
                                    Log.e("Filtro", "Falha na API: ${response.errorBody()?.string()}")
                                }
                            }
                        }
                    ) {
                        Text(text = "Filtrar")
                    }



//                    Button(
//                        onClick = {
//                            val filtros = Filtros(
//                                categoria = unidadeSelecionada,
//                                especialidade = especialidadeSelecionada
//                            )
//
//                            CoroutineScope(Dispatchers.IO).launch {
//                                val response = apiFiltrar.filtrarUnidades(filtros)
//
//                                if (response.isSuccessful && response.body() != null) {
//                                    val unidadesFiltradas = response.body()!!.unidadesDeSaude
//
//                                    withContext(Dispatchers.Main) {
//                                        // Passa as unidades filtradas para a tela mapa
//                                        navController.currentBackStackEntry?.savedStateHandle?.set(
//                                            "unidadesFiltradas",
//                                            unidadesFiltradas
//                                        )
//                                        navController.navigate("mapafiltrado")
//                                    }
//                                }
//                            }
//                        }
//                    ) {
//                        Text(text = "Filtrar")
//                    }

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
    onSelect: (String?) -> Unit
) {
    var mostrar by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 10.dp, end = 16.dp, bottom = 4.dp)
        ) {
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
                        .clickable { onSelect(if (selecionado == item) null else item) }
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
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
//@Composable
//fun BotaoFiltrar(
//    especialidade: String?,
//    unidade: String?,
//    navController: NavHostController,
//    modifier: Modifier = Modifier,
//    onResult: (List<UnidadeDeSaude>) -> Unit
//) {
//    var carregando by remember { mutableStateOf(false) }
//    var erro by remember { mutableStateOf<String?>(null) }
//    val apiFiltrar = remember { RetrofitFactoryFiltrar().getUnidadesService() }
//    val scope = rememberCoroutineScope()
//
//    Button(
//        onClick = {
//            carregando = true
//            erro = null
//            val filtros = Filtros(
//                categoria = unidade,
//                especialidade = especialidade
//            )
//
//            scope.launch(Dispatchers.IO) {
//                try {
//                    val response = apiFiltrar.filtrarUnidades(filtros)
//                    if (response.isSuccessful) {
//                        val body = response.body()
//                        if (body != null && body.status) {
//                            withContext(Dispatchers.Main) {
//                                onResult(body.unidadesDeSaude)
//                                if (body.unidadesDeSaude.isNotEmpty()) {
//                                    val unidadesJson = Uri.encode(Gson().toJson(body.unidadesDeSaude))
//                                    navController.navigate("mapafiltrado/$unidadesJson")
//                                }
//                            }
//                        } else {
//                            withContext(Dispatchers.Main) {
//                                erro = "Nenhum resultado encontrado."
//                                onResult(emptyList())
//                            }
//                        }
//                    } else {
//                        withContext(Dispatchers.Main) {
//                            erro = "Erro (${response.code()}) ao filtrar."
//                            onResult(emptyList())
//                        }
//                    }
//                } catch (e: Exception) {
//                    withContext(Dispatchers.Main) {
//                        erro = "Falha de conexão: ${e.message}"
//                        onResult(emptyList())
//                    }
//                } finally {
//                    withContext(Dispatchers.Main) {
//                        carregando = false
//                    }
//                }
//            }
//        },
//        modifier = modifier
//    ) {
//        if (carregando) {
//            CircularProgressIndicator(
//                color = Color.White,
//                modifier = Modifier.size(20.dp)
//            )
//        } else {
//            Text("Filtrar")
//        }
//    }
//}


//
//@Composable
//fun BotaoFiltrar(
//    especialidade: String?,
//    unidade: String?,
//    modifier: Modifier = Modifier,
//    onResult: (List<UnidadeDeSaude>) -> Unit
//) {
//    var carregando by remember { mutableStateOf(false) }
//    var erro by remember { mutableStateOf<String?>(null) }
//
//    val apiFiltrar = remember { RetrofitFactoryFiltrar().getUnidadesService() }
//    val scope = rememberCoroutineScope() // Para lançar coroutine no onClick
//
//    Button(
//        onClick = {
//            carregando = true
//            erro = null
//            val filtros = Filtros(
//                categoria = unidade,
//                especialidade = especialidade
//            )
//
//            scope.launch {
//                try {
//                    val response = withContext(Dispatchers.IO) {
//                        apiFiltrar.filtrarUnidades(filtros) // POST
//                    }
//
//                    if (response.isSuccessful) {
//                        val body = response.body()
//                        if (body != null && body.status) {
//                            onResult(body.unidadesDeSaude)
//                        } else {
//                            erro = "Nenhum resultado encontrado."
//                            onResult(emptyList())
//                        }
//                    } else {
//                        erro = "Erro (${response.code()}) ao filtrar."
//                        onResult(emptyList())
//                    }
//                } catch (e: Exception) {
//                    erro = "Falha de conexão: ${e.message}"
//                    onResult(emptyList())
//                } finally {
//                    carregando = false
//                }
//            }
//        },
//        modifier = modifier
//    ) {
//        if (carregando) {
//            CircularProgressIndicator(
//                color = Color.White,
//                modifier = Modifier.size(20.dp)
//            )
//        } else {
//            Text("Filtrar")
//        }
//    }
//}
}