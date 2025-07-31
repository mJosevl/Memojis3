package com.example.memojis.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.memojis.R
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.memojis.model.Nota
import java.text.SimpleDateFormat
import java.util.Locale

class NotasAdapter(
    private var notas: List<Nota>, // La lista de notas que el adaptador va a mostrar
    private val onNotaClickListener: (Nota) -> Unit // Función que se llamará cuando se haga clic en una nota
) : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    // Clase interna que representa la "vista" de un solo elemento de la lista (una tarjeta de nota)
    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitulo: TextView = itemView.findViewById(R.id.textViewTitulo)
        val textViewFecha: TextView = itemView.findViewById(R.id.textViewFecha)
    }

    // Método que crea y "infla" el diseño de cada ítem de la lista (nuestra tarjeta)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_nota, parent, false)
        return NotaViewHolder(view)
    }

    // Método que "rellena" los datos de una nota en el diseño de su tarjeta
    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position] // Obtenemos la nota actual
        holder.textViewTitulo.text = nota.titulo // Asignamos el título al TextView

        // Formateamos la fecha para que se vea bonita
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.textViewFecha.text = "Modificado: ${dateFormat.format(nota.fechaModificacion)}"

        // Configuramos lo que sucede cuando se hace clic en la tarjeta de la nota
        holder.itemView.setOnClickListener {
            onNotaClickListener(nota) // Llamamos a la función que nos pasaron desde la Activity
        }
    }

    // Método que le dice al adaptador cuántos ítems hay en la lista
    override fun getItemCount(): Int {
        return notas.size
    }

    // Función para actualizar la lista de notas que el adaptador está mostrando
    fun actualizarNotas(nuevasNotas: List<Nota>) {
        this.notas = nuevasNotas
        notifyDataSetChanged() // Le decimos al RecyclerView que los datos han cambiado y debe redibujarse
    }

    fun getNotaEnPosicion(pos: Int): Nota {
        return notas[pos]
    }
}