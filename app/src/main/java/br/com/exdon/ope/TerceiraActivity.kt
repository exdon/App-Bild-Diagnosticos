package br.com.exdon.ope

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.actionbar.*


class TerceiraActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terceira)

        /*
        botaoExame.setOnClickListener{getTextExame()}
        botaoAgendamento.setOnClickListener{getTextAgendamento()}
        botaoContact.setOnClickListener{getTextContact()}*/

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val args = intent.extras
        val opcoes = args?.getString("title")

        supportActionBar?.title = opcoes

        //Pega a intent que disparou esta Activity

        //Pega a intent que disparou esta Activity
        val intent = intent

        //Recuperei o texto

        //Recuperei o texto
        val texto = intent.getStringExtra("chave")


    }
}