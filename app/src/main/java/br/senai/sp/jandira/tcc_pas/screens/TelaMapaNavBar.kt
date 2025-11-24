package br.senai.sp.jandira.tcc_pas.screens

import android.Manifest
import android.R.attr.onClick
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
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
import br.senai.sp.jandira.tcc_pas.map.CustomInfoWindow
import br.senai.sp.jandira.tcc_pas.model.Filtros
import br.senai.sp.jandira.tcc_pas.model.UnidadeDeSaude
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryCampanha
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryFiltrar
import br.senai.sp.jandira.tcc_pas.service.RetrofitFactoryOSM
import br.senai.sp.jandira.tcc_pas.ui.theme.Tcc_PasTheme
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaMapaNavBar(navController: NavHostController) {

    val unidadeGeoMap = remember { mutableStateMapOf<String, GeoPoint>() }

    val api = RetrofitFactoryOSM().getOSMService()

    val context = LocalContext.current

    // osmdroid
    var latitude by remember { mutableStateOf(0.0) }
    var longitude by remember { mutableStateOf(0.0) }

    var mapView: MapView? by remember { mutableStateOf(null) }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    // cria o cliente de localização
    val fusedClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // controla se o usuário já deu acesso à localização
    val locationPermissionGranted = remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted -> locationPermissionGranted.value = granted }

    // pede permissão de usar localizacao assim que o composable é carregado
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

                            // Primeiro, coloca o marker do usuário
                            val userGeo = GeoPoint(loc.latitude, loc.longitude)
                            mapView?.controller?.setCenter(userGeo)

                            val userMarker = Marker(mapView).apply {
                                position = userGeo
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                title = "Você está aqui"
                                icon = ContextCompat.getDrawable(context, R.drawable.marker)
                                setInfoWindowAnchor(Marker.ANCHOR_CENTER, -0.1f)
                                infoWindow = CustomInfoWindow(R.layout.custom_infowindow, mapView!!)
                            }
                            mapView?.overlays?.add(userMarker)

                            // Agora busca todas as unidades da API
                            val apiUnidades = RetrofitFactoryFiltrar().getUnidadesService()
                            val response = apiUnidades.filtrarUnidades(Filtros(categoria = null,
                                especialidade = null,
                                disponibilidade24h = null)) // ou o endpoint que retorna todas
                            val todasUnidades = response.body()?.unidadesDeSaude?.flatten().orEmpty()

                            // Adiciona cada unidade como marker
                            todasUnidades.forEach { unidade ->
                                val endereco = unidade.local.endereco.firstOrNull()
                                if (endereco != null) {
                                    val unidadeGeo = GeoPoint(endereco.latitude.toDouble(), endereco.longitude.toDouble())
                                    val marker = Marker(mapView).apply {
                                        position = unidadeGeo
                                        setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                                        title = unidade.nome
                                        icon = ContextCompat.getDrawable(context, R.drawable.marker)
                                        setInfoWindowAnchor(Marker.ANCHOR_CENTER, -0.1f)
                                        infoWindow = CustomInfoWindow(R.layout.custom_infowindow, mapView!!)
                                    }
                                    mapView?.overlays?.add(marker)
                                }
                            }

                            isLoading = false
                        }
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

    Scaffold(
        bottomBar = { BarraDeNavegacaoMapanav(navController = navController) }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(10f)
            ) {
                BarraDePesquisaComFiltros(navController = navController)
            }

            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0035A2))
            ) {
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
    }
}



@Composable
fun BarraDeNavegacaoMapanav(navController: NavHostController?) {
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




















