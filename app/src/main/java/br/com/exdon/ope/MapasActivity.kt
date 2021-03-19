package br.com.exdon.ope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.actionbar.*

class MapasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapas)

    }

    override fun onResume() {
        super.onResume()
        val mapaFragment = MapaFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.layoutMapas, mapaFragment)
            .commit()
    }
}