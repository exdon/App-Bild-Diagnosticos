package br.com.exdon.ope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.actionbar.*

class TermoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_termo)

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "TERMOS DE USO"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}