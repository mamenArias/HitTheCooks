package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appverse.hitthecooks.databinding.ActivityAboutUsBinding

/**
 * Clase que contiene el reclicler con los miembros del grupo
 * @author
 * @since
 */
class AboutUs : AppCompatActivity() {

    /** Constante que permite enlazar directamente con las vistas del layout **/
    private val binding by lazy { ActivityAboutUsBinding.inflate(layoutInflater) }

    /**
     * Inicializa las vistas
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    /**
     * Función que permite navegar a la pantalla principal
     */
    fun navigateToMain(view: android.view.View) {
        startActivity(Intent(this, PantallaPrincipal::class.java))
    }
}