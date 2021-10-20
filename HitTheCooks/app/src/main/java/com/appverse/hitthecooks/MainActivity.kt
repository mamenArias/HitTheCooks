package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appverse.hitthecooks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun navigateToMyLists(view: android.view.View) {

    }

    fun navigateToCreateAList(view: android.view.View) {

    }

    fun navigateToAboutUs(view: android.view.View) {
        startActivity(Intent(this, AboutUs::class.java))
    }

    fun navigateToConfiguration(view: android.view.View) {

    }

}