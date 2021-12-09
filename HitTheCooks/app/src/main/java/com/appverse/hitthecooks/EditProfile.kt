package com.appverse.hitthecooks

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.appverse.hitthecooks.databinding.ActivityEditProfileBinding
import com.appverse.hitthecooks.databinding.ActivityMainBinding

class EditProfile : SuperActivity() {

    private val binding by lazy { ActivityEditProfileBinding.inflate(layoutInflater) }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_profile)

        applyDarkMode(binding.root)

        binding.profileIcon.setImageResource(R.id.profileIcon)

        binding.goBackButton.setOnClickListener {
            val intent: Intent = Intent(this, PantallaPrincipal::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }

}