package com.example.keymapper

import android.content.Context

/** Guarda mapeamentos: keyCode -> posição (x,y) na tela. */
object MappingStore {
    private fun prefs(c: Context) = c.getSharedPreferences("maps", Context.MODE_PRIVATE)

    fun save(c: Context, keyCode: Int, x: Float, y: Float) {
        prefs(c).edit().putString(keyCode.toString(), "$x,$y").apply()
    }

    fun get(c: Context, keyCode: Int): Pair<Float, Float>? {
        val v = prefs(c).getString(keyCode.toString(), null) ?: return null
        val p = v.split(",")
        return p[0].toFloat() to p[1].toFloat()
    }

    fun all(c: Context): Map<Int, String> =
        prefs(c).all.mapKeys { it.key.toInt() }.mapValues { it.value as String }

    fun clear(c: Context) = prefs(c).edit().clear().apply()
}
