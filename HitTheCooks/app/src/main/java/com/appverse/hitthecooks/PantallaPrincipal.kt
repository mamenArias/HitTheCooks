package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.appverse.hitthecooks.databinding.ActivityPrincipalBinding

/**
 * Actividad de la pantalla principal desde la que navegar al resto de pantallas
 * @author
 * @since
 */
class PantallaPrincipal : AppCompatActivity() {

    /** Constante que permite enlazar directamente con las vistas del layout **/
    private val binding by lazy { ActivityPrincipalBinding.inflate(layoutInflater) }

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
        startActivity(Intent(this, ShoppingListActivity::class.java))
    }

    /**
     * Función que permite navegar a la pantalla de "Crear Lista"
     */
    fun navigateToCreateAList(view: android.view.View) {
        startActivity(Intent(this, FoodList::class.java))
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
        startActivity(Intent(this, ConfigurationActivity::class.java))
    }

    fun navigateToEditProfile(view: android.view.View) {
        startActivity(Intent(this, EditProfile::class.java))
    }

}