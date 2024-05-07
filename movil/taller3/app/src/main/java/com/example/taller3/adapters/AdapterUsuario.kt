package com.example.taller3.adapters
import com.example.taller3.R.layout.celda_usuario_disponible
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.taller3.MapaUsuariosActivity
import com.example.taller3.R
import com.example.taller3.clases.Usuario
import com.example.taller3.clases.UsuarioCelda

class AdapterUsuario (context: Context, usuarioList: MutableList<UsuarioCelda>) : ArrayAdapter<UsuarioCelda>(context, celda_usuario_disponible, usuarioList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val usuario = getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.celda_usuario_disponible, parent, false)
        }

        val tv = convertView!!.findViewById<TextView>(R.id.nombreUsuario)
        val iv = convertView.findViewById<ImageView>(R.id.imgUsuario)
        val btn = convertView.findViewById<Button>(R.id.posActualButton)

        usuario?.let { usuarioCelda ->
            tv.text = usuarioCelda.nombre + " " + usuarioCelda.apellido
            // Poner la imagen correspondiente
            iv.setImageBitmap(usuarioCelda.imagenDir)

            // Acción al hacer clic en el botón
            btn.setOnClickListener {
                val intent = Intent(context, MapaUsuariosActivity::class.java)

                val bundle = Bundle()
                bundle.putString("uid", usuarioCelda.uid)

                intent.putExtras(bundle) // Agrega los extras al Intent
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Agrega la bandera FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent) // Inicia la actividad utilizando el contexto
            }
        }

        return convertView
    }
}