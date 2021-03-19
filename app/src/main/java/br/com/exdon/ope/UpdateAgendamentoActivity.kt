package br.com.exdon.ope

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_novo_exame.*
import kotlinx.android.synthetic.main.activity_update_agendamento.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateAgendamentoActivity : AppCompatActivity() {
    var idAgendamento: Int? = null
    var nomePaciente : String? = null
    var dataEscolhidaS : String? = null
    var horaEscolhidaS : String? = null
    var obsS : String? = null
    lateinit var option: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_agendamento)

        //this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "ALTERAR AGENDAMENTO"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //configuraMenuLateral()


        // get putExtra do AgendamentoDetailsActivity
        var intent = intent
        idAgendamento = intent.getIntExtra("Id",0)
        nomePaciente = intent.getStringExtra("nomePaciente").toString()
        dataEscolhidaS = intent.getStringExtra("dataEscolhida").toString()
        horaEscolhidaS = intent.getStringExtra("horaEscolhida").toString()
        obsS = intent.getStringExtra("obs").toString()

        // Spinner Convenio
        option = findViewById(R.id.textConvenioUpdate) as Spinner

        val optionsConvenio = arrayOf(
            "Selecione",
            "Particular",
            "Allianz",
            "Amil",
            "Bradesco Saúde",
            "CarePlus",
            "Cabesp",
            "Cassi",
            "Coopus",
            "Doctor Clin",
            "Economus",
            "E&E",
            "Funcesp",
            "Gama Saúde",
            "Geap Saúde",
            "Golden Cross",
            "Green Life",
            "Intermedici",
            "Life empresarial saude",
            "Marítima Saúde",
            "MediSanitas",
            "Mediservice",
            "OP.Unicentral Plano Saúde",
            "Porto Seguro",
            "PHS Samaritano",
            "Santa casa de Valinhos",
            "Santa Mália saúde",
            "Santa Tereza",
            "São Franscisco",
            "Saúde beneficiência",
            "Seprev",
            "Sinpospetro",
            "SulAmérica",
            "Unimed"
        )
        option.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsConvenio)

        // Spinner Exame update
        option = findViewById(R.id.nomeExameUpdate) as Spinner

        val optionsExame = arrayOf(
            "Selecione",
            "Ressonância",
            "Mamografia",
            "Tomografia",
            "Ultrassom",
            "Raio-x",
            "Densitometria"
        )
        option.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsExame)

        // Spinner Unidade update
        option = findViewById(R.id.textUnidadeUpdate) as Spinner

        val optionsUnidade = arrayOf(
            "Selecione",
            "Unidade 1 - Samarianto Campinas",
            "Unidade 2 - Samarianto Campinas",
            "Samaritano Hortolândia",
            "Shopping Hortolândia",
            "Hospital Stª Ignês Indaiatuba"
        )
        option.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsUnidade)

        // calendar
        var c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONDAY)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val hora = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)



        dataNovoExameUpdate.setOnClickListener {
            val dataEscolhida = DatePickerDialog(
                this,
                R.style.CalendarCustom,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // set to textView
                    dataNovoExameUpdate.setText("" + dayOfMonth + "/" + "${monthOfYear + 1}" + "/" + year)

                },
                year,
                month,
                day
            )
            // show dialog
            dataEscolhida.show()
        }

        //Timer Picking
        horaNovoExameUpdate.setOnClickListener{
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)

                horaNovoExameUpdate.setText(SimpleDateFormat("HH:mm").format(c.time))

            }
            TimePickerDialog(this, R.style.CalendarCustom, timeSetListener, hora, minute, true).show()
        }


        // set conteudo nos edit text

        var db = DataBaseHandler(this)
        var data = db.readData()
        /*
        //var intent = Intent()
        for (i in 0..(data.size - 1)) {
            textPacienteUpdate.text = Editable.Factory.getInstance().newEditable(nomePaciente)
            dataNovoExameUpdate.text = Editable.Factory.getInstance().newEditable(dataEscolhidaS)
            horaNovoExameUpdate.text = Editable.Factory.getInstance().newEditable(horaEscolhidaS)
            observacoesUpdate.text = Editable.Factory.getInstance().newEditable(obsS)
        }

         */


        btnUpdateAgendamento.setOnClickListener{
            val convenio = findViewById<View>(R.id.textConvenioUpdate) as Spinner
            val nomeExame = findViewById<View>(R.id.nomeExameUpdate) as Spinner
            val unidade = findViewById<View>(R.id.textUnidadeUpdate) as Spinner
            val dataEscolhida = findViewById<View>(R.id.dataNovoExameUpdate) as EditText
            val horaEscolhida = findViewById<View>(R.id.horaNovoExameUpdate) as EditText
            val observacoes = findViewById<View>(R.id.observacoesUpdate) as EditText

            val convenioString = convenio.selectedItem.toString().trim()
            val nomeExameString = nomeExame.selectedItem.toString().trim()
            val unidadeString = unidade.selectedItem.toString().trim()
            val dataEscolhidaString = dataEscolhida.text.toString().trim()
            val horaEscolhidaString = horaEscolhida.text.toString().trim()
            val observacoesString = observacoes.text.toString().trim()

            if (!nomeExameString.isEmpty() && !unidadeString.isEmpty()  && !dataEscolhidaString.isEmpty() &&
                !convenioString.isEmpty() && !horaEscolhidaString.isEmpty()) {
                db.updateSqlite(idAgendamento!!, convenioString, nomeExameString, unidadeString, dataEscolhidaString, horaEscolhidaString, observacoesString)
                Toast.makeText(this, "Dados alterados com sucesso!", Toast.LENGTH_SHORT).show()
                val intentReturn = Intent(this, MeusAgendamentosActivity::class.java)
                startActivity(intentReturn)
            }
            else{
                Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}