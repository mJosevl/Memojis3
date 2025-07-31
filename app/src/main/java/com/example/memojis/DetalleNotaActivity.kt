package com.example.memojis

import android.app.Activity
import android.os.Bundle
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

        // Recibe el id como String
        notaId = intent.getStringExtra("nota_id")
        if (notaId != null) {
            editTextTitulo.setText(intent.getStringExtra("titulo_nota"))
            editTextContenido.setText(intent.getStringExtra("contenido_nota"))
        }

        btnGuardar.setOnClickListener {
            val titulo = editTextTitulo.text.toString()
            val contenido = editTextContenido.text.toString()
            if (notaId != null) {
                val notaOriginal = NotasManager.obtenerNotaPorId(notaId!!)
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
}}