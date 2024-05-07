package com.example.taller3

import android.app.Notification
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.taller3.Services.Service
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.taller3.databinding.ActivityMapaJsonBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONObject

class MapaJsonActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapaJsonBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private val localPermissionName=android.Manifest.permission.ACCESS_FINE_LOCATION;
    private val notificationpermissionName=android.Manifest.permission.POST_NOTIFICATIONS
    private final val REQUEST_CODE=123
    private val multiplepPermissionNameList= arrayOf(localPermissionName,notificationpermissionName)
    lateinit var location: FusedLocationProviderClient
    private var latActual:Double= 0.0
    private var longActual:Double= 0.0

    val permissionRequestNotificacion = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ActivityResultCallback {
            if(it)
            {
                notificar()
            }
        }
    )

    val permissionRequest= registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ActivityResultCallback {
            if(it)
            {
                locationSettings()
            }
        })

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions.entries.forEach { entry ->
                val permission = entry.key
                val isGranted = entry.value
                // Manejar la respuesta de cada permiso individualmente
                if (permission == android.Manifest.permission.ACCESS_FINE_LOCATION) {
                    if (isGranted) {
                        // El permiso de ubicación fue concedido
                        locationSettings()
                    }
                } else if (permission == android.Manifest.permission.POST_NOTIFICATIONS) {
                    if (isGranted) {
                        // El permiso de notificaciones fue concedido
                        notificar()
                    }
                }
                // Puedes manejar más permisos aquí si es necesario
            }
        }

    val locationSettings= registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult(),
        ActivityResultCallback {
            if(it.
                resultCode ==
                RESULT_OK){

                startLocationUpdates()
            }else{
                Toast.makeText(getApplicationContext(), "GPS TURNED OFF", Toast.LENGTH_LONG).show();
            }
        })

    private fun notificar() {
        if(checkSelfPermission(notificationpermissionName) ==
            PackageManager.PERMISSION_GRANTED) {
            Intent (applicationContext, Service::class.java). apply {
                action = Service.ACTION_START
                putExtra("uid", auth.currentUser?.uid)
                startService(this) // Aquí inicia el servicio
            }
        }
    }

    fun gestionarPermiso() {
         if (ContextCompat.checkSelfPermission(this, notificationpermissionName) == PackageManager.PERMISSION_DENIED|| ContextCompat.checkSelfPermission(this, localPermissionName) == PackageManager.PERMISSION_DENIED) {
            if (shouldShowRequestPermissionRationale(notificationpermissionName)) {
                // Mostrar una explicación al usuario sobre por qué se necesitan los permisos de notificación
                Toast.makeText(applicationContext, "La aplicación necesita permisos para mostrar notificaciones y usar la localizacion", Toast.LENGTH_LONG).show()
            }
            // Solicitar permisos de notificación
             requestMultiplePermissions.launch(multiplepPermissionNameList)
        }
        else if (ContextCompat.checkSelfPermission(this, notificationpermissionName) == PackageManager.PERMISSION_DENIED) {
            if (shouldShowRequestPermissionRationale(notificationpermissionName)) {
                // Mostrar una explicación al usuario sobre por qué se necesitan los permisos de notificación
                Toast.makeText(applicationContext, "La aplicación necesita permisos para mostrar notificaciones", Toast.LENGTH_LONG).show()
            }
            // Solicitar permisos de notificación
            permissionRequestNotificacion.launch(notificationpermissionName)
        }
        else if(ContextCompat.checkSelfPermission(this, localPermissionName) == PackageManager.PERMISSION_DENIED) {
            if(shouldShowRequestPermissionRationale(localPermissionName))
            {
                Toast.makeText(getApplicationContext(), "The app requires access to location", Toast.LENGTH_LONG).show();
            }
            permissionRequest.launch(localPermissionName)
        }
        else {
            // La aplicación ya tiene permisos, mostrar notificaciones
            notificar()
            startLocationUpdates()
        }
    }

    //LOCALIZACION
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapaJsonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        auth=FirebaseAuth.getInstance()

        configurarLocalizacion()

        databaseReference= FirebaseDatabase.getInstance().getReference("Usuarios")
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        //configurarLocalizacion()
        startLocationUpdates()
    }

    override fun onRestart(){
        super.onRestart()
        configurarLocalizacion()
    }

    private fun stopLocationUpdates() {
        location.removeLocationUpdates(locationCallBack)
    }

    private fun configurarLocalizacion() {

        location= LocationServices.getFusedLocationProviderClient(this);
        locationRequest=createLocationRequest()
        locationCallBack=createLocationCallback()

        //primero gestionar los permisos
        gestionarPermiso()

    }

    private fun createLocationRequest():LocationRequest
    {
        val request=LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 7000)
            .setMinUpdateIntervalMillis(2000)
            .setWaitForAccurateLocation(true)
            .build()

        return request
    }

    private var MarkerActual: Marker? = null
    private var firstTime=true
    private  fun createLocationCallback():LocationCallback
    {
        val locationCallback=object: LocationCallback()//clase anonima en kotlin
        //heredar y sobreescribir sobre la misma linea
        {
            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                val last=result.lastLocation
                if(last!=null)
                {
                    //Toast.makeText(getApplicationContext(), "($last.latitude , $last.longitude)", Toast.LENGTH_LONG).show();

                    latActual=last.latitude
                    longActual=last.longitude
                    auth.currentUser?.uid?.let { userId ->
                        databaseReference.child(userId).apply {
                            child("latitud").setValue(latActual)
                            child("longitud").setValue(longActual)
                        }
                    }
                    var pos=LatLng(latActual,longActual)
                    MarkerActual?.remove()
                    MarkerActual=mMap.addMarker(MarkerOptions().position(pos).title("YO"))

                    if(firstTime)
                    {
                        firstTime=false
                        val zoomLevel = 15.0f // Puedes ajustar este valor según sea necesario
                        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(pos, zoomLevel)
                        mMap.moveCamera(cameraUpdate)
                        firstTime=false
                    }
                }
            }
        }

        return locationCallback
    }


    fun startLocationUpdates()
    {
        if(ActivityCompat.checkSelfPermission(this, localPermissionName)== PackageManager.PERMISSION_GRANTED)
        {
            location.requestLocationUpdates(locationRequest,locationCallBack, Looper.getMainLooper())

            //PARA PONER LA POSICION INICIAL DEL USUARIO
            location.lastLocation.addOnSuccessListener {
                if (it != null) {
                    latActual=it.latitude
                    longActual=it.longitude
                    //auth.currentUser?.uid?.let { databaseReference.child(it).child("latitud").setValue(latActual)}
                    //auth.currentUser?.uid?.let { databaseReference.child(it).child("latitud").setValue(longActual)}

                }
            }
        }
        else
        {
            //Toast.makeText(getApplicationContext(), "NO HAY PERMISO", Toast.LENGTH_LONG).show();
        }
    }


    //evaluar el gps . si esta prendido o no no existe
    fun locationSettings()
    {
        val builder= LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(this)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            startLocationUpdates()
        }
        task.addOnFailureListener{
            if(it is ResolvableApiException)
            {
                try{
                    val isr: IntentSenderRequest = IntentSenderRequest.Builder(it.resolution).build()
                    locationSettings.launch(isr)
                }
                catch (sendEx: IntentSender.SendIntentException)
                {
                    //ignore the error
                }

            }
            else
            {
                Toast.makeText(getApplicationContext(), "there is no gps hardware", Toast.LENGTH_LONG).show();
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        ponerOtrasUbicaciones()
        configurarLocalizacion()
        var pos=LatLng(latActual,longActual)
        val zoomLevel = 15.0f // Puedes ajustar este valor según sea necesario
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(pos, zoomLevel)
        mMap.moveCamera(cameraUpdate)
    }

    private fun ponerOtrasUbicaciones() {
        // Leer el JSON
        val json_string = this.assets.open("locations.json").bufferedReader().use {
            it.readText()
        }

        // Convertir el JSON a un objeto JSONObject
        val json = JSONObject(json_string)

        // Obtener el objeto JSON "locations"
        val locationsJsonObject = json.getJSONObject("locations")

        // Iterar sobre las claves del objeto "locations" para obtener los objetos de ubicación y agregar marcadores al mapa
        val keys = locationsJsonObject.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val jsonObject = locationsJsonObject.getJSONObject(key)
            val lat = jsonObject.getDouble("latitude")
            val long = jsonObject.getDouble("longitude")
            val nombre = jsonObject.getString("name")
            val pos = LatLng(lat, long)
            mMap.addMarker(MarkerOptions().position(pos).title(nombre))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val clicked = item.itemId
        if(clicked == R.id.signout){
            stopLocationUpdates()
            auth.signOut()
            val i = Intent(this, IniciarSesionActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        }
        else if (clicked == R.id.disponible){
            //poner disponible en firebase al usuario (cambiarle ese atributo)
            //poner disponible en firebase al usuario (cambiarle ese atributo)
            auth.currentUser?.uid?.let { databaseReference.child(it).child("disponible").setValue(true)
                Toast.makeText(this,"Estado cambiado a disponible", Toast.LENGTH_LONG ).show()
            }
        }
        else if (clicked == R.id.Listar){
            startActivity(Intent(this, ListaUsuariosActivity::class.java))
        }

        return super.onOptionsItemSelected(item)
    }
}