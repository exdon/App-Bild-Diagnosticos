package br.com.exdon.ope

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.setuid
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.common.base.MoreObjects
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_novo_exame.*
import java.util.zip.Inflater
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_novo_exame.layout_menu_lateral
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class NovoExameActivity : DebugActivity() {

    lateinit var db: DocumentReference
    lateinit var option: Spinner
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: Notification.Builder
    private val channelId = "br.com.exdon.ope"
    private val description = "Teste Noitificação"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_exame)


        //this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "NOVO AGENDAMENTO"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //configuraMenuLateral()


        // Notification add on 6/11
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


        // firebase cloud firestore
        db = FirebaseFirestore.getInstance().document("Agendamentos/Dados Agendamento")

        val enviarbtn = findViewById<View>(R.id.botaoSalvar) as Button

        enviarbtn.setOnClickListener { view: View? ->

        }

        // Spinner Convenio
        option = findViewById(R.id.textConvenio) as Spinner

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

        // Spinner Exame
        option = findViewById(R.id.nomeExame) as Spinner

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

        // Spinner Unidade
        option = findViewById(R.id.textUnidade) as Spinner

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



        dataNovoExame.setOnClickListener {
            val dataEscolhida = DatePickerDialog(
                this,
                R.style.CalendarCustom,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // set to textView
                    dataNovoExame.setText("" + dayOfMonth + "/" + "${monthOfYear + 1}" + "/" + year)

                },
                year,
                month,
                day
            )
            // show dialog
            dataEscolhida.show()
        }

        //Timer Picking
        horaNovoExame.setOnClickListener{
            val timeSetListener = TimePickerDialog.OnTimeSetListener{ timePicker, hour, minute ->
                c.set(Calendar.HOUR_OF_DAY, hour)
                c.set(Calendar.MINUTE, minute)

                horaNovoExame.setText(SimpleDateFormat("HH:mm").format(c.time))

            }
            TimePickerDialog(this, R.style.CalendarCustom, timeSetListener, hora, minute, true).show()
        }


        // cadastro agendamento sqllite 09/11
        botaoSalvar.setOnClickListener{
            enviarDadosAgendamento()
        }



        /*// cadastro agendamento by sqllite

        botaoSalvar.setOnClickListener {
            val agendamento = Agendamento()
            agendamento.nomePaciente = textPaciente.text.toString()
            agendamento.convenio = textConvenio.text.toString()
            agendamento.exame = nomeExame.selectedItem.toString()
            agendamento.unidade = textUnidade.selectedItem.toString()
            agendamento.dataExame = dataNovoExame.text.toString()
            agendamento.observacao = observacoes.text.toString()

            taskAtualizar(agendamento)
        }
    }

    private fun taskAtualizar(agendamento: Agendamento) {
        // Thread para salvar o agendamento
        Thread {
            OpeAppService.saveAgendamento(agendamento)
            runOnUiThread{
                // após cadastrar, voltar para activity anterior
                finish()
            }
        }.start()*/
    }

    private fun enviarDadosAgendamento(){
        val nomePaciente = findViewById<View>(R.id.textPaciente) as EditText
        val convenio = findViewById<View>(R.id.textConvenio) as Spinner
        val nomeExame = findViewById<View>(R.id.nomeExame) as Spinner
        val unidade = findViewById<View>(R.id.textUnidade) as Spinner
        val dataEscolhida = findViewById<View>(R.id.dataNovoExame) as EditText
        val horaEscolhida = findViewById<View>(R.id.horaNovoExame) as EditText
        val observacoes = findViewById<View>(R.id.observacoes) as EditText

        val nomePacienteString = nomePaciente.text.toString().trim()
        val convenioString = convenio.selectedItem.toString().trim()
        val nomeExameString = nomeExame.selectedItem.toString().trim()
        val unidadeString = unidade.selectedItem.toString().trim()
        val dataEscolhidaString = dataEscolhida.text.toString().trim()
        val horaEscolhidaString = horaEscolhida.text.toString().trim()
        val observacoesString = observacoes.text.toString().trim()

        if (!nomeExameString.isEmpty() && !unidadeString.isEmpty() && !nomePacienteString.isEmpty() &&
            !dataEscolhidaString.isEmpty() && !convenioString.isEmpty() && !horaEscolhidaString.isEmpty()) {

            var dadosAgendamento = DadosAgendamento(nomePacienteString, convenioString, nomeExameString, unidadeString, dataEscolhidaString, horaEscolhidaString, observacoesString)
            var db = DataBaseHandler(this)
            db.insertData(dadosAgendamento)
            enviar()
            //enviaNotificacao()
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }
    }

    /*
    private fun setDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONDAY)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dataEscolhida = DatePickerDialog(
            this, DatePickerDialog.OnDateSetListener{view, year, monthOfYear, dayOfMonth ->
                val returnDate = "${monthOfYear + 1} $dayOfMonth $year"
                val date = StringHelper.parseDate(
                    "MM dd yyyy",
                    "dd/MM/yyyy",
                    returnDate
                )
                dataNovoExame.setText(MoreObjects.ToStringHelper.parseDate("MM/dd/yyyy", "MMM dd yyyy", date))
                dataNovoExame.error = null


            },year-30, month, day
        )
    }*/

    // Salvar no Firebase

    private fun enviar() {
        val nomeExame = findViewById<View>(R.id.nomeExame) as Spinner
        val unidade = findViewById<View>(R.id.textUnidade) as Spinner
        val convenio = findViewById<View>(R.id.textConvenio) as Spinner
        val nomePaciente = findViewById<View>(R.id.textPaciente) as EditText
        val dataEscolhida = findViewById<View>(R.id.dataNovoExame) as EditText
        val horaEscolhida = findViewById<View>(R.id.horaNovoExame) as EditText
        val observacoes = findViewById<View>(R.id.observacoes) as EditText

        val nomeExameString = nomeExame.selectedItem.toString().trim()
        val convenioString = convenio.selectedItem.toString().trim()
        val unidadeString = unidade.selectedItem.toString().trim()
        val nomePacienteString = nomePaciente.text.toString().trim()
        val dataEscolhidaString = dataEscolhida.text.toString().trim()
        val horaEscolhidaString = horaEscolhida.text.toString().trim()
        val observacoesString = observacoes.text.toString().trim()

        if (!nomeExameString.isEmpty() && !unidadeString.isEmpty() && !nomePacienteString.isEmpty() && !dataEscolhidaString.isEmpty() && !convenioString.isEmpty() && !horaEscolhidaString.isEmpty()) {
            try {
                val items = HashMap<String, Any>()
                items.put("nomeExame", nomeExameString)
                items.put("convenio", convenioString)
                items.put("unidade", unidadeString)
                items.put("nomePaciente", nomePacienteString)
                items.put("dataEscolhida", dataEscolhidaString)
                items.put("horaEscolhida", horaEscolhidaString)
                items.put("descricao", observacoesString)
                db.collection(nomePacienteString).document("Dados Agendamento").set(items, SetOptions.merge()).addOnSuccessListener {
                    void: Void? -> Toast.makeText(this, "Dados carregados com Sucesso!", Toast.LENGTH_SHORT).show()
                    enviaNotificacao()
                    //var intent = Intent(this, MenuActivity::class.java)
                    //startActivity(intent)
                }.addOnFailureListener{
                    exception: java.lang.Exception -> Toast.makeText(this, exception.toString(),Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
        }

    }
    fun enviaNotificacao() {
        val nomePaciente = findViewById<View>(R.id.textPaciente) as EditText
        val nomePacienteString = nomePaciente.text.toString().trim()
        val dataEscolhida = findViewById<View>(R.id.dataNovoExame) as EditText
        val dataEscolhidaString = dataEscolhida.text.toString().trim()

        val intent = Intent(this, MenuActivity::class.java)
        NotificationUtil.create(1, intent, "Confirmação de Agendamento", "Olá ${nomePacienteString}, você acabou de fazer um agendamento conosco para o dia ${dataEscolhidaString}. Maiores informações, entre em contato conosco e/ou acesse a área de 'Meus agendamentos' em nosso app!")
        startActivity(intent)
    }

}