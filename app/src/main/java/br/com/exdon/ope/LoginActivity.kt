package br.com.exdon.ope

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_create_account.view.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.menu_lateral_cabecalho.*
import kotlinx.android.synthetic.main.menu_lateral_cabecalho.view.*

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //variaveis globais

    private var user: String? = null
    private var password: String? = null

    //elementos da interface UI

    private var forgotPassword: TextView? = null
    private var userLogin: TextView? = null
    private var userPassword: TextView? = null
    private var btnLogin: TextView? = null
    private var btnCreateAccount: TextView? = null
    private var mProgressBar: ProgressDialog? = null
    private var checkbox: CheckBox? = null



    //referencias ao banco de dados

    private var mAuth: FirebaseAuth? = null

    // SharedPreferences
    lateinit var sharedPreferences: SharedPreferences
    lateinit var preferences: SharedPreferences
    var isRemembered = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // SharredPreferences Lembrar Usuario e Senha
        login_user.setText(Prefs.getString("lembrarNome"))
        password_user.setText(Prefs.getString("lembrarSenha"))
        checkboxRememberMe.isChecked = Prefs.getBoolean("lembrar")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColorTo(R.color.colorPrimary)
        }

        initialise()

        /*
        // SharedPreferences CheckBox
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false)
        val checked: Boolean = checkboxRememberMe.isChecked

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("USER", user)
        editor.putString("PASSWORD", password)
        editor.putBoolean("CHECKBOX", checked)
        editor.apply() */

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun Window.setStatusBarColorTo(color: Int) {
        this.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

        this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.statusBarColor = ContextCompat.getColor(baseContext, color)
    }

    private fun initialise(){
        forgotPassword = findViewById(R.id.forgot_password) as TextView
        userLogin = findViewById(R.id.login_user) as EditText
        userPassword = findViewById(R.id.password_user) as EditText
        btnLogin = findViewById(R.id.btn_login) as Button
        btnCreateAccount = findViewById(R.id.btn_register_account) as Button
        mProgressBar = ProgressDialog(this)
        checkbox = findViewById(R.id.checkboxRememberMe)

        mAuth = FirebaseAuth.getInstance()
        forgotPassword!!.setOnClickListener{startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))}

        btnCreateAccount!!.setOnClickListener{startActivity(Intent(this@LoginActivity, CreateAccountActivity::class.java))}

        btnLogin!!.setOnClickListener{ loginUser() }

    }

    //------ LOGIN PROFESSOR -----------
    // USUARIO: professor@impacta.com.br
    // SENHA: impacta


    private fun loginUser() {
        user = userLogin?.text.toString()
        password = userPassword?.text.toString()

        val valorUsuario = login_user.text.toString()
        val valorSenha = password_user.text.toString()

        Prefs.setBoolean("lembrar", checkboxRememberMe.isChecked)
        if (checkboxRememberMe.isChecked) {
            Prefs.setString("lembrarNome", valorUsuario)
            Prefs.setString("lembrarSenha", valorSenha )
        } else {
            Prefs.setString("lembrarNome", "")
            Prefs.setString("lembrarSenha", "")
        }

        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)) {
            mProgressBar!!.setMessage("Autenticando Usuário")
            mProgressBar!!.show()

            Log.d(TAG, "Login do usuário")

            mAuth!!.signInWithEmailAndPassword(user!!, password!!).addOnCompleteListener(this) {
                task ->

                mProgressBar!!.hide()

                //Autenticando usuário, atualizando UI com as informações de login

                if (task.isSuccessful) {
                    Log.d(TAG, "Login Bem-sucedido!")
                    updateUi()
                } else {
                    Log.e(TAG, "Autenticação Falhou", task.exception)
                    Toast.makeText(this@LoginActivity, "Usuário ou Senha Incorretos", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Campos obrigatórios NÃO preenchidos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUi() {
        val intent = Intent(this@LoginActivity, MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}