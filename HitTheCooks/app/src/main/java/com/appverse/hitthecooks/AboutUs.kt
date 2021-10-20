package com.appverse.hitthecooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appverse.hitthecooks.databinding.ActivityAboutUsBinding
import com.appverse.hitthecooks.databinding.ActivityMainBinding

class AboutUs : AppCompatActivity() {

    private val binding by lazy { ActivityAboutUsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}