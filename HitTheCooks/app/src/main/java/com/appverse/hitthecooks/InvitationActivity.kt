package com.appverse.hitthecooks

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.appverse.hitthecooks.databinding.ActivityInvitationBinding
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

/**
 * Actividad que contiene la pantalla de "invitación"
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 */
class InvitationActivity : SuperActivity() {
    /** Objeto que permite enlazar las vistas del layout **/
    private val binding by lazy { ActivityInvitationBinding.inflate(layoutInflater) }

    /**
     * Inicializa la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Infla la vista en el layout de la actividad superior
        drawerLayout.addView(binding.root, 1)
        //Aplica el modo oscuro en caso de que esté activado
        applyDarkMode(binding.root)

        //Recupera el id de la lista del intent de la actividad
        val listId : String = intent.extras?.getString("listId") as String

        //Creación del link para invitación.En él, se inserta el id de la lista
        val builder =  FirebaseDynamicLinks.getInstance()
            .createDynamicLink()
            .apply {
                domainUriPrefix = "https://hitthecooks.page.link"
                link = Uri.parse("https://hitthecooks.page.link/invite?list=$listId")
                setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
          }

            val link = builder.buildDynamicLink()

        //Muestra la url de la invitación
        binding.invitationUrl.text = link.uri.toString()

        /**
         * Envia una invitación a unirse a la lista de la compra
         */
        binding.shareImageView.setOnClickListener {
            val image : Uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/hit-the-cooks.appspot.com/o/hitthecooks%2Flogo_hitthecooks.png?alt=media&token=4dad97d4-2416-441c-997e-a5d28f3e16fe")
            var share: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                data = image
                putExtra(Intent.EXTRA_TITLE,"Hit the cooks")
                clipData = ClipData.newRawUri("",image)
                putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.invitationMessage)+"\n"+link.uri.toString())
                type="text/*"
            }
            share = Intent.createChooser(share,null)
            startActivity(share)
        }

        /**
         * Copia la url con la invitación a unirse a la lista
         */
        binding.copyToClipboardButton.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("link",binding.invitationUrl.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, R.string.copiedToClipboard, Toast.LENGTH_SHORT).show()
        }

        /**
         * Permite navegar a la actividad de la lista de productos
         */
        binding.createListButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("listId",listId)
            startActivity(Intent(this,FoodList::class.java).putExtras(bundle))
        }

        /**
         * Función que envia una invitación a compartir la lista a través de Whatsapp
         */
        binding.whatsappImageView.setOnClickListener {
            var shareWhatsapp : Intent = Intent().apply {
                action = Intent.ACTION_SEND
                `package` = "com.whatsapp"
                putExtra(Intent.EXTRA_TITLE,"Hit the cooks")
                putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.invitationMessage)+"\n"+link.uri.toString())
                type="text/*"
            }
            shareWhatsapp = Intent.createChooser(shareWhatsapp,null)
            try {
                startActivity(shareWhatsapp)
            }catch ( e : ActivityNotFoundException){
                Toast.makeText(this, R.string.whatsappNotInstalled, Toast.LENGTH_SHORT).show()
            }
        }

    }

    /***
     * No permite ir hacia atrás
     */
    override fun onBackPressed() {
    }
}