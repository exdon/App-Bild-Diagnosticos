package br.com.exdon.ope


import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.common.internal.service.Common
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.navigation_menu.*

open class DebugActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    //lateinit var preferences: SharedPreferences

    val TAG = "OPEApp"
    private val className: String
        get() {
            val s = javaClass.name
            return s.substring(s.lastIndexOf("."))
        }

    var generic_layout : DrawerLayout? = null

    // Firebase auth logout add on 6/11
    var myAuth  = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, className + ".onCreate() chamado")
    }

    protected fun configuraMenuLateral() {
        var toogle = ActionBarDrawerToggle(
            this,
            generic_layout,
            toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        generic_layout?.addDrawerListener(toogle)
        toogle.syncState()

        menu_lateral.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem) : Boolean {
        when(item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_about_us -> {
                val intent = Intent(this, AboutUsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_unidades -> {
                val intent = Intent(this, UnidadeActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_exam -> {
                var intent = Intent(this, ExamesActivity::class.java)
                var titleOpcoes = Bundle()
                titleOpcoes.putString("title", "EXAMES")
                intent.putExtras(titleOpcoes)
                startActivity(intent)
            }
            R.id.nav_meus_exames -> {
                val intent = Intent(this, MeusExamesActivity2::class.java)
                startActivity(intent)
            }
            R.id.nav_agendamento -> {
                var intent = Intent(this, OpcoesAgendamentoActivity::class.java)
                var titleOpcoes = Bundle()
                titleOpcoes.putString("title", "AGENDAMENTO")
                intent.putExtras(titleOpcoes)
                startActivity(intent)
            }
            R.id.nav_fale_conosco -> {
                var intent = Intent(this, FaleConoscoActivity::class.java)
                var titleOpcoes = Bundle()
                titleOpcoes.putString("title", "FALE CONOSCO")
                intent.putExtras(titleOpcoes)
                startActivity(intent)
            }
            R.id.nav_config -> {
                var intent = Intent(this, ConfigActivity::class.java)
                var titleOpcoes = Bundle()
                titleOpcoes.putString("title", "CONFIGURAÇÕES")
                intent.putExtras(titleOpcoes)
                startActivity(intent)
                /*val intent = Intent(this, MeusExamesActivity::class.java)
                Toast.makeText(this, "Menu Meus Exames", Toast.LENGTH_SHORT).show()
                startActivity(intent)*/

            }
            /*
            R.id.nav_localizacao -> {
                var intent = Intent(this, MapasActivity::class.java)
                var titleOpcoes = Bundle()
                titleOpcoes.putString("title", "LOCALIZAÇÃO")
                intent.putExtras(titleOpcoes)
                startActivity(intent)
                Toast.makeText(this, "Menu Localização", Toast.LENGTH_SHORT).show()
            }

             */
            R.id.nav_logout -> {
                val builder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
                builder.setTitle("Saída do App")
                builder.setIcon(R.drawable.ic_warning)
                builder.setMessage("Você deseja sair do aplicativo?")
                builder.setPositiveButton("Sim") { dialog, which ->
                    var intent = Intent(this, LoginActivity::class.java)
                    myAuth.signOut()
                    startActivity(intent)
                    //finish()
                }
                builder.setNegativeButton("Não") {dialog, which ->}
                val dialog: AlertDialog = builder.create()
                dialog.show()
                /*val returnIntent = Intent();
                returnIntent.putExtra("result", "Saída do BrewerApp");
                setResult(RESULT_OK, returnIntent);
                finish();*/
            }
        }
        myAuth.addAuthStateListener {
            if (myAuth.currentUser == null) {
                this.finish()
            }
        }

        generic_layout?.closeDrawer(GravityCompat.START)

        return true
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, className + ".onStart() chamado")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, className + ".onRestart() chamado")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, className + ".onResume() chamado")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, className + ".onPause() chamado")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, className + ".onStop() chamado")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, className + ".onDestroy() chamado")
    }


}