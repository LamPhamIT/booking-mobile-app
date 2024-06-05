package com.example.tanlam.ui.ingredients

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.tanlam.R
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


@Composable
fun MapUI(
    mapView: MapView,
    context: Context,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AndroidView({ mapView }) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()
                map.uiSettings.isZoomControlsEnabled = true
            }
        }
    }
}

//fun getDistance(mapView: MapView, context: Context) {
//    mapView.getMapAsync {
//        val Sydney = LatLng(-34.0, 151.0)
//        val Brisbane = LatLng(-27.470125, 153.021072)
//
//        val locationA: Location = Location("Sydney")
//        locationA.latitude = Sydney.latitude
//        locationA.longitude = Sydney.longitude
//
//        val locationB: Location = Location("Brisbane")
//        locationB.latitude = Brisbane.latitude
//        locationB.longitude = Brisbane.longitude
//
//        var distance: Float = locationA.distanceTo(locationB)
//        distance /= 1000
//
//        Toast.makeText(
//            context,
//            "Distance between Sydney and Brisbane is : " + distance + "km",
//            Toast.LENGTH_SHORT
//        ).show()
//
//        val locationArrayList: ArrayList<LatLng?> = ArrayList()
//        locationArrayList.add(Sydney)
//        locationArrayList.add(Brisbane)
//
//        for (i in locationArrayList.indices) {
//            it.addMarker(MarkerOptions().position(locationArrayList[i]!!).title("Marker"))
//            it.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
//            it.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList[i]!!))
//        }
//    }
//}

fun getMapLocation(
    location1: String,
    location2: String,
    context: Context,
    mapView: MapView,
    getKm: (Double) -> Unit
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
                val testAddress = test[0]

                val latLng = LatLng(address.latitude, address.longitude)
                val latLng2 = LatLng(testAddress.latitude, testAddress.longitude)

                val locationA = Location(location1)
                locationA.latitude = latLng.latitude
                locationA.longitude = latLng.longitude

                val locationB = Location(location2)
                locationB.latitude = latLng2.latitude
                locationB.longitude = latLng2.longitude

                var distance: Double = locationA.distanceTo(locationB).toDouble()
                distance /= 1000

                googleMap.clear()

                googleMap.addMarker(
                    MarkerOptions().position(latLng).title("$location1")
                )
                googleMap.addMarker(
                    MarkerOptions().position(latLng2).title("$location2")
                )
                googleMap.addPolyline(
                    PolylineOptions()
                        .add(latLng, latLng2)
                )

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))

                getKm(distance)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}

@Composable
fun rememberMapViewWithLifecycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map
        }
    }

    val lifecycleObserver = rememberMapLifecycleObserver(mapView)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    DisposableEffect(lifecycle) {
        lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycle.removeObserver(lifecycleObserver)
        }
    }
    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }