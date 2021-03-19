package br.com.exdon.ope

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.EventLogTags
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_agendamento_details.*
import kotlinx.android.synthetic.main.activity_agendamento_details.layout_menu_lateral
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.activity_profile_update.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.regex.Matcher


class ProfileUpdateActivity : DebugActivity() {


    class User(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val contato: String = "",
        val CEP: String = "",
        val CPF: String = "",
        val RG: String = "",
        val bairroEndereco: String = "",
        val cidadeEndereco: String = "",
        val complementoEndereco: String = "",
        val dataNascimento: String = "",
        val endereco: String = "",
        val estadoEndereco: String = "",
        val numeroEndereco: String = ""
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_update)

        //this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "ATUALIZAR DADOS"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //configuraMenuLateral()


        btn_updateData.setOnClickListener{
            var nomeEditText = first_name_update.text.toString()
            var sobrenomeEditText = last_name_update.text.toString()
            var dataNascEditText = data_nasc_update.text.toString()
            var telefoneEditText = telefone_update.text.toString()
            var cpfEditText = cpf_update.text.toString()
            var rgEditText = rg_update.text.toString()
            var enderecoEditText = logradouro_update.text.toString()
            var numEndEditText = numero_endereco_update.text.toString()
            var complementoEditText = complemento_update.text.toString()
            var bairroEditText = bairro_update.text.toString()
            var cidadeEditText = cidade_update.text.toString()
            var estadoEditText = estado_update.text.toString()
            var cepEditText = cep_update.text.toString()

            if (!nomeEditText.isEmpty() && !sobrenomeEditText.isEmpty() && !dataNascEditText.isEmpty() && !telefoneEditText.isEmpty() &&!cpfEditText.isEmpty() &&
                    !rgEditText.isEmpty() && !enderecoEditText.isEmpty() && !numEndEditText.isEmpty() &&
                    !bairroEditText.isEmpty() && !cidadeEditText.isEmpty() && !estadoEditText.isEmpty() && !cepEditText.isEmpty()) {
                updateData()
            }
            else {
                Toast.makeText(this, "Por favor, preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }


        // calendar
        var c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONDAY)
        val day = c.get(Calendar.DAY_OF_MONTH)



        data_nasc_update.setOnClickListener {
            val dataEscolhida = DatePickerDialog(
                this,
                R.style.CalendarCustom,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // set to textView
                    data_nasc_update.setText("" + dayOfMonth + "/" + "${monthOfYear + 1}" + "/" + year)

                },
                year,
                month,
                day
            )
            // show dialog
            dataEscolhida.show()
        }

        // cep


        consulta_cep_icon.setOnClickListener{
            val cepText = findViewById<View>(R.id.cep_update) as EditText
            val cepTextString = cepText.text.toString()
            if(cep_update.text.toString().length == 8) {
                getCep(cepTextString)
            }else {
                Toast.makeText(this, "O CEP contém 8 digitos", Toast.LENGTH_SHORT).show()
            }
        }


        // completa campos update
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val rootRef = FirebaseDatabase.getInstance().reference
        val uidRef = rootRef.child("Users").child(uid)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                Log.d(TAG, "HELLO" + user!!.firstName)
                first_name_update.text = Editable.Factory.getInstance().newEditable(user.firstName)
                last_name_update.text = Editable.Factory.getInstance().newEditable(user.lastName)
                email_update.text = Editable.Factory.getInstance().newEditable(user.email)
                cep_update.text = Editable.Factory.getInstance().newEditable(user.CEP)
                telefone_update.text = Editable.Factory.getInstance().newEditable(user.contato)
                cpf_update.text = Editable.Factory.getInstance().newEditable(user.CPF)
                rg_update.text = Editable.Factory.getInstance().newEditable(user.RG)
                bairro_update.text = Editable.Factory.getInstance().newEditable(user.bairroEndereco)
                cidade_update.text = Editable.Factory.getInstance().newEditable(user.cidadeEndereco)
                complemento_update.text = Editable.Factory.getInstance().newEditable(user.complementoEndereco)
                data_nasc_update.text = Editable.Factory.getInstance().newEditable(user.dataNascimento)
                logradouro_update.text = Editable.Factory.getInstance().newEditable(user.endereco)
                estado_update.text = Editable.Factory.getInstance().newEditable(user.estadoEndereco)
                numero_endereco_update.text = Editable.Factory.getInstance().newEditable(user.numeroEndereco)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, databaseError.message) //Don't ignore errors!
            }
        }
        uidRef.addListenerForSingleValueEvent(valueEventListener)
    }


    fun updateData(){
        var nomeEditText = first_name_update.text.toString()
        var sobrenomeEditText = last_name_update.text.toString()
        var dataNascEditText = data_nasc_update.text.toString()
        var telefoneEditText = telefone_update.text.toString()
        var cpfEditText = cpf_update.text.toString()
        var rgEditText = rg_update.text.toString()
        var enderecoEditText = logradouro_update.text.toString()
        var numEndEditText = numero_endereco_update.text.toString()
        var complementoEditText = complemento_update.text.toString()
        var bairroEditText = bairro_update.text.toString()
        var cidadeEditText = cidade_update.text.toString()
        var estadoEditText = estado_update.text.toString()
        var cepEditText = cep_update.text.toString()

        var map = mutableMapOf<String,Any>()
        map["firstName"] = nomeEditText
        map["lastName"] = sobrenomeEditText
        map["dataNascimento"] = dataNascEditText
        map["contato"] = telefoneEditText
        map["CPF"] = cpfEditText
        map["RG"] = rgEditText
        map["endereco"] = enderecoEditText
        map["numeroEndereco"] = numEndEditText
        map["complementoEndereco"] = complementoEditText
        map["bairroEndereco"] = bairroEditText
        map["cidadeEndereco"] = cidadeEditText
        map["estadoEndereco"] = estadoEditText
        map["CEP"] = cepEditText

        var user= FirebaseAuth.getInstance().getCurrentUser();
        var useruid = user?.getUid()
        FirebaseDatabase.getInstance().getReference()
            .child("Users")
            .child(useruid!!)
            .updateChildren(map)
            .addOnSuccessListener {
                val intent = Intent(this, ConfigActivity::class.java)
                Toast.makeText(this, "Dados Alterados com Sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(intent)
        }
            .addOnFailureListener{
                Toast.makeText(this, "Ocorreu um erro. Tente novamente!", Toast.LENGTH_SHORT).show()
            }
    }

    // cep
    private fun getCep(cep: String){
        val url = "https://viacep.com.br/ws/$cep/json/"
        doAsync {
            val url = URL(url)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = 7000
            val content = urlConnection.inputStream.bufferedReader().use(BufferedReader::readText)
            var json = JSONObject(content)
            uiThread {
                if(json.has("erro")){
                    Toast.makeText(this@ProfileUpdateActivity, "Erro no CEP", Toast.LENGTH_SHORT).show()
                }
                else{
                    val cep = json.getString("cep")
                    val logradouro = json.getString("logradouro")
                    val bairro = json.getString("bairro")
                    val cidade = json.getString("localidade")
                    val estado = json.getString("uf")
                    cep_update.text = Editable.Factory.getInstance().newEditable(cep)
                    logradouro_update.text = Editable.Factory.getInstance().newEditable(logradouro)
                    bairro_update.text = Editable.Factory.getInstance().newEditable(bairro)
                    cidade_update.text = Editable.Factory.getInstance().newEditable(cidade)
                    estado_update.text = Editable.Factory.getInstance().newEditable(estado)
                }
            }
        }
    }
}