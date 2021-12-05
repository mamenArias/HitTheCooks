package com.appverse.hitthecooks

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat

abstract class SuperActivity : AppCompatActivity() {

    /** Gestiona las preferencias de la app **/
    lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun applyDarkMode(rootView : ViewGroup) : Unit {
        preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val darkMode : Boolean = preferences.getBoolean("darkMode", false)
        if (rootView.context is PantallaPrincipal){

        }
        if(darkMode){
            rootView.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }
    }



}