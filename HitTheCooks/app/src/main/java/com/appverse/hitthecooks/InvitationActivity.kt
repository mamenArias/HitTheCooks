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

class InvitationActivity : AppCompatActivity() {
    private val binding by lazy { ActivityInvitationBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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

        binding.invitationUrl.text = link.uri.toString()

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
        binding.copyToClipboardButton.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("link",binding.invitationUrl.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, R.string.copiedToClipboard, Toast.LENGTH_SHORT).show()
        }
        binding.createListButton.setOnClickListener {
            startActivity(Intent(this,FoodList::class.java))
        }
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