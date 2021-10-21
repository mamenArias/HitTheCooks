package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appverse.hitthecooks.databinding.ActivityMainBinding

/**
 * Actividad de la pantalla principal desde la que navegar al resto de pantallas
 * @author Manuel Carrillo
 * @since 1.0
 */
class MainActivity : AppCompatActivity() {

    /** Constante que permite enlazar directamente con las vistas del layout **/
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    /**
     * Inicializa la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    /**
     * Función que permite navegar a la pantalla de "Mis Listas"
     */
    fun navigateToMyLists(view: android.view.View) {

    }

    /**
     * Función que permite navegar a la pantalla de "Crear Lista"
     */
    fun navigateToCreateAList(view: android.view.View) {

    }

    /**
     * Función que permite navegar a la pantalla de "Sobre Nosotros"
     */
    fun navigateToAboutUs(view: android.view.View) {
        startActivity(Intent(this, AboutUs::class.java))
    }

    /**
     * Función que permite navegar a la pantalla de configuración
     */
    fun navigateToConfiguration(view: android.view.View) {

    }

}