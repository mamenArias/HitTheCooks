package com.appverse.hitthecooks

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appverse.hitthecooks.databinding.ActivityInvitationBinding
import com.appverse.hitthecooks.model.ShoppingList
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
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            val body = "Hola"
            val sub = link.uri.toString()
            intent.putExtra(Intent.EXTRA_TEXT,body)
            intent.putExtra(Intent.EXTRA_TEXT,sub)
            startActivity(Intent.createChooser(intent,"Share"))
        }
        binding.copyToClipboardButton.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("link",binding.invitationUrl.text.toString())
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, R.string.copiedToClipboard, Toast.LENGTH_SHORT).show()
        }


    }

    override fun onBackPressed() {
    }
}