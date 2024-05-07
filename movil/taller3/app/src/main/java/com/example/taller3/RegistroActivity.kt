package com.example.taller3

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.example.taller3.clases.Usuario
import com.example.taller3.databinding.ActivityRegistroBinding
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
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.util.Date

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var dialog: Dialog

    private val localPermissionName=android.Manifest.permission.ACCESS_FINE_LOCATION;
    lateinit var location: FusedLocationProviderClient
    private var latActual:Double= 0.0
    private var longActual:Double= 0.0

    val getContentGallery = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        ActivityResultCallback {
            if (it != null) {
                loadImage(it)
            }
        })

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

    //LOCALIZACION
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallBack: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtener su ubicacion actual

        auth=FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().getReference("Usuarios")

        configurarLocalizacion()

        binding.registrarse.setOnClickListener {
            showProgressBar()
            registerUser()
        }

        binding.seleccionarFoto.setOnClickListener {
            getContentGallery.launch("image/*")
        }
    }

    override fun onStop() {
        super.onStop()
        stopLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
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

        val client: SettingsClient =LocationServices.getSettingsClient(this)
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

    private fun registerUser() {
        var email=binding.correoRegister.text.toString()
        var password=binding.contrasenaRegister.text.toString()
        var nombre=binding.nombre.text.toString()
        var apellido=binding.apellido.text.toString()
        var id=binding.id.text.toString()


        if(forms(email,password,nombre,apellido,id)){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if(it.isSuccessful)
                {//guardar la otra informacion en la bdd de tiempo real
                    val idUsuario=auth.currentUser?.uid
                    val drawableFoto = binding.fotoUsuario.drawable
                    val usuario= Usuario(email,nombre,apellido,id.toInt(),latActual,longActual,idUsuario,"Usuarios/"+auth.currentUser?.uid, false)

                    if(idUsuario!=null)
                    {
                        databaseReference.child(idUsuario).setValue(usuario).addOnCompleteListener {

                            if(it.isSuccessful)
                            {
                                uploadFotoPerfil()
                            }
                            else
                            {
                                hideProgressBar()
                                Toast.makeText(this, "Fallo en guardar la informacion del usuario", Toast.LENGTH_LONG ).show()

                            }
                        }
                    }
                }
                else
                {
                    hideProgressBar()
                    val message = it.exception!!.message
                    Toast.makeText(this, message, Toast.LENGTH_LONG ).show()
                    Log.w(ContentValues.TAG, "Error para registrar el usuario", it.exception)
                    binding.correoRegister.text.clear()
                    binding.contrasenaRegister.text.clear()
                    binding.nombre.text.clear()
                    binding.apellido.text.clear()
                    binding.id.text.clear()
                }
            }
        }
    }

    private fun forms(email: String, password: String,nombre: String,apellido: String, id: String): Boolean {

        var valid = false

        if (email.isEmpty()) {
            binding.correoRegister.setError ("Required!")
        } else if (!validEmailAddress(email)) {
            binding.correoRegister.setError("correo invalido")
        } else if (password.isEmpty()) {
            binding.contrasenaRegister.setError("Requerido!")
        } else if (password.length < 6){
            binding.contrasenaRegister.setError("la contrasena debe ser por lo menos de 6 caracteres!")
        } else if(nombre.isEmpty()) {
            binding.nombre.setError("Debe poner nombre")
        } else if(apellido.isEmpty()) {
            binding.apellido.setError("Debe poner apellido")
        }else if(id.isEmpty()) {
            binding.apellido.setError("Debe poner id")
        }
        else {
            valid = true
        }
        return valid
    }

    private fun validEmailAddress(email: String): Boolean {
        val regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return email.matches(regex.toRegex())
    }

    private fun loadImage(uri : Uri?) {
        val imageStream = getContentResolver().openInputStream(uri!!)
        val bitmap = BitmapFactory.decodeStream(imageStream)
        binding.fotoUsuario.setImageBitmap(bitmap)
    }

    private fun uploadFotoPerfil() {
        var imageUri: Uri? = null

        val drawableFoto = binding.fotoUsuario.drawable
        //var imgUrlplan: String? =null

        storageReference=FirebaseStorage.getInstance().getReference("Usuarios/"+auth.currentUser?.uid)

        if (drawableFoto != null) {
            if (drawableFoto is BitmapDrawable) {
                // Si el Drawable es un BitmapDrawable, puedes obtener el Bitmap y luego su URI
                val bitmap = drawableFoto.bitmap
                imageUri = bitmapToUri(this, bitmap)
                storageReference.putFile(imageUri).addOnSuccessListener {
                    hideProgressBar()
                    Toast.makeText(this, "Usuario guardado correctamente", Toast.LENGTH_LONG ).show()
                    startActivity(Intent(this, MapaJsonActivity::class.java))
                }.addOnFailureListener()
                {
                    hideProgressBar()
                    Toast.makeText(this, "Fallo en guardar la informacion del usuario", Toast.LENGTH_LONG ).show()
                }

            }
        }
    }

    private fun bitmapToUri(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(path)
    }

    private  fun showProgressBar()
    {
        dialog= Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialogue_wait)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    private  fun hideProgressBar()
    {
        dialog.dismiss()
    }
}

