package com.example.taller3

import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import com.example.taller3.clases.Usuario
import com.example.taller3.databinding.ActivityMapaUsuariosBinding
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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import com.google.android.play.integrity.internal.i
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt


class MapaUsuariosActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapaUsuariosBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database : FirebaseDatabase

    private val localPermissionName=android.Manifest.permission.ACCESS_FINE_LOCATION;
    lateinit var location: FusedLocationProviderClient
    private var latActual:Double= 0.0
    private var longActual:Double= 0.0
    private var latActualOtro:Double= 0.0
    private var longActualOtro:Double= 0.0
    private var uidOtro=""

    val permissionRequest= registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ActivityResultCallback {
            if(it)
            {
                locationSettings()
            }
        })

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

    override fun onRestart(){
        super.onRestart()
        configurarLocalizacion()
    }

    //LOCALIZACION
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapaUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtener informacion del UID del otro usuario
        //aqui obtengo informacion del usuario que se selecciono
        //tambien uso la locaclizacion actual del usuario en cuestion y voy actualizando su informacion
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        uidOtro= intent.getStringExtra("uid").toString()
        databaseReference= FirebaseDatabase.getInstance().getReference("Usuarios")
        setSupportActionBar(toolbar)
        auth=FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
                    Log.i("posyo"," pos: $latActual,$longActual")
                    MarkerActual?.remove()
                    MarkerActual=mMap.addMarker(MarkerOptions().position(pos).title("yo"))

                    binding.distanciasText.text=calcularUbicacion(latActualOtro,longActualOtro).toString();

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


    fun gestionarPermiso()
    {
        if(ActivityCompat.checkSelfPermission(this, localPermissionName)== PackageManager.PERMISSION_DENIED)
        {
            if(shouldShowRequestPermissionRationale(localPermissionName))
            {
                Toast.makeText(getApplicationContext(), "The app requires access to location", Toast.LENGTH_LONG).show();
            }
            permissionRequest.launch(localPermissionName)
        }
        else
        {

            startLocationUpdates()
        }
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
                    auth.currentUser?.uid?.let { databaseReference.child(it).child("latitud").setValue(latActual)}
                    auth.currentUser?.uid?.let { databaseReference.child(it).child("longitud").setValue(longActual)}
                    binding.distanciasText.text=calcularUbicacion(latActualOtro,longActualOtro).toString();
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
        obtenerDatosInicialesUsuarioOtro()
        subscribirseACambiosDelUsuario()
        configurarLocalizacion()
        var pos=LatLng(latActual,longActual)
        val zoomLevel = 15.0f // Puedes ajustar este valor según sea necesario
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(pos, zoomLevel)
        mMap.moveCamera(cameraUpdate)

        binding.yo.setOnClickListener {
            var posYo=LatLng(latActual,longActual)
            val zoomLevel = 15.0f // Puedes ajustar este valor según sea necesario
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(posYo, zoomLevel)
            mMap.moveCamera(cameraUpdate)
        }

        binding.otro.setOnClickListener {
            var posOtro=LatLng(latActualOtro,longActualOtro)
            val zoomLevel = 15.0f // Puedes ajustar este valor según sea necesario
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(posOtro, zoomLevel)
            mMap.moveCamera(cameraUpdate)
        }
    }

    private var MarkerActualOtro: Marker? = null

    private fun obtenerDatosInicialesUsuarioOtro() {
        val usuarioRef = database.getReference("Usuarios").child(uidOtro)

        usuarioRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Aquí puedes obtener los datos del usuario desde dataSnapshot
                val usuario = dataSnapshot.getValue(Usuario::class.java)
                if (usuario != null) {
                    // Haz lo que necesites con los datos del usuario
                    latActualOtro=usuario.latitud
                    longActualOtro=usuario.longitud
                    var pos=LatLng(latActualOtro,longActualOtro)
                    MarkerActualOtro?.remove()
                    MarkerActualOtro=mMap.addMarker(MarkerOptions().position(pos).title(usuario.nombre))
                    binding.distanciasText.text=calcularUbicacion(latActualOtro,longActualOtro).toString();
                } else {
                    println("No se encontraron datos para el usuario con UID: $uidOtro")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Maneja el error en caso de que ocurra
                println("Error al obtener los datos del usuario: ${databaseError.message}")
            }
        })
    }
    private fun subscribirseACambiosDelUsuario() {
        databaseReference = database.getReference("Usuarios")

        databaseReference.child(uidOtro)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, prevChildKey: String?) {

                }
                override fun onChildChanged(dataSnapshot: DataSnapshot, prevChildKey: String?) {

                    Log.i("snapshot"," pos: $dataSnapshot")
                    if(dataSnapshot.key=="latitud")
                    {
                        latActualOtro= dataSnapshot.value as Double;
                        obtenerOtrosDatosUsuario()
                    }
                    else if (dataSnapshot.key=="longitd")
                    {
                        longActualOtro= dataSnapshot.value as Double;
                        obtenerOtrosDatosUsuario2()
                    }
                    else {
                        println("No se encontraron datos para el usuario con UID: $uidOtro")
                    }
                }
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                }
                override fun onChildMoved(dataSnapshot: DataSnapshot, prevChildKey: String?) {
                }
                override fun onCancelled(databaseError: DatabaseError) {
                }
            })
    }

    private fun obtenerOtrosDatosUsuario() {
        // Realiza la consulta para obtener otros datos del usuario usando su UID
        // Por ejemplo, podrías usar una referencia a la base de datos para buscar los datos
        val usuarioRef = database.getReference("Usuarios").child(uidOtro)

        usuarioRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Aquí puedes obtener los datos del usuario desde dataSnapshot
                val usuario = dataSnapshot.getValue(Usuario::class.java)
                if (usuario != null) {
                    // Haz lo que necesites con los datos del usuario

                    longActualOtro=usuario.longitud

                    var pos=LatLng(latActualOtro,longActualOtro)
                    MarkerActualOtro?.remove()
                    MarkerActualOtro=mMap.addMarker(MarkerOptions().position(pos).title("otro usuario"))
                    binding.distanciasText.text=calcularUbicacion(latActualOtro,longActualOtro).toString();
                    Log.i("posotro"," pos: $latActualOtro,$longActualOtro")
                } else {
                    println("No se encontraron datos para el usuario con UID: $uidOtro")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Maneja el error en caso de que ocurra
                println("Error al obtener los datos del usuario: ${databaseError.message}")
            }
        })
    }

    private fun obtenerOtrosDatosUsuario2() {
        // Realiza la consulta para obtener otros datos del usuario usando su UID
        // Por ejemplo, podrías usar una referencia a la base de datos para buscar los datos
        val usuarioRef = database.getReference("Usuarios").child(uidOtro)

        usuarioRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Aquí puedes obtener los datos del usuario desde dataSnapshot
                val usuario = dataSnapshot.getValue(Usuario::class.java)
                if (usuario != null) {
                    // Haz lo que necesites con los datos del usuario

                    latActualOtro=usuario.latitud
                    var pos=LatLng(latActualOtro,longActualOtro)
                    MarkerActualOtro?.remove()
                    MarkerActualOtro=mMap.addMarker(MarkerOptions().position(pos).title("otro usuario"))
                    binding.distanciasText.text=calcularUbicacion(latActualOtro,longActualOtro).toString();
                    Log.i("posotro"," pos: $latActualOtro,$longActualOtro")
                } else {
                    println("No se encontraron datos para el usuario con UID: $uidOtro")
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Maneja el error en caso de que ocurra
                println("Error al obtener los datos del usuario: ${databaseError.message}")
            }
        })
    }

    fun calcularUbicacion(latitudOrigen: Double, longitudOrigen: Double): Double
    {

        val RADIUS_OF_EARTH_KM=6378

        val latDistance = Math.toRadians(latitudOrigen - latActual)
        val lngDistance = Math.toRadians(longitudOrigen - longActual)
        val a = sin(latDistance / 2) * sin(latDistance / 2) +
                cos(Math.toRadians(latitudOrigen)) * cos(Math.toRadians(latActual)) *
                sin(lngDistance / 2) * sin(lngDistance / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a));

        val result = RADIUS_OF_EARTH_KM * c;
        return round(result*100.0) /100.0;

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