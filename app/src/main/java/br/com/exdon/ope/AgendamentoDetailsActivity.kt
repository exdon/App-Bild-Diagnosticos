package br.com.exdon.ope

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_agendamento_details.*
import kotlinx.android.synthetic.main.activity_document_meus_exames.*
import kotlinx.android.synthetic.main.activity_document_meus_exames.a_description
import kotlinx.android.synthetic.main.activity_document_meus_exames.a_title
import kotlinx.android.synthetic.main.activity_document_meus_exames.imageView
import kotlinx.android.synthetic.main.menu_lateral_cabecalho.*

class AgendamentoDetailsActivity : DebugActivity() {
    var idAgendamento: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendamento_details)

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
        idAgendamento = intent.getIntExtra("Id",0)

        // set title in another activity
        actionBar.setTitle(aTitle)
        a_title.text = aTitle
        a_description.text = aDescription
        imageView.setImageResource(aImageView)

        var db = DataBaseHandler(this)

        /*
        btn_read.setOnClickListener {

            var data = db.readData()
            var intent = Intent()
            tvResult.text = ""
            for (i in 0..(data.size - 1)) {
                tvResult.append(
                    "ID: " + data.get(i).id.toString() + " " + "Nome: " + data.get(i).nome_paciente + " " + "Convênio: " + data.get(i).convenio + " " +
                            "Exame: " + data.get(i).exame + " " + "Unidade: " + data.get(i).unidade + " " + "Data: " + data.get(i).data_exame + " " +
                            "Hora: " + data.get(i).hora_exame + " " + "Obs: " + data.get(i).observacao + "\n")
            }
        }

         */

        btn_update.setOnClickListener {
            val intent = Intent(this, UpdateAgendamentoActivity::class.java)
            intent.putExtra("Id", idAgendamento)
            var data = db.readData()
            for (i in 0..(data.size - 1)) {
                intent.putExtra("nomePaciente", data.get(i).nome_paciente)
                intent.putExtra("dataEscolhida", data.get(i).data_exame)
                intent.putExtra("horaEscolhida", data.get(i).hora_exame)
                intent.putExtra("obs", data.get(i).observacao)
            }
            startActivity(intent)
            //btn_read.performClick()
        }

        btn_delete.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
            builder.setTitle("Excluir Agendamento")
            builder.setIcon(R.drawable.ic_warning)
            builder.setMessage("Você deseja excluir o agendamento?")
            builder.setPositiveButton("Sim") { dialog, which ->
                db.deleteData(idAgendamento!!)
                var intent = Intent(this, MeusAgendamentosActivity::class.java)
                startActivity(intent)
                enviaNotificacao()
                //finish()
            }
            builder.setNegativeButton("Não") {dialog, which ->}
            val dialog: AlertDialog = builder.create()
            dialog.show()

            //btn_read.performClick()
        }
    }
    fun enviaNotificacao() {
        val intent = Intent(this, MenuActivity::class.java)
        NotificationUtil.create(1, intent, "Cancelamento de Agendamento", "Olá, você cancelou um agendamento de exame. Maiores informações, entre em contato conosco!")
        startActivity(intent)
    }
}