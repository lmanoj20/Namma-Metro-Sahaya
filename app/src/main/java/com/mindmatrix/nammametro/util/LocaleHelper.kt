package com.mindmatrix.nammametro.util

import android.content.Context
import android.content.res.Configuration
import androidx.core.content.edit
import java.util.Locale

/**
 * Persists and applies the user's language choice (FR-08).
 * Default English; toggle switches to Kannada with 20sp+ font sizes
 * (handled in values-kn dimens / styles).
 */
object LocaleHelper {

    private const val PREFS = "namma_metro_prefs"
    private const val KEY_LANG = "selected_language"
    const val EN = "en"
    const val KN = "kn"

    fun getLanguage(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANG, EN) ?: EN
    }

    fun setLanguage(context: Context, lang: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit { putString(KEY_LANG, lang) }
    }

    fun applyLanguage(context: Context): Context {
        val lang = getLanguage(context)
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

    fun toggle(context: Context): String {
        val next = if (getLanguage(context) == EN) KN else EN
        setLanguage(context, next)
        return next
    }
}
