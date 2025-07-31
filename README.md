Una app Android sencilla (Kotlin) para crear, editar y gestionar notas de forma rápida, con modo oscuro / claro y datos que se conservan entre sesiones.

Funcionalidades principales
Función	Detalle
Crear / editar notas	Pantalla DetalleNotaActivity con campos de título y contenido.
Borrar notas	1) Deslizar la tarjeta a la izquierda / derecha.
2) Botón Eliminar dentro de la vista de edición.
Lista de notas	RecyclerView con NotasAdapter; ordena de la más reciente a la más antigua.
Modo claro / oscuro	Opción de menú . Se guarda la preferencia y se aplica al volver a abrir la app (ThemeHelper).
Persistencia	SharedPreferences + Gson. Almacena toda la lista en JSON, de modo que las notas siguen ahí tras cerrar la app.
