package br.com.exdon.ope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_novo_exame.*
import kotlinx.android.synthetic.main.activity_opcoes_agendamento.*

class OpcoesAgendamentoActivity : DebugActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opcoes_agendamento)

        //this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "AGENDAMENTO"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //configuraMenuLateral()


        cardview_novo_agendamento.setOnClickListener{
            var intent = Intent(this, NovoExameActivity::class.java)
            var titleOpcoes = Bundle()
            titleOpcoes.putString("title", "AGENDAMENTO")
            intent.putExtras(titleOpcoes)
            startActivity(intent)
        }

        cardview_meus_agendamentos.setOnClickListener{
            var intent = Intent(this, MeusAgendamentosActivity::class.java)
            var titleOpcoes = Bundle()
            titleOpcoes.putString("title", "MEUS AGENDAMENTOS")
            intent.putExtras(titleOpcoes)
            startActivity(intent)
        }
    }
}