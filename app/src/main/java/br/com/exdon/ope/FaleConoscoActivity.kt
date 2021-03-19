package br.com.exdon.ope

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.actionbar.*
import kotlinx.android.synthetic.main.activity_fale_conosco.*
import kotlinx.android.synthetic.main.escolher_location_dialog.view.*


class FaleConoscoActivity : AppCompatActivity() {
    lateinit var option: Spinner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fale_conosco)

        //this.generic_layout = layout_menu_lateral

        // colocar toolbar
        setSupportActionBar(toolbar)


        // alterar título da ActionBar
        supportActionBar?.title = "FALE CONOSCO"

        // up navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //configuraMenuLateral()

        /*
        // Spinner Localização Unidade
        option = findViewById(R.id.localizacaoSpinner) as Spinner

        val optionsLocation = arrayOf(
            "Selecione",
            "Unidade 1 - Samarianto Campinas",
            "Unidade 2 - Samarianto Campinas",
            "Samaritano Hortolândia",
            "Shopping Hortolândia",
            "Hospital Stª Ignês Indaiatuba"
        )
        option.adapter =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsLocation)

         */

        card_whatsapp.setOnClickListener{
            openWhatsapp()
        }

        card_phoneNumber.setOnClickListener{
            openCall()
        }

        card_email.setOnClickListener{
            val email = "contato@bilddiagnosticos.com.br"

            sendEmail(email)
        }

        card_presencial.setOnClickListener{
            // Infalte the dialog with custom view
            val mDialogView = LayoutInflater.from(this).inflate(
                R.layout.escolher_location_dialog,
                null
            )
            // AlertDialogBuilder
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("PREENCHA AS INFORMAÇÕES")
            // show dialog
            val mAlertDialog = mBuilder.show()

            // Spinner Localização Unidade
            option = mDialogView.localizacaoSpinner
            val optionsLocation = arrayOf(
                "Selecione",
                "Unidade 1 - Samarianto Campinas",
                "Unidade 2 - Samarianto Campinas",
                "Samaritano Hortolândia",
                "Shopping Hortolândia",
                "Hospital Stª Ignês Indaiatuba"
            )
            option.adapter =
                ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, optionsLocation)

            // button click
            mDialogView.dialogLocationBtn.setOnClickListener{
                //val unidadeLocation = findViewById<View>(R.id.localizacaoSpinner) as Spinner
                //val unidadeLocationString = unidadeLocation.selectedItem.toString().trim()
                val unidadeLocation = mDialogView.localizacaoSpinner.selectedItem.toString()
                val enderecoAtual = mDialogView.enderecoAtual.text.toString()
                if (!enderecoAtual.isEmpty()){
                    if (unidadeLocation == "Shopping Hortolândia"){
                        val address = "R.Jose Camilo de Camargo, 5"
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=${enderecoAtual}&daddr=${address}")
                        )
                        startActivity(intent)
                    } else if (unidadeLocation == "Unidade 1 - Samarianto Campinas"){
                        val address = "R. Engenheiro Monlevade, 206"
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=${enderecoAtual}&daddr=${address}")
                        )
                        startActivity(intent)
                    } else if (unidadeLocation == "Unidade 2 - Samarianto Campinas") {
                        val address = "Av. São José dos Campos, 256"
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=${enderecoAtual}&daddr=${address}")
                        )
                        startActivity(intent)
                    } else if (unidadeLocation == "Samaritano Hortolândia") {
                        val address = "R. Osvaldo Silva, 10"
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=${enderecoAtual}&daddr=${address}")
                        )
                        startActivity(intent)
                    } else if (unidadeLocation == "Hospital Stª Ignês Indaiatuba") {
                        val address = "Av. Presidente Vargas, 1591"
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?saddr=${enderecoAtual}&daddr=${address}")
                        )
                        startActivity(intent)
                    } else if (unidadeLocation == "Selecione"){
                        Toast.makeText(this, "Selecione uma unidade!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Digite seu endereço atual!", Toast.LENGTH_SHORT).show()
                }
                /*
                var intent = Intent(this, MapasActivity::class.java)
                var titleOpcoes = Bundle()
                titleOpcoes.putString("title", "LOCALIZAÇÃO")
                intent.putExtras(titleOpcoes)
                intent.putExtra("locationSelected", unidadeLocation)
                startActivity(intent)

                 */
            }
            // cancel button
            mDialogView.dialogLocationCancelBtn.setOnClickListener{
                mAlertDialog.dismiss()
            }
        }
    }

    private fun openWhatsapp() {
        /*val numero = number_whatsapp.text.toString()
        val message = "Olá, estou precisando de ajuda!"

        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, message)
        sendIntent.type = "text/plain"
        sendIntent.setPackage("com.whatsapp")
        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(sendIntent)
        }*/
        /*try {
            val headerReceiver = "Teste" // Replace with your message.
            val bodyMessageFormal = "Teste 2" // Replace with your message.
            val whatsappcontain= headerReceiver + bodyMessageFormal
            val trimToNumner = "+5511960702182" //10 digit number
            val intent = Intent(Intent.ACTION_VIEW)
            intent.putExtra(Intent
            intent.data = Uri.parse("https://wa.me/$trimToNumner/?text=")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }*/
        val number = "+5519998005464"
        val message = "Olá, gostaria de maiores informações sobre os serviços da Bild Diagnósticos!"
        //val url = "https://api.whatsapp.com/send?phone=$number"
        val url = String.format("https://api.whatsapp.com/send?phone=%s&text=%s", number, message)
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun openCall() {
        val numberContact = "01938094343"
        val uri = Uri.parse("tel:" + numberContact)
        val intent = Intent(Intent.ACTION_DIAL, uri)
        startActivity(intent)
    }

    private fun sendEmail(email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        // email is put as array because you may wanna send email to multiple emails so enter comma(,) separated emails, it will be stored array
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))


        try{
            startActivity(Intent.createChooser(intent, "Escolha uma opção de Email Client..."))
        }
        catch (e: Exception) {
            Toast.makeText(this, "Não foi possível concluir sua solicitação!", Toast.LENGTH_SHORT).show()
        }

    }
}