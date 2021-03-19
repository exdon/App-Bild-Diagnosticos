package br.com.exdon.ope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.layout_menu_lateral
import kotlinx.android.synthetic.main.activity_meus_exames2.*

class ExamesActivity : DebugActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exames)

        this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "Exames"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        configuraMenuLateral()

        val arrayList = ArrayList<Model>()

        arrayList.add(Model("RAIO-X", "A Bild Diagnósticos a fim de prover o melhor atendimento oferece a radiografia digitalizada em todas as suas unidades.", R.drawable.raio_x))
        arrayList.add(Model("ULTRASSONOGRAFIA", "A Bild Diagnósticos oferece estes exames em horários de atendimento estendidos com profissionais capacitados e com resultados sendo entregues de forma rápida e segura", R.drawable.ultrassonografia))
        arrayList.add(Model("TOMOGRAFIA MULTISLICE", "A Bild Diagnósticos oferece este exame em suas unidades, tanto para pacientes agendados/ambulatoriais quanto para pacientes que necessitam deste exame de forma urgente.", R.drawable.tomografia_multislice))
        arrayList.add(Model("RESSONÂNCIA MAGNÉTICA", "A Bild Diagnósticos oferece estes exames inclusive com sedação anestésica, sempre com segurança, precisão e atendimento humanizado com uma estrutura moderna e acolhedora. ", R.drawable.ressonancia_magnetica))
        arrayList.add(Model("MAMOGRAFIA DIGITAL", "A Bild Diagnósticos oferece este exame em um espaço exclusivo para as mulheres, Espaço Saúde Mulher, sempre com segurança, precisão e atendimento humanizado em uma estrutura moderna e acolhedora. ", R.drawable.mamografia))
        arrayList.add(Model("DENSITOMETRIA ÓSSEA", "A Densitometria Óssea é um exame que mede a densidade de minerais dos ossos e a composição de massa corpórea.", R.drawable.densitometria_ossea))

        val myAdapter = MyAdapterExame(arrayList, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
    }
}