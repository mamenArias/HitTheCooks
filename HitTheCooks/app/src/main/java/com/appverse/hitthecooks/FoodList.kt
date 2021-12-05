package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.appverse.hitthecooks.databinding.ActivityFoodListBinding

class FoodList : SuperActivity() {

    private val binding by lazy { ActivityFoodListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        applyDarkMode(binding.root)

        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }

        binding.buttonUser.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            startActivity(intent)
        }

        binding.buttonConfiguration.setOnClickListener {
            val intent = Intent(this, ConfigurationActivity::class.java)
            startActivity(intent)
        }
    }




}