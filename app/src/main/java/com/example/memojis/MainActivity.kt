package com.example.memojis

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.memojis.adapter.NotasAdapter
import com.example.memojis.data.NotasManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import util.ThemeHelper

class MainActivity : AppCompatActivity() {

    private lateinit var notasAdapter: NotasAdapter
    private lateinit var detalleLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        // -- aplica el tema guardado ANTES de pintar la activity
        ThemeHelper.applyThemeFromPrefs(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* ---------- Toolbar --------- */
        setSupportActionBar(findViewById(R.id.toolbar))

        /* ---------- RecyclerView ---------- */
        val recycler = findViewById<RecyclerView>(R.id.recyclerViewNotas).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        /* ---------- Launcher de Detalle ---------- */
        detalleLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                notasAdapter.actualizarNotas(
                    NotasManager.obtenerTodasLasNotas(this)
                )
            }
        }

        /* ---------- Adapter ---------- */
        notasAdapter = NotasAdapter(
            NotasManager.obtenerTodasLasNotas(this)
        ) { nota ->
            // abrir Detalle en modo edición
            val i = Intent(this, DetalleNotaActivity::class.java).apply {
                putExtra("nota_id", nota.id)
                putExtra("titulo_nota", nota.titulo)
                putExtra("contenido_nota", nota.contenido)
            }
            detalleLauncher.launch(i)
        }
        recycler.adapter = notasAdapter

        /* ---------- Swipe-to-delete ---------- */
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val pos = vh.bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    val nota = notasAdapter.getNotaEnPosicion(pos)
                    NotasManager.eliminarNota(this@MainActivity, nota.id)
                    notasAdapter.actualizarNotas(
                        NotasManager.obtenerTodasLasNotas(this@MainActivity)
                    )
                }
            }
        }).attachToRecyclerView(recycler)

        /* ---------- FAB ---------- */
        findViewById<FloatingActionButton>(R.id.fabAgregarNota).setOnClickListener {
            detalleLauncher.launch(Intent(this, DetalleNotaActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        notasAdapter.actualizarNotas(
            NotasManager.obtenerTodasLasNotas(this)
        )
    }

    /* ---------- Menú ---------- */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        // cambia dinámicamente el título según el tema actual
        menu.findItem(R.id.action_toggle_theme).title = if (ThemeHelper.isDarkTheme(this))
            getString(R.string.menu_light) else getString(R.string.menu_dark)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_toggle_theme -> {
                ThemeHelper.toggleTheme(this)   // aplica y guarda
                recreate()                     // recreamos para aplicar de inmediato
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

