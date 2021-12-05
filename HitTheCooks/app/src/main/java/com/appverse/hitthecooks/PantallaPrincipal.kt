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
class PantallaPrincipal : SuperActivity() {

    /** Constante que permite enlazar directamente con las vistas del layout **/
    private val binding by lazy { ActivityPrincipalBinding.inflate(layoutInflater) }

    /**
     * Inicializa la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        applyDarkMode(binding.root)

        /**
         * Función que permite navegar a la pantalla de configuración
         */
        binding.configButton.setOnClickListener {
            startActivity(Intent(this, ConfigurationActivity::class.java))
        }

        /**
         * Función que permite navegar a la pantalla de editar perfil
         */
        binding.userButton.setOnClickListener {
            startActivity(Intent(this, EditProfile::class.java))
        }

        /**
         * Función que permite navegar a la pantalla de "Mis Listas"
         */
        binding.myListsButton.setOnClickListener {
            startActivity(Intent(this, ShoppingListActivity::class.java))
        }

        /**
         * Función que permite navegar a la pantalla de "Crear una Lista"
         */
        binding.createAListButton.setOnClickListener {
            startActivity(Intent(this, FoodList::class.java))
        }

        /**
         * Función que permite navegar a la pantalla de "Sobre Nosotros"
         */
        binding.aboutUsButton.setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }

    }

}