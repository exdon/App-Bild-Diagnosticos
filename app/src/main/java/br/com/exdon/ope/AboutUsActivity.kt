package br.com.exdon.ope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_menu.layout_menu_lateral

class AboutUsActivity : DebugActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "Sobre a Bild Diagnósticos"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        configuraMenuLateral()
    }
}