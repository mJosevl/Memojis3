package com.example.memojis

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memojis.adapter.NotasAdapter
import com.example.memojis.data.NotasManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var notasAdapter: NotasAdapter
    private lateinit var recyclerView: RecyclerView
    private val notasManager = NotasManager
    private lateinit var detalleLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerViewNotas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        // ...dentro de onCreate, después de configurar el recyclerView y el adapter:

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // No soportamos mover, solo swipe
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val nota = notasAdapter.getNotaEnPosicion(position)
                NotasManager.eliminarNota(nota.id)
                notasAdapter.actualizarNotas(NotasManager.obtenerTodasLasNotas())
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)

        // Registrar el launcher para recibir el resultado de la actividad de detalle
        detalleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                notasAdapter.actualizarNotas(notasManager.obtenerTodasLasNotas())
            }
        }

        // Inicializar el adapter con la lista y el clickListener
        notasAdapter = NotasAdapter(notasManager.obtenerTodasLasNotas()) { nota ->
            val intent = Intent(this, DetalleNotaActivity::class.java)
            intent.putExtra("nota_id", nota.id)
            intent.putExtra("titulo_nota", nota.titulo)
            intent.putExtra("contenido_nota", nota.contenido)
            detalleLauncher.launch(intent)
        }
        recyclerView.adapter = notasAdapter

        val fabAgregar = findViewById<FloatingActionButton>(R.id.fabAgregarNota)
        fabAgregar.setOnClickListener {
            // Para agregar una nota nueva, solo abre el DetalleNotaActivity sin extras
            val intent = Intent(this, DetalleNotaActivity::class.java)
            detalleLauncher.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        notasAdapter.actualizarNotas(notasManager.obtenerTodasLasNotas())
    }
}
