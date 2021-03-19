package br.com.exdon.ope

import android.content.SharedPreferences

object Prefs {
    val PREF_ID = "OPE"


    private fun prefs() : SharedPreferences {
        val contexto = LMSApplication.getInstance().applicationContext
        return contexto.getSharedPreferences(PREF_ID, 0)
    }

    fun setString(flag: String, valor: String) = prefs().edit().putString(flag, valor).apply()

    fun getString(flag: String) = prefs().getString(flag, "")

    fun setBoolean(flag: String, valor: Boolean) = prefs().edit().putBoolean(flag, valor).apply()

    fun getBoolean(flag: String) = prefs().getBoolean(flag, false)
}