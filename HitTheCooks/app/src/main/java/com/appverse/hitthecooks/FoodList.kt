package com.appverse.hitthecooks

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.appverse.hitthecooks.databinding.ActivityFoodListBinding
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class FoodList : SuperActivity() {

    private val binding by lazy { ActivityFoodListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        drawerLayout.addView(binding.root, 1)

        applyDarkMode(binding.root)

        binding.buttonBack.setOnClickListener {
           startActivity(Intent(this,ShoppingListActivity::class.java))
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

    override fun onBackPressed() {
        startActivity(Intent(this,ShoppingListActivity::class.java))
    }




}