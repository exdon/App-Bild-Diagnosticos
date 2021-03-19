package br.com.exdon.ope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.layout_menu_lateral
import kotlinx.android.synthetic.main.activity_meus_exames2.*

class UnidadeActivity : DebugActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unidade)

        this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "Unidades"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        configuraMenuLateral()

        val arrayList = ArrayList<Model>()

        arrayList.add(Model("UNIDADE 1 - Samarianto Campinas", "R. Engenheiro Monlevade, 206", R.drawable.logo_ic))
        arrayList.add(Model("UNIDADE 2 - Samarianto Campinas", "Av. São José dos Campos, 256", R.drawable.logo_ic))
        arrayList.add(Model("Samaritano Hortolândia", "R. Osvaldo Silva, 10", R.drawable.logo_ic))
        arrayList.add(Model("Shopping Hortolândia", "R. José Camilo de Carmargo, 5", R.drawable.logo_ic))
        arrayList.add(Model("Hospital Stª Ignês Indaiatuba", "Av. Presidente Vargas, 1591", R.drawable.logo_ic))

        val myAdapter = MyAdapterUnidade(arrayList, this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter
    }
}