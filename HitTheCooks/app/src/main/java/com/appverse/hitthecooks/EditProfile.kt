package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appverse.hitthecooks.databinding.ActivityEditProfileBinding
import com.appverse.hitthecooks.databinding.ActivityMainBinding

class EditProfile : SuperActivity() {

    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        applyDarkMode(binding.root)

        binding.goBackButton.setOnClickListener {
            onBackPressed()
        }
    }

}