package com.appverse.hitthecooks

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.lang.Exception

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
            //applyWhiteTexts(rootView)
            if(rootView.context is ConfigurationActivity){
                val text : TextView = findViewById(R.id.labelConfiguration)
                text.setTextColor(ContextCompat.getColor(this, R.color.whiteEgg))
            }
        }
    }
    
    private fun applyWhiteTexts (view: ViewGroup) : Unit {
        for (i in 0 until view.childCount) {
            val child: View = view.getChildAt(i)
            if (child is ViewGroup) {
                applyWhiteTexts(child)
            } else {
                try {
                    (child as TextView).setTextColor(ContextCompat.getColor(this, R.color.white))
                } catch (e: Exception) {

                }
            }
        }
    }


}