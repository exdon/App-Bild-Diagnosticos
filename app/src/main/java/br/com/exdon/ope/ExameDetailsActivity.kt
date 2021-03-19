package br.com.exdon.ope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_document_meus_exames.*

class ExameDetailsActivity : DebugActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exame_details)


        //this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)

        // alterar título da ActionBar
        //supportActionBar?.title = "Meus Exames"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //configuraMenuLateral()

        val actionBar : ActionBar? = supportActionBar
        // actionBar!!.setDisplayHomeAsUpEnabled(true)
        actionBar!!.setDisplayShowHomeEnabled(true)
        // now get data from putExtra intent
        var intent = intent
        val aTitle = intent.getStringExtra("iTitle")
        val aDescription = intent.getStringExtra("iDescription")
        val aImageView = intent.getIntExtra("iImageView", 0)

        // set title in another activity
        actionBar.setTitle(aTitle)
        a_title.text = aTitle
        a_description.text = aDescription
        imageView.setImageResource(aImageView)
    }
}