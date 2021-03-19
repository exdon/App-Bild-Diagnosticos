package br.com.exdon.ope

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.net.URL

object MeusExamesService {

    val host = "https://fesousa.pythonanywhere.com"
    val TAG = "WS_opeApp"

    fun getMeusExames() : List<MeusExames> {
        val url = "$host/disciplinas"
        val json = HttpHelper.get(url)
        Log.d(TAG, json)
        return parserJson<List<MeusExames>>(json)
    }

    fun saveExames(exames: MeusExames) {
        val json = GsonBuilder().create().toJson(exames)
        HttpHelper.post("$host/disciplinas", json)
    }

    inline fun <reified T> parserJson(json: String): T {
        val type = object: TypeToken<T>() {}.type
        return Gson().fromJson<T>(json, type)
    }
}