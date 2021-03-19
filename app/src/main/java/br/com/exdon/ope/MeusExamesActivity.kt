package br.com.exdon.ope

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_meus_exames.*
import kotlinx.android.synthetic.main.activity_meus_exames.layout_menu_lateral

class MeusExamesActivity : DebugActivity() {

    private val context: Context get() = this
    private var agendamentos = listOf<Agendamento>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_exames)
        this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "Meus Exames"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        configuraMenuLateral()

        recyclerMeusExames?.layoutManager = LinearLayoutManager(this)
        recyclerMeusExames?.itemAnimator = DefaultItemAnimator()
        recyclerMeusExames?.setHasFixedSize(true)

    }

    override fun onResume() {
        super.onResume()
        taskMeusAgendamentos()
    }


    fun taskMeusAgendamentos() {
        this.agendamentos = OpeAppService.getAgendamento(context)
        recyclerMeusExames?.adapter = MeusAgendamentosAdapter(agendamentos) {onClickMeusAgendamentos((it))}
    }

    fun onClickMeusAgendamentos(meusExames: Agendamento) {
        Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show()

    }
}