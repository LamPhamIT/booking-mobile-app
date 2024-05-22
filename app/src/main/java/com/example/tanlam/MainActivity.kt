package com.example.tanlam

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tanlam.controller.viewmodel.DataViewModel
import com.example.tanlam.nav.NavGraph
import com.example.tanlam.theme.TanLamTheme
import com.example.tanlam.ui.screens.login.SelectScreen
import com.example.tanlam.ui.screens.login.SignupScreen
import com.example.tanlam.ui.screens.main.MapScreen
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private val dataViewModel: DataViewModel by viewModels()
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            val context = LocalContext.current
            TanLamTheme {
                Scaffold {
                    val i = it
                    NavGraph(
                        dataViewModel = dataViewModel,
                        navController = navController
                    )
//                    MapScreen()
                }
            }
        }
    }
}

@Composable
fun mapUI(
    context: Context,
    modifier: Modifier = Modifier
) {
    val mapView = rememberMapViewWithLifecycle()
    var locationName1 by remember { mutableStateOf("") }
    var locationName2 by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//            TextField(
//                value = locationName1,
//                onValueChange = { locationName1 = it },
//                placeholder = { Text(text = "your location") },
//                modifier = Modifier
//                    .padding(3.dp)
//                    .fillMaxWidth()
//                    .height(60.dp),
//                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//                keyboardActions = KeyboardActions(
//                    onSearch = {
//                        getMapLocation(locationName1, locationName2, context, mapView)
//                    }
//                )
//            )
//
//            TextField(
//                value = locationName2,
//                onValueChange = { locationName2 = it },
//                placeholder = { Text(text = "your destination") },
//                modifier = Modifier
//                    .padding(3.dp)
//                    .fillMaxWidth()
//                    .height(60.dp),
//                textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
//                singleLine = true,
//                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
//                keyboardActions = KeyboardActions(
//                    onSearch = {
//                        getMapLocation(locationName1, locationName2, context, mapView)
//                    }
//                )
//            )
        }
        AndroidView({ mapView }) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()
                map.uiSettings.isZoomControlsEnabled = true
            }
        }
    }
}


private fun getMapLocation(
    location1: String,
    location2: String,
    context: Context,
    mapView: MapView
) {
    mapView.getMapAsync { googleMap ->
        if (location1.isNotEmpty() && location2.isNotEmpty()) {
            val geocoder = Geocoder(context)
            try {
                val addressList = geocoder.getFromLocationName(location1, 1)
                val test = geocoder.getFromLocationName(location2, 1)
                if (addressList.isNullOrEmpty() || test.isNullOrEmpty()) {
                    // Handle the case where no address is found
                    return@getMapAsync
                }
                val address = addressList[0]
                val testAdress = test[0]
                val latLng = LatLng(address.latitude, address.longitude)
                val latLng2 = LatLng(testAdress.latitude, testAdress.longitude)
                googleMap.addMarker(
                    MarkerOptions().position(latLng).title("Marker in $location1")
                )
                googleMap.addMarker(
                    MarkerOptions().position(latLng2).title("Marker in $location2")
                )
                googleMap.addPolyline(
                    PolylineOptions()
                        .add(latLng, latLng2)
                )
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
