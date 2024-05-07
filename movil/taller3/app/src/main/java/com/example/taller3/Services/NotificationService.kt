package com.example.tall

import android.annotation.SuppressLint
import android.app.PendingIntent
import com.example.taller3.MapaUsuariosActivity

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.taller3.IniciarSesionActivity
import com.example.taller3.R
import com.example.taller3.clases.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.util.UUID

class NotificationService : Service() {
    private val serviceScope = CoroutineScope(SupervisorJob())
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database : FirebaseDatabase

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate ()

        databaseReference= FirebaseDatabase.getInstance().getReference("Usuarios")
        auth=FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
    }

    private val usuariosNoDisponibles = HashSet<String>()

    private fun subscribirseACambiosDelUsuario() {
        databaseReference = database.getReference("Usuarios")
        val query = databaseReference

        query.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, prevChildKey: String?) {
                val usuario = dataSnapshot.getValue(Usuario::class.java)

                // Agregar usuario a la lista si está disponible y no está ya en la lista
                if (usuario != null && !usuario.disponible) {
                    usuario.uid?.let { usuariosNoDisponibles.add(it) }
                    Log.i("entre22", "Usuario no disponible agregado: $usuario")
                }
            }

            @RequiresApi(Build.VERSION_CODES.Q)
            @SuppressLint("ForegroundServiceType")
            override fun onChildChanged(dataSnapshot: DataSnapshot, prevChildKey: String?) {
                val usuario = dataSnapshot.getValue(Usuario::class.java)

                if (usuario != null) {
                    if (usuario.disponible) {
                        if (usuariosNoDisponibles.contains(usuario.uid)) {
                            usuariosNoDisponibles.remove(usuario.uid)
                            Log.i("entre22", "Usuario disponible ahora: $usuario")

                            val notificationId = UUID.randomUUID().hashCode()
                            //se llama a la notificacion
                            val notification = NotificationCompat. Builder (applicationContext,"disponible")
                                .setContentTitle("Nuevo usuario disponible")
                                .setContentText("Usuario ${usuario.nombre} ${usuario.apellido} ahora esta disponible")
                                .setSmallIcon(R.drawable. ic_launcher_background)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setAutoCancel(true)

                            //revisar cual abrir
                            if(auth.currentUser!=null){
                                val mapIntent=Intent(applicationContext,MapaUsuariosActivity::class.java)
                                mapIntent.putExtra("uid",usuario.uid)
                                mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                val mapPendingIntent = PendingIntent.getActivity( applicationContext, 1, mapIntent,
                                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
                                notification.addAction(R.drawable. ic_launcher_background,"ver ubicacion", mapPendingIntent)
                                //notification.setContentIntent(mapPendingIntent)
                            }
                            else
                            {
                                val signIntent=Intent(applicationContext,IniciarSesionActivity::class.java)
                                signIntent.putExtra("uid",usuario.uid)
                                signIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                val signPendingIntent = PendingIntent.getActivity( applicationContext, 1, signIntent,
                                    PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
                                notification.addAction(R.drawable. ic_launcher_background,"ver ubicacion", signPendingIntent)
                                //notification.setContentIntent(signPendingIntent)
                            }

                            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                            notificationManager.notify(notificationId, notification.build())

                            startForeground(notificationId, notification.build(), ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE);
                        }
                    } else {
                        if (!usuariosNoDisponibles.contains(usuario.uid)) {
                            usuario.uid?.let { usuariosNoDisponibles.add(it) }
                            Log.i("entre22", "Usuario no disponible ahora: $usuario")
                        }
                    }
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


    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }

    override fun onStartCommand (intent: Intent?, flags: Int, startId: Int): Int{

        when(intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand (intent, flags, startId)
    }

    private  fun start()
    {
        databaseReference= FirebaseDatabase.getInstance().getReference("Usuarios")
        auth=FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        subscribirseACambiosDelUsuario()
    }

    private  fun stop()
    {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super .onDestroy()
        serviceScope.cancel()
    }

}
