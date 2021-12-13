package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.appverse.hitthecooks.databinding.ActivitySplashBinding
/**
 * Clase que contiene el SplashScreen que aparece al abrir la aplicación
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.1
 */
class SplashActivity : AppCompatActivity() {

    /** Constante que permite enlazar directamente con las vistas del layout **/
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    /**
     * Función que inicializa la actividad que cambia a otra actividad tras 2500ms
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /**
         * Gestiona el tiempo de muestra de la animación
         */
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }



}