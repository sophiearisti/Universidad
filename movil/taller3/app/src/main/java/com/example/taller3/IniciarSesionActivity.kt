package com.example.taller3

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.taller3.databinding.ActivityIniciarSesionBinding
import com.example.taller3.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIniciarSesionBinding
    private lateinit var auth: FirebaseAuth
    private var uid:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //obtener su ubicacion actual
        auth=FirebaseAuth.getInstance()

        uid=intent.getStringExtra("uid").toString()
        Log.i("uid","usuario1 ${intent.getStringExtra("uid").toString()}")

        binding.Register.setOnClickListener {
            startActivity(Intent(this,RegistroActivity::class.java))

        }

        binding.login.setOnClickListener {
            ManejarLogIn();
        }

    }

    fun ManejarLogIn()
    {
        var email=binding.correo.text.toString()
        var password=binding.contrasena.text.toString()

        //revisar si es usuario
        if(forms(email,password)){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                if(it.isSuccessful)
                {
                    updateUI(auth.currentUser)
                }
                else
                {
                    val message = it.exception!!.message
                    Toast.makeText(this, message, Toast.LENGTH_LONG ).show()
                    Log.w(ContentValues.TAG, "signInWithEmailAndPassword:failure", it.exception)
                    binding.correo.text.clear()
                    binding.contrasena.text.clear()
                }
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if(currentUser!=null)
        {
            if(intent.getStringExtra("uid").toString()!="null")
            {
                Log.i("uid2","usuario ${intent.getStringExtra("uid").toString()}")

                val intent = Intent(this, MapaUsuariosActivity::class.java)
                val bundle = Bundle()
                bundle.putString("uid", uid)
                intent.putExtra("uid",uid)
                intent.putExtras(bundle)
                startActivity(intent)

            }
            else
            {
                startActivity(Intent(this, MapaJsonActivity::class.java))
            }

        }

    }

    override fun onResume() {
        super.onResume()
        updateUI(auth.currentUser)
    }

    private fun forms(email: String, password: String): Boolean {

        var valid = false

        if (email.isEmpty()) {
            binding.correo.setError ("Required!")
        } else if (!validEmailAddress(email)) {
            binding.correo.setError("Invalid email address")
        } else if (password.isEmpty()) {
            binding.contrasena.setError("Required!")
        } else if (password.length < 6){
            binding.contrasena.setError("Password should be at least 6 characters long!")
        }else {
            valid = true
        }
        return valid
    }

    private fun validEmailAddress(email: String): Boolean {
        val regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
        return email.matches(regex.toRegex())
    }
}