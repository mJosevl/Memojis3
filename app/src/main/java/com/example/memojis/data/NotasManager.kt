package com.example.memojis.data

import android.content.Context
import com.example.memojis.model.Nota
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object NotasManager {
    private const val PREFS_NAME = "notas_prefs"
    private const val KEY_NOTAS = "notas"

    private var notas: MutableList<Nota> = mutableListOf()
    private var initialized = false

    private fun init(context: Context) {
        if (!initialized) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val notasJson = prefs.getString(KEY_NOTAS, null)
            notas = if (notasJson != null) {
                val type = object : TypeToken<MutableList<Nota>>() {}.type
                Gson().fromJson(notasJson, type)
            } else {
                mutableListOf()
            }
            initialized = true
        }
    }

    fun obtenerTodasLasNotas(context: Context): List<Nota> {
        init(context)
        return notas.toList()
    }

    fun agregarNota(context: Context, titulo: String, contenido: String): Nota {
        init(context)
        val nota = Nota(titulo = titulo, contenido = contenido)
        notas.add(nota)
        guardarNotas(context)
        return nota
    }

    fun eliminarNota(context: Context, id: String) {
        init(context)
        notas.removeIf { it.id == id }
        guardarNotas(context)
    }

    fun actualizarNota(context: Context, notaActualizada: Nota): Boolean {
        init(context)
        val index = notas.indexOfFirst { it.id == notaActualizada.id }
        return if (index != -1) {
            notas[index] = notaActualizada
            guardarNotas(context)
            true
        } else {
            false
        }
    }

    fun obtenerNotaPorId(context: Context, id: String): Nota? {
        init(context)
        return notas.find { it.id == id }
    }

    private fun guardarNotas(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val notasJson = Gson().toJson(notas)
        prefs.edit().putString(KEY_NOTAS, notasJson).apply()
    }
}
