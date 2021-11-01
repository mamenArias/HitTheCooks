package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.appverse.hitthecooks.databinding.ActivityConfigurationBinding
import com.appverse.hitthecooks.databinding.ActivityMainBinding

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
    }
}