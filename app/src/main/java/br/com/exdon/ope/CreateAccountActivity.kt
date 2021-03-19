package br.com.exdon.ope

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.ProgressBar
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.menu_lateral_cabecalho.*

class CreateAccountActivity : AppCompatActivity() {

    //Elementos da Interface do usuario

    private var getFirstName: EditText? = null
    private var getLastName: EditText? = null
    private var getEmail: EditText? = null
    private var getPasswordRegister: EditText? = null
    //private var getConfirmPasswordRegister: EditText? = null
    private var btnRegister: Button? = null
    private var mProgressBar: ProgressDialog? = null

    //Referencias ao Banco de dados

    private var mDataBaseReference: DatabaseReference? = null
    private var mDataBase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"

    //Variaveis Globais

    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var passwordRegister: String? = null
    //private var confirmPasswordRegister: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialise()
    }
    private fun initialise() {
        getFirstName = findViewById(R.id.first_name) as EditText
        getLastName = findViewById(R.id.last_name) as EditText
        getEmail = findViewById(R.id.email) as EditText
        getPasswordRegister = findViewById(R.id.password_register) as EditText
        //getConfirmPasswordRegister = findViewById(R.id.confirm_password_register) as EditText
        btnRegister = findViewById(R.id.btn_register) as Button
        mProgressBar = ProgressDialog(this)

        mDataBase = FirebaseDatabase.getInstance()
        mDataBaseReference = mDataBase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()

        btnRegister!!.setOnClickListener { createNewAccount() }


    }

    private fun createNewAccount() {
        firstName = getFirstName?.text.toString()
        lastName = getLastName?.text.toString()
        email = getEmail?.text.toString()
        passwordRegister = getPasswordRegister?.text.toString()
        //confirmPasswordRegister = getConfirmPasswordRegister?.text.toString()

        if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(passwordRegister)) {
            Toast.makeText(this, "Informações preenchidas corretamente", Toast.LENGTH_SHORT).show()

        mProgressBar!!.setMessage("Registrando Usuário...")
        mProgressBar!!.show()

        mAuth!!.createUserWithEmailAndPassword(email!!, passwordRegister!!).addOnCompleteListener(this) { task ->
            mProgressBar!!.hide()

            if (task.isSuccessful) {
                Log.d(TAG, "CreateUserWithEmail:Success")

                val userId = mAuth!!.currentUser!!.uid


                //verifica se o usuario confirmou o email
                verifyEmail();

                val currentUserDb = mDataBaseReference!!.child(userId)
                currentUserDb.child("firstName").setValue(firstName)
                currentUserDb.child("lastName").setValue(lastName)
                currentUserDb.child("email").setValue(email)
                currentUserDb.child("contato").setValue("")
                currentUserDb.child("CEP").setValue("")
                currentUserDb.child("CPF").setValue("")
                currentUserDb.child("RG").setValue("")
                currentUserDb.child("bairroEndereco").setValue("")
                currentUserDb.child("cidadeEndereco").setValue("")
                currentUserDb.child("complementoEndereco").setValue("")
                currentUserDb.child("dataNascimento").setValue("")
                currentUserDb.child("endereco").setValue("")
                currentUserDb.child("estadoEndereco").setValue("")
                currentUserDb.child("numeroEndereco").setValue("")

                //atualiza as informações no banco de dados
                updateUserInfoandUi()
            } else {
                Log.w(TAG, "CreateUserWithEmail:Failure", task.exception)
                Toast.makeText(this@CreateAccountActivity, "A autenticação falhou", Toast.LENGTH_SHORT).show()
            }
        }
        } else {
            Toast.makeText(this, "Campos obrigatórios NÃO preenchidos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUserInfoandUi() {
        //inicia uma nova atividade
        val intent = Intent(this@CreateAccountActivity, MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        enviaNotificacao()
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification().addOnCompleteListener(this) {
            task ->
            if (task.isSuccessful) {
                Toast.makeText(this@CreateAccountActivity, "E-mail de verificação enviado para" + mUser.getEmail(),
                    Toast.LENGTH_SHORT).show()
            }else {
                Log.e(TAG, "SendEmailVerification", task.exception)
                Toast.makeText(this@CreateAccountActivity, "Falha no envio de e-mail de verificação!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun enviaNotificacao() {
        val intent = Intent(this, MenuActivity::class.java)
        NotificationUtil.create(1, intent, "Seja bem-vindo(a)!", "É um prazer tê-lo(a) conosco. Não se esqueça de completar seu cadastro, acessando nosso menu > Configurações > Atualizar dados. Maiores informações, entre em contato conosco! ")
        startActivity(intent)
    }
}