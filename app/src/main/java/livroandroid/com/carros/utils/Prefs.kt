package livroandroid.com.carros.utils

import android.content.SharedPreferences
import livroandroid.com.carros.CarrosApplication

object Prefs {

    val PREF_ID = "carros"

    private fun prefs(): SharedPreferences {
        val context = CarrosApplication.getInstance().applicationContext
        return context.getSharedPreferences(PREF_ID, 0)
    }

    fun setInt(flag: String, valor: Int) {
        val pref = prefs()
        val editor = pref.edit()
        editor.putInt(flag, valor)
        editor.apply()
    }

    fun getInt(flag: String): Int {
        val pref = prefs()
        val l = pref.getInt(flag, 0)
        return l
    }

    fun setString(flag: String, valor: String) {
        val pref = prefs()
        val editor = pref.edit()
        editor.putString(flag, valor)
        editor.apply()
    }

    fun getString(flag: String): String? {
        val pref = prefs()
        val s = pref.getString(flag, "")
        return s
    }

    var tabIdx: Int
        get() = getInt("tabIdx")
        set(value) = setInt("tabIdx", value)
}