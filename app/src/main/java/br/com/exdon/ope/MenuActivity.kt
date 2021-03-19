package br.com.exdon.ope

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.menu_lateral_cabecalho.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class MenuActivity : DebugActivity() {

    var refUser: DatabaseReference? = null
    var firebaseUser: FirebaseUser? = null

    class User(
        val firstName: String = "",
        val lastName: String = "",
        val profileImageUrl: String = ""
    )


    private val context: Context get() = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        this.generic_layout = layout_menu_lateral

        // acessar parametros da intnet
        // intent é um atributo herdado de Activity
        val args = intent.extras
        // recuperar o parâmetro do tipo String
       val nome = args?.getString("nome")

        // recuperar parâmetro simplificado
        //val numero = intent.getIntExtra("nome", 0)

        //Toast.makeText(context, "Parâmetro: $nome", Toast.LENGTH_LONG).show()
        //Toast.makeText(context, "Numero: $numero", Toast.LENGTH_LONG).show()

        //mensagemInicial.text = "Bem vindo !"

        //var txtBtnExame = botaoExame.text.toString()
        //var txtBtnAgendamento = botaoAgendamento.text.toString()
        //var txtBtnContact = botaoContact.text.toString()

        firebaseUser = FirebaseAuth.getInstance().currentUser
        refUser = FirebaseDatabase.getInstance().reference.child("User").child(firebaseUser!!.uid)


        // msg Saudação com nome do usuario atual
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val rootRef = FirebaseDatabase.getInstance().reference
        val uidRef = rootRef.child("Users").child(uid)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                    Log.d(TAG, "HELLO" + user!!.firstName)
                    val saudacao = user.firstName + " " + user.lastName
                    val displayName = "Olá, " + user.firstName
                    nameUser.text = saudacao
                    textGrid.text = displayName
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d(TAG, databaseError.message) //Don't ignore errors!
            }
        }
        uidRef.addListenerForSingleValueEvent(valueEventListener)



        // add on 6/11
        val user = FirebaseAuth.getInstance().currentUser

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName("Jane Q. User")
            .setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))
            .build()

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User profile updated.")
                }
            }

        /*
        btn_img_user.setOnClickListener{
            Toast.makeText(this, "Clicou photo", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)

            val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
            builder.setTitle("Salvar Foto")
            builder.setIcon(R.drawable.ic_warning)
            builder.setMessage("Você deseja salvar essa foto?")
            builder.setPositiveButton("Sim") { dialog, which ->
                var intent = Intent(this, MenuActivity::class.java)
                uploadImageToFirebaseStorage()
                startActivity(intent)
            }
            builder.setNegativeButton("Não") {dialog, which ->}
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }


         */

         */

        cardview_agendamento.setOnClickListener{
            var intent = Intent(this, OpcoesAgendamentoActivity::class.java)
            var titleOpcoes = Bundle()
            titleOpcoes.putString("title", "AGENDAMENTO")
            intent.putExtras(titleOpcoes)
            startActivity(intent)
        }

        cardview_unidades.setOnClickListener{
            var intent = Intent(this, UnidadeActivity::class.java)
            var titleOpcoes = Bundle()
            titleOpcoes.putString("title", "UNIDADES")
            intent.putExtras(titleOpcoes)
            startActivity(intent)
        }
        cardview_exames.setOnClickListener{
            var intent = Intent(this, ExamesActivity::class.java)
            var titleOpcoes = Bundle()
            titleOpcoes.putString("title", "EXAMES")
            intent.putExtras(titleOpcoes)
            startActivity(intent)
        }
        cardview_faleConosco.setOnClickListener{
            var intent = Intent(this, FaleConoscoActivity::class.java)
            var titleOpcoes = Bundle()
            titleOpcoes.putString("title", "FALE CONOSCO")
            intent.putExtras(titleOpcoes)
            startActivity(intent)
        }


        // alterar título da ActionBar
        supportActionBar?.title = "TESTE TESTE TES "

        // colocar toolbar
        setSupportActionBar(toolbar)

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        configuraMenuLateral()
    }

    // mostrar o progress bar
   class NetworkTask(var activity: MenuActivity) : AsyncTask<Void, Void, Void>() {

       var dialog = Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar)

       override fun onPreExecute() {
           val view = activity.layoutInflater.inflate(R.layout.progress_bar, null)
           dialog.setContentView(view)
           dialog.setCancelable(false)
           dialog.show()
           super.onPreExecute()
       }
       override fun doInBackground(vararg params: Void?): Void? {
           Thread.sleep(10000)
           return null
       }

       override fun onPostExecute(result: Void?) {
           super.onPostExecute(result)
           dialog.dismiss()
       }

   }

    private fun openNextActivity() {
        val intent = Intent(this, SegundaActivity::class.java)
        startActivity(intent)
    }

    fun cliqueSair() {
        val returnIntent = Intent();
        returnIntent.putExtra("result", "Saída do BrewerApp");
        setResult(RESULT_OK, returnIntent);
        finish();
    }
    /*
    // add on 17/11
    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was..
            Toast.makeText(this, "Foto foi selecionada", Toast.LENGTH_SHORT).show()

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            val bitmapDrawable = BitmapDrawable(bitmap)
            btn_img_user.setBackgroundDrawable(bitmapDrawable)

        }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val uTask = FirebaseDatabase.getInstance().getReference("Users/$uid")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Toast.makeText(this, "Foto salva com sucesso!", Toast.LENGTH_SHORT).show()

                savePhotoToFirebaseDatabasde(it.toString())
            }.addOnFailureListener{
                Toast.makeText(this, "Falha ao salvar foto", Toast.LENGTH_SHORT).show()
            }
    }

    private fun savePhotoToFirebaseDatabasde(profileImageUrl: String){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")

        //val user = User("","", profileImageUrl)

        ref.setValue(profileImageUrl)
            .addOnSuccessListener {
                Toast.makeText(this, "Foto salva no firebase", Toast.LENGTH_SHORT).show()
            }
    }

    // fim 17/11

     */


    // método sobrescrito para inflar o menu na Actionbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // infla o menu com os botões da ActionBar
        menuInflater.inflate(R.menu.menu_main, menu)
        // vincular evento de buscar
        /*
        (menu?.findItem(R.id.action_buscar)?.actionView as SearchView?)?.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String): Boolean {
                    // ação enquanto está digitando
                    return false
                }

                override fun onQueryTextSubmit(query: String): Boolean {
                    // ação  quando terminou de buscar e enviou
                    return false
                }

            })

         */
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // id do item clicado
        val id = item?.itemId
        // verificar qual item foi clicado e mostrar a mensagem Toast na tela
        // a comparação é feita com o recurso de id definido no xml
        /*
        if  (id == R.id.action_buscar) {
            Toast.makeText(context, "Botão de buscar", Toast.LENGTH_LONG).show()
        }else if (id == R.id.action_atualizar) {
            NetworkTask(this).execute()
            Toast.makeText(context, "Botão de atualizar", Toast.LENGTH_LONG).show()
         */
        if (id == R.id.action_versao) {
            val ite = Intent(this, VersaoActivity::class.java)
            startActivity(ite)
            // botão up navigation
        } else if (id == android.R.id.home) {
            finish()
        } else if (id == R.id.action_config) {
            val it = Intent(this, ConfigActivity::class.java)
            startActivity(it)
        }
        return super.onOptionsItemSelected(item)
    }

    //add on 27/10
    private fun DatabaseError.message() {

    }
/*
    fun enviaNotificacao(agendamento: Agendamento) {
        val intent = Intent(this, NovoExameActivity::class.java)
        intent.putExtra("agendamento", agendamento)

        NotificationUtil.create(1, intent, "ope", "Você tem pendências na ${agendamento.exame}")
    }

 */

}


