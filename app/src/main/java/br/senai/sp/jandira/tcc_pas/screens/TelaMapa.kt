package br.senai.sp.jandira.tcc_pas.screens

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.tcc_pas.R
import br.senai.sp.jandira.tcc_pas.model.UnidadeDeSaude
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryCampanha
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryOSM
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView


@Composable
fun HomeMapa(navController: NavHostController) {
    val unidadesFiltradas = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.get<List<UnidadeDeSaude>>("unidadesFiltradas") ?: emptyList()

    TelaMapa(navController, unidadesFiltradas)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaMapa(navController: NavHostController, unidades: List<UnidadeDeSaude>) {

    val api = RetrofitFactoryOSM().getOSMService()

    val context = LocalContext.current

    // osmdroid
    var latitude by remember { mutableStateOf(0.0) }
    var longitude by remember { mutableStateOf(0.0) }

    var mapView: MapView? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // 🛰️ FUSED → cria o cliente de localização
    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // 🧩 PERMISSÃO → controla se o usuário já deu acesso à localização
    val locationPermissionGranted = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> locationPermissionGranted.value = granted }

    // 🧩 PERMISSÃO → pede permissão assim que o composable é carregado
    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (!granted) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            locationPermissionGranted.value = true
        }
    }

    LaunchedEffect(locationPermissionGranted.value) {
        if (locationPermissionGranted.value) {
            try {
                val fusedClient = LocationServices.getFusedLocationProviderClient(context)
                fusedClient.lastLocation.addOnSuccessListener { loc ->
                    if (loc != null) {
                        scope.launch {
                            isLoading = true

                            val response = api.buscarPorCoord(loc.latitude, loc.longitude)
                            val item = response.body()
                            if (item != null) {
                                val geoPoint = GeoPoint(item.lat.toDouble(), item.lon.toDouble())
                                latitude = geoPoint.latitude
                                longitude = geoPoint.longitude
                                mapView?.controller?.setCenter(geoPoint)
                            }

                            isLoading = false
                        }
                    } else {
                        Log.d("LOCALIZAÇÃO", "Não foi possível obter localização (GPS desligado?)")
                    }
                }
            } catch (e: SecurityException) {
                Log.e("LOCALIZAÇÃO", "Permissão negada.")
            }
        }
    }





    val density = LocalDensity.current
    var navBarHeight by remember { mutableStateOf(0.dp) }

    val screenH = LocalConfiguration.current.screenHeightDp.dp

    // peek dinâmico: começa no meio, depois vai para a altura da nav
    var peek by remember { mutableStateOf(screenH * 0.5f) }
    var lockToNavAfterFirstHide by remember { mutableStateOf(true) }

    // vamos recriar o scaffold quando o peek mudar (ancoras novas)
    key(peek) {

        val sheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.PartiallyExpanded, // abre “no meio”
            skipHiddenState = false
        )
        val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
        val scope = rememberCoroutineScope()

        // abre no meio logo que monta (somente quando peek = 50%)
        LaunchedEffect(Unit) {
            if (peek >= screenH * 0.5f - 1.dp && peek <= screenH * 0.5f + 1.dp) {
                sheetState.partialExpand()
            }
        }

        // quando usuário esconder e já sabemos a altura da nav,
        // mudamos o peek para nav e reabrimos em parcial
        LaunchedEffect(navBarHeight, lockToNavAfterFirstHide) {
            if (navBarHeight > 0.dp && lockToNavAfterFirstHide) {
                snapshotFlow { sheetState.currentValue }.collect { v ->
                    if (v == SheetValue.Hidden) {
                        lockToNavAfterFirstHide = false
                        // troca o peek (isso recria o scaffold por causa do key)
                        peek = navBarHeight
                    }
                }
            }
        }

        // toda vez que o peek for mudado para a altura da nav, reabre em parcial
        LaunchedEffect(peek) {
            if (peek == navBarHeight && navBarHeight > 0.dp) {
                sheetState.partialExpand()
            }
        }

        // esconder barras quando expandido (mude para false se quiser sempre visíveis)
        val hideWhenExpanded = true
        val hideChrome by remember {
            derivedStateOf { hideWhenExpanded && sheetState.currentValue == SheetValue.Expanded }
        }

        Box(Modifier.fillMaxSize()) {

            // (1) Mapa + Sheet (embaixo)
            BottomSheetScaffold(
                modifier = Modifier
                    .matchParentSize()
                    .zIndex(1f),
                scaffoldState = scaffoldState,
                sheetPeekHeight = peek,                       // <- PEEK dinâmico (com key)
                sheetSwipeEnabled = true,
                sheetDragHandle = { BottomSheetDefaults.DragHandle() },
                sheetContainerColor = Color.White,
                sheetContent = {
                    // garante que possa expandir até o topo
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .heightIn(min = screenH)
                            .padding(horizontal = 16.dp, vertical = 16.dp)
                    ) {
                        if (unidades.isNotEmpty()) {
                            unidades.forEach { unidade ->
                                CartaoUnidade(
                                    nomeUnidade = unidade.nome ?: "Sem nome"
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                        } else {
                            Text("Nenhuma unidade encontrada", color = Color.Gray)
                        }

                    }
                },
                containerColor = Color.Transparent
            ) { innerPadding ->
                // MAPA (fundo)
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .background(Color(0xFF0035A2))
                ){
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            MapView(context).apply {
                                setTileSource(TileSourceFactory.MAPNIK)
                                setMultiTouchControls(true)
                                controller.setZoom(19.0)
                                controller.setCenter(GeoPoint(latitude, longitude))
                                mapView = this
                            }
                        }
                    )

                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

            }

            // (2) Search bar sobre o sheet
            AnimatedVisibility(
                visible = !hideChrome,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp, start = 10.dp, end = 10.dp)
                    .zIndex(2f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(10f)
                ) {
                    BarraDePesquisaComFiltros(navController = navController)
                }
            }

            // (3) Bottom nav por cima de tudo (mede altura real p/ encaixe)
            AnimatedVisibility(
                visible = !hideChrome,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .zIndex(3f)
                    .onSizeChanged { size ->
                        navBarHeight = with(density) { size.height.toDp() }
                    }
            ) {
                BarraDeNavegacaoCampanha(navController)
            }
        }
    }
}



// ------- SUA NAV BAR (inalterada, apenas referenciada acima) -------
@Composable
fun BarraDeNavegacaoMapa(navController: NavHostController?) {
    NavigationBar(containerColor = Color(0xFF298BE6)) {
        NavigationBarItem(
            selected = false,
            onClick = { navController?.navigate("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = MaterialTheme.colorScheme.onPrimary) },
            label = { Text("Início", color = MaterialTheme.colorScheme.onPrimary) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController?.navigate("mapa") },
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Mapa", tint = MaterialTheme.colorScheme.onPrimary) },
            label = { Text("Mapa", color = MaterialTheme.colorScheme.onPrimary) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController?.navigate("perfil") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil", tint = MaterialTheme.colorScheme.onPrimary) },
            label = { Text("Perfil", color = MaterialTheme.colorScheme.onPrimary) }
        )
    }
}



@Composable
fun CartaoUnidade(nomeUnidade: String) {
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

// ------- PREVIEWS -------
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HomeMapaPreview() {
    Tcc_PasTheme {
        HomeMapa(rememberNavController())
    }
}

@Preview
@Composable
private fun BarraDeNavegacaoMapaPreview() {
    Tcc_PasTheme {
        BarraDeNavegacaoMapa(null)
    }
}






















