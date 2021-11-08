package com.appverse.hitthecooks

import android.app.Dialog
import android.content.Intent
import android.media.tv.TvContract
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.appverse.hitthecooks.databinding.ActivityConfigurationBinding
import com.appverse.hitthecooks.databinding.ActivityMainBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ConfigurationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityConfigurationBinding.inflate(layoutInflater) }
    private val buttonProfile : Button by lazy { binding.buttonProfile }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /** Botón para cambiar de pantalla **/
        buttonProfile.setOnClickListener {
            startActivity(Intent(this@ConfigurationActivity, EditProfile::class.java))
        }

        binding.buttonLogOut.setOnClickListener {
            val builder =  AlertDialog.Builder(this)
            builder.setView(R.layout.loading_progress_bar)
            builder.create()
            val alertDialog = builder.show()
            alertDialog.window?.setLayout(400,400)
            Firebase.auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}