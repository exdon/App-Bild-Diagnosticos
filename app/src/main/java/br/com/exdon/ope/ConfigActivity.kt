package br.com.exdon.ope

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_config.*

class ConfigActivity : AppCompatActivity() {
    private var eyp: Switch? = null
    internal lateinit var sharedpref: SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "CONFIGURAÇÕES"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        card_feedback.setOnClickListener{
            val email = "contato@bilddiagnosticos.com.br"

            sendEmail(email)
        }

        card_termo.setOnClickListener{
            var intent = Intent(this, TermoActivity::class.java)
            var titleOpcoes = Bundle()
            titleOpcoes.putString("title", "TERMOS DE USO")
            intent.putExtras(titleOpcoes)
            startActivity(intent)
        }

        card_update_dados.setOnClickListener{
            var intent = Intent(this, ProfileUpdateActivity::class.java)
            var titleOpcoes = Bundle()
            titleOpcoes.putString("title", "ATUALIZAR DADOS")
            intent.putExtras(titleOpcoes)
            startActivity(intent)
        }


        /*
        // Dark mode
        sharedpref = SharedPref(this)
        if (sharedpref.loadNIghtModeState() === true) {
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.AppTheme)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_config)

        eyp = findViewById<View>(R.id.enableDark) as Switch?
        if (sharedpref.loadNIghtModeState() === true) {
            eyp!!.isChecked = true
        }

        eyp!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sharedpref.setNightModeState(true)
                restartApp()
            } else {
                sharedpref.setNightModeState(false)
                restartApp()
            }
        }
    }

    fun restartApp() {
        val i = Intent(getApplicationContext(), ConfigActivity::class.java)
        startActivity(i)
        finish()
    }

         */
    }

    private fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        // email is put as array because you may wanna send email to multiple emails so enter comma(,) separated emails, it will be stored array
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback - bild Diagnósticos APP")


        try{
            startActivity(Intent.createChooser(intent, "Escolha uma opção de Email Client..."))
        }
        catch (e: Exception) {
            Toast.makeText(this, "Não foi possível concluir sua solicitação!", Toast.LENGTH_SHORT).show()
        }

    }
}