package com.example.memojis.model

import java.io.Serializable // ¡Importante para que nuestra nota viaje entre pantallas!
import java.util.Date

data class Nota(

    val id: String = java.util.UUID.randomUUID().toString(),

    var titulo: String,

    var contenido: String,

    val fechaCreacion: Date = Date(),

    var fechaModificacion: Date = Date()
) : Serializable



// Esto le dice al sistema que nuestra nota puede ser "empaquetada" y enviada.

/*¿Qué diablos es java.util.UUID.randomUUID().toString()?
java.util.UUID.randomUUID().toString() es como ir a una fábrica de números de serie infinitos y garantizados como únicos, pulsar un botón que te entrega el siguiente número de serie único,
y luego convertir ese número de serie a un formato de texto para que lo podamos guardar y usar fácilmente
en nuestra aplicación.

¿Por qué lo usamos?
Para darle a cada nota un identificador único garantizado, para que nunca haya confusión entre
"Nota A" y "Nota B", incluso si ambas tienen el mismo título o contenido.
 Esto es fundamental para poder guardar, encontrar, editar y eliminar la nota correcta en nuestra aplicación.
 */