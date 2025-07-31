package util

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {

    private const val PREFS = "ui_prefs"
    private const val KEY_NIGHT = "night_enabled"

    /** Aplica el tema guardado al arrancar la app */
    fun applyThemeFromPrefs(context: Context) {
        val night = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean(KEY_NIGHT, false)

        AppCompatDelegate.setDefaultNightMode(
            if (night) AppCompatDelegate.MODE_NIGHT_YES
            else       AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    /** Alterna entre claro / oscuro y lo persiste */
    fun toggleTheme(context: Context) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val next  = !prefs.getBoolean(KEY_NIGHT, false)     // el nuevo estado

        prefs.edit().putBoolean(KEY_NIGHT, next).apply()

        AppCompatDelegate.setDefaultNightMode(
            if (next) AppCompatDelegate.MODE_NIGHT_YES
            else      AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    /** Útil si quieres saber cuál está activo para cambiar el título del menú */
    fun isDarkTheme(context: Context): Boolean =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .getBoolean(KEY_NIGHT, false)
}
