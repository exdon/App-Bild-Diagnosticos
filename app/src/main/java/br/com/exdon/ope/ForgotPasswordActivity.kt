package br.com.exdon.ope

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private val TAG = "ForgotPasswordActivity"

    //elementos da interface UI

    private var userLogin: EditText? = null
    private var btnSubmit: Button? = null

    //referencia do banco de dados

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        inicialise()
    }

    private fun inicialise() {
        userLogin = findViewById(R.id.login_user) as EditText
        btnSubmit = findViewById(R.id.btn_submit) as Button

        mAuth = FirebaseAuth.getInstance()

        btnSubmit!!.setOnClickListener{ sendPasswordEmail() }
    }

    private fun sendPasswordEmail() {
        val email = userLogin?.text.toString()

        if (!TextUtils.isEmpty(email)) {
            mAuth!!
                .sendPasswordResetEmail(email)
                .addOnCompleteListener{ task ->
                    if (task.isSuccessful){
                        val message = "E-mail Enviado com Sucesso!"
                        Log.d(TAG, message)
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                        updateUI()
                    } else {
                        Log.w(TAG, task.exception!!.message.toString())
                        Toast.makeText(this, "Nenhum Usuário encontrado com esse e-mail!", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Entre com um e-mail válido.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}