package com.appverse.hitthecooks

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import com.appverse.hitthecooks.databinding.ActivityInvitationBinding
import com.appverse.hitthecooks.model.ShoppingList
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.firebase.dynamiclinks.ktx.androidParameters
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.dynamiclinks.ktx.shortLinkAsync
import com.google.firebase.ktx.Firebase

/**
 * Actividad que contiene la pantalla de "invitación"
 * @author Miguel Angel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian Gracia
 * @author Sergio Lopez
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
        drawerLayout.addView(binding.root, 1)
        //Aplica el modo oscuro en caso de que esté activado
        applyDarkMode(binding.root)

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