package com.jessicaamadearahma.mybusinessproducts.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.jessicaamadearahma.mybusinessproducts.R
import com.jessicaamadearahma.mybusinessproducts.databinding.ActivityLocationBinding

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding : ActivityLocationBinding
    private lateinit var mMap: GoogleMap
    private lateinit var geofencingClient: GeofencingClient
    private val locationLat = -7.56828
    private val LocationLng = 110.81901
    private val geofenceRadius = 2000.0

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(this, GeofenceBroadcastReceiver::class.java)
        intent.action = GeofenceBroadcastReceiver.ACTION_GEOFENCE_EVENT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_MUTABLE) }
        else { PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT) }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.geofenceMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        val kirimJandT = LatLng(locationLat, LocationLng)
        mMap.addMarker(
            MarkerOptions()
                .position(kirimJandT)
                .title("Tempat Pengiriman J&T")
                .snippet("Disiniii... pada Latitude: $locationLat, Longitude: $LocationLng")
        )?.showInfoWindow()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kirimJandT, 15f))

        mMap.addCircle(
            CircleOptions()
                .center(kirimJandT)
                .radius(geofenceRadius)
                .fillColor(0x22FF0000)
                .strokeColor(Color.CYAN)
                .strokeWidth(3f)
        )
        getMyLocation()

        geofencingClient = LocationServices.getGeofencingClient(this)
        val geofence = Geofence.Builder()
            .setRequestId("")
            .setCircularRegion(locationLat, LocationLng, geofenceRadius.toFloat())
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_ENTER)
            .setLoiteringDelay(5000)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        geofencingClient.removeGeofences(geofencePendingIntent).run {
            addOnCompleteListener {
                geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
                    addOnSuccessListener { Snackbar.make(binding.root, getString(R.string.geofencing_success), Snackbar.LENGTH_SHORT).show() }
                    addOnFailureListener { Snackbar.make(binding.root, getString(R.string.geofencing_error) +"${it.message}",Snackbar.LENGTH_SHORT).show() }
                }
            }
        }
    }

    private fun checkPermission(permission: String): Boolean { return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED }
    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        if (checkForegroundAndBackgroundLocationPermission()) { mMap.isMyLocationEnabled = true }
        else { requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
    }

    private val requestBackgroundLocationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) { getMyLocation()}
    }

    @TargetApi(Build.VERSION_CODES.Q)
    private val requestLocationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { requestBackgroundLocationPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION) }
            else { getMyLocation() }
        }
    }
    @TargetApi(Build.VERSION_CODES.Q)
    private fun checkForegroundAndBackgroundLocationPermission(): Boolean {
        val foregroundLocationApproved = checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val backgroundPermissionApproved = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION) } else { true }
        return foregroundLocationApproved && backgroundPermissionApproved
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}