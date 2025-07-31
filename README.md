app/
 ├─ data/
 │   └─ NotasManager.kt    ← CRUD + persistencia
 ├─ model/
 │   └─ Nota.kt            ← data class (UUID, título, contenido, fechas)
 ├─ ui/
 │   ├─ MainActivity.kt    ← lista + FAB + swipe-to-delete + menú tema
 │   └─ DetalleNotaActivity.kt ← crear / editar / eliminar
 ├─ util/
 │   └─ ThemeHelper.kt     ← guardar / restaurar preferencia de tema
 └─ res/
     ├─ layout/            ← activity_main.xml, activity_detalle_nota.xml, item_nota.xml
     ├─ values/            ← colores, strings, themes.xml
     └─ values-night/      ← themes.xml (modo oscuro)
