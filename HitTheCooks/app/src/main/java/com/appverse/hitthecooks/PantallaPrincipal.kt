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
        //setContentView(binding.root)
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_main)

        applyDarkMode(binding.root)

        /**
         * Función que permite navegar a la pantalla de configuración
         */
        binding.configButton.setOnClickListener {
            val intent:Intent = Intent(this, ConfigurationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        /**
         * Función que permite navegar a la pantalla de editar perfil
         */
        binding.userButton.setOnClickListener {
            val intent:Intent = Intent(this, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        /**
         * Función que permite navegar a la pantalla de "Mis Listas"
         */
        binding.myListsButton.setOnClickListener {
            val intent:Intent = Intent(this, ShoppingListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        /**
         * Función que permite navegar a la pantalla de "Sobre Nosotros"
         */
        binding.aboutUsButton.setOnClickListener {
            val intent:Intent = Intent(this, AboutUs::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

    }

}