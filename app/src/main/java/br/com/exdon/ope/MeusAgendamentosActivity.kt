package br.com.exdon.ope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.layout_menu_lateral
import kotlinx.android.synthetic.main.activity_meus_agendamentos.*
import kotlinx.android.synthetic.main.activity_meus_exames2.*
import kotlinx.android.synthetic.main.activity_meus_exames2.recyclerView
import kotlinx.android.synthetic.main.activity_opcoes_agendamento.*

class MeusAgendamentosActivity : DebugActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meus_agendamentos)

        //this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "MEUS AGENDAMENTOS"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //configuraMenuLateral()

        //swipe refresh
        refreshApp()


        var db = DataBaseHandler(this)
        var data = db.readData()
        val arrayList = ArrayList<Model>()
        for ( i in 0..(data.size-1)) {
            arrayList.add(Model(data.get(i).exame,
                "Nome: " + data.get(i).nome_paciente + "\n" +
                "Convênio: " + data.get(i).convenio + "\n" +
                        "Unidade: " + data.get(i).unidade + "\n" +
                        "Exame: " +  data.get(i).exame + "\n" +
                    "Data: "+ data.get(i).data_exame + "\n" +
                        "Horário: " + data.get(i).hora_exame + "\n" +
                    "Obs: " + data.get(i).observacao,
                R.drawable.logo_ic,
                data.get(i).id))

        }
        val myAdapter = MyAdapterAgendamento(arrayList, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter


    }

    private fun refreshApp(){
        swipeToRefresh.setOnRefreshListener {
            Toast.makeText(this, "Página atualizada!", Toast.LENGTH_SHORT).show()

            swipeToRefresh.isRefreshing = false
        }
    }
}