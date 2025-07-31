package com.example.memojis

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.memojis.data.NotasManager
import com.example.memojis.model.Nota
import java.util.Date

class DetalleNotaActivity : AppCompatActivity() {
    private var notaId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_nota)

        val editTextTitulo = findViewById<EditText>(R.id.editTextTitulo)
        val editTextContenido = findViewById<EditText>(R.id.editTextContenido)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        // Recibe el id como String
        notaId = intent.getStringExtra("nota_id")
        if (notaId != null) {
            editTextTitulo.setText(intent.getStringExtra("titulo_nota"))
            editTextContenido.setText(intent.getStringExtra("contenido_nota"))
        }else{
            btnEliminar.visibility= View.GONE // Oculta el botón de eliminar si es una nueva nota
        }

        btnGuardar.setOnClickListener {
            val titulo = editTextTitulo.text.toString()
            val contenido = editTextContenido.text.toString()
            if (notaId != null) {
                val notaOriginal = NotasManager.obtenerNotaPorId(this, notaId!!)
                val notaActualizada = Nota(
                    id = notaId!!,
                    titulo = titulo,
                    contenido = contenido,
                    fechaCreacion = notaOriginal?.fechaCreacion ?: Date(),
                    fechaModificacion = Date()
                )
                NotasManager.actualizarNota(this, notaActualizada) // PASA CONTEXTO
            } else {
                NotasManager.agregarNota(this, titulo, contenido) // PASA CONTEXTO
            }
            setResult(Activity.RESULT_OK)
            finish()
        }
        btnEliminar.setOnClickListener {
            if (notaId != null) {
                NotasManager.eliminarNota(this, notaId!!)
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
}}