package com.appverse.hitthecooks

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import java.lang.Exception

/**
 * Actividad abstracta superior de la qe heredan el resto de actividades excepto el login
 * Contiene el archivo de preferencias y las funciones del modo oscuro
 */
abstract class SuperActivity : AppCompatActivity() {

    /** Gestiona las preferencias de la app **/
    lateinit var preferences : SharedPreferences

    /**
     * Inicializa la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * Función que aplica el modo oscuro en el caso de que el valor del mismo en el archivo de preferencias
     * sea verdadero. Cambia el color de fondo de la vista raiz del layout y pone los textos en color blanco
     */
    fun applyDarkMode(rootView : ViewGroup) : Unit {
        //Inicializa el archivo de preferencias
        preferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        //Consulta el valor del modo oscuro
        val darkMode : Boolean = preferences.getBoolean("darkMode", false)
        if(darkMode){
            //Cambia el color de fondo de la vista raiz
            rootView.setBackgroundColor(ContextCompat.getColor(this, R.color.darkBlue))
            //applyWhiteTexts(rootView)
            if(rootView.context is ConfigurationActivity){
                //Cambia el color del titulo y el icono del modo dia/noche si es la actividad de configuración
                val text : TextView = findViewById(R.id.labelConfiguration)
                text.setTextColor(ContextCompat.getColor(this, R.color.whiteEgg))
                val switchText : TextView = findViewById(R.id.labelTheme)
                switchText.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sun_icon, 0, 0, 0)
            } else if(rootView.context is PantallaPrincipal){
                //Cambia el logo de la app si es la actividad principal
                val logo : ImageView = findViewById(R.id.appLogo)
                logo.setImageResource(R.drawable.logo_hit_the_cook_blanco)
            }
        }
    }

    /**
     * Función recursiva que recorre los elementos hijos del ViewGroup raiz y, si es un texto,
     * le aplica un color claro
     */
    private fun applyWhiteTexts (view: ViewGroup) : Unit {
        for (i in 0 until view.childCount) {
            val child: View = view.getChildAt(i)
            if (child is ViewGroup) {
                applyWhiteTexts(child)
            } else {
                try {
                    (child as TextView).setTextColor(ContextCompat.getColor(this, R.color.whiteEgg))
                } catch (e: Exception) {

                }
            }
        }
    }


}