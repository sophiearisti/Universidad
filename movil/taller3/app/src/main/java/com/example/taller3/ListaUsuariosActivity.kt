package com.example.taller3

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.taller3.adapters.AdapterUsuario
import com.example.taller3.clases.Usuario
import com.example.taller3.clases.UsuarioCelda
import com.example.taller3.databinding.ActivityListaUsuariosBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File


class ListaUsuariosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaUsuariosBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        auth=FirebaseAuth.getInstance()

        llenarLista()

    }
    val listaUsuariosDisponibles: MutableList<UsuarioCelda> = mutableListOf()

    private fun llenarLista() {

        databaseReference = FirebaseDatabase.getInstance().getReference("Usuarios")

        auth.currentUser?.uid?.let { currentUserUid ->
            databaseReference.orderByChild("disponible").equalTo(true)
                .addChildEventListener(object : ChildEventListener {
                    override fun onChildAdded(dataSnapshot: DataSnapshot, prevChildKey: String?) {

                        // Obtener el usuario de dataSnapshot
                        val usuario = dataSnapshot.getValue(Usuario::class.java)

                        // Verificar si el usuario no es el usuario actual antes de agregarlo a la lista
                        if (usuario != null && dataSnapshot.key != currentUserUid) {

                            val storageRef = FirebaseStorage.getInstance().reference.child(usuario.imagenDir)

                            val localfile = File. createTempFile( "tempImage", "jpg")

                            storageRef.getFile(localfile).addOnSuccessListener {
                                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                                var usuarioADD=UsuarioCelda(usuario.nombre,usuario.apellido,usuario.uid,bitmap)
                                listaUsuariosDisponibles.add(usuarioADD)
                                //ponerlo en la lista
                                val adapter = AdapterUsuario(applicationContext,listaUsuariosDisponibles);
                                binding.listaUsuarios.adapter = adapter
                                //clickLista()
                            }.addOnFailureListener{
                                Log.i("revisar", "no se pudo poner al usuario")
                            }

                        }
                    }
                    override fun onChildChanged(dataSnapshot: DataSnapshot, prevChildKey: String?) {

                    }
                    override fun onChildRemoved(dataSnapshot: DataSnapshot) {

                    }
                    override fun onChildMoved(dataSnapshot: DataSnapshot, prevChildKey: String?) {
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                    }
                })
        }

    }

    private fun clickLista() {

        binding.listaUsuarios.setOnItemClickListener { parent, view, position, id ->
            Log.i("revisar", "si estoy")

            val selectedUsuario = listaUsuariosDisponibles[position] // Obtiene el objeto Pais seleccionado
            val intent = Intent(baseContext, MapaUsuariosActivity::class.java)

            val bundle = Bundle()
            bundle.putString("uid",selectedUsuario.uid)

            // Pasa el objeto Pais como un extra del Intent
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val clicked = item.itemId
        if(clicked == R.id.signout){
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