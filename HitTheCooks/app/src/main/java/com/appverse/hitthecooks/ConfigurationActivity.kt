package com.appverse.hitthecooks

import android.animation.Animator
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.core.view.GravityCompat
import com.airbnb.lottie.LottieAnimationView
import com.appverse.hitthecooks.databinding.ActivityConfigurationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Actividad que contiene la pantalla de preferencias
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 */
class ConfigurationActivity : SuperActivity() {
    private var isCheckedDone = false
    /** Objeto que permite enlazar las vistas del layout **/
    private val binding by lazy { ActivityConfigurationBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    /** Botón de navegación a la pantalla de perfil de usuario" **/
    private val buttonProfile: Button by lazy { binding.buttonProfile }

    /** Editor de preferencias **/
    lateinit var preferencesEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Infla la vista en el layout de la actividad superior
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_configuration)

        //Aplica el modo oscuro en el caso de que el valor sea true
        applyDarkMode(binding.root)

        /**
         * Navega a la actividad de Configuración
         */
        buttonProfile.setOnClickListener {
            val intent:Intent = Intent(this@ConfigurationActivity, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        /**
         * Hace el logout de la aplicación
         */
        binding.buttonLogOut.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            .setTitle(R.string.cerrarSesion)
            .setMessage(R.string.cierreSesionPregunta)
                .setNegativeButton(R.string.cancelar) { view, _ ->
                    view.dismiss()
                }
                .setPositiveButton(R.string.confirmar) { view, _ ->
                    val builder =  AlertDialog.Builder(this)
                    builder.setView(R.layout.logginout_progressbar)
                    builder.create()
                    val alertDialog = builder.show()
                    alertDialog.window?.setLayout(500,450)
                    Firebase.auth.signOut()
                    val intent:Intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                    view.dismiss()
                }
                .setCancelable(false)
                .create()
            dialog.show()

        }

        //Inicializa el editor de preferencias con el gestor preferences
        preferencesEditor = preferences.edit()

        //Establece el valor de la preferencia "modo oscuro", si no hay ninguno, usará el valor por defecto
        val darkMode : Boolean = preferences.getBoolean("darkMode", false)

        //Switch personalizado para el modo oscuro
        val switch : LottieAnimationView = findViewById(R.id.switchNight)

        if(darkMode){
            //Si el valor es verdadero, activa el switch del modo oscuro
            isCheckedDone = true
            switch.setMinAndMaxProgress(0.5f,1.0f)
        }else{
            switch.setMinAndMaxProgress(0.0f,0.5f)
        }
        switch.speed = 4f

        //Modifica el valor del modo oscuro mediante el switch
        switch.setOnClickListener {
            if(isCheckedDone){
                switch.setMinAndMaxProgress(0.5f,1.0f)
                switch.playAnimation()
                isCheckedDone = false
                switch.addAnimatorListener(object: Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator?) {
                        TODO("Not yet implemented")
                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        preferencesEditor.putBoolean("darkMode", false)
                        applyChanges()
                    }

                    override fun onAnimationCancel(p0: Animator?) {
                        TODO("Not yet implemented")
                    }

                    override fun onAnimationRepeat(p0: Animator?) {
                        TODO("Not yet implemented")
                    }
                })

            }else{
                switch.setMinAndMaxProgress(0.0f,0.5f)
                switch.playAnimation()
                isCheckedDone = true

                switch.addAnimatorListener(object: Animator.AnimatorListener {
                    override fun onAnimationStart(p0: Animator?) {

                    }

                    override fun onAnimationEnd(p0: Animator?) {
                        preferencesEditor.putBoolean("darkMode",true)
                        applyChanges()
                    }

                    override fun onAnimationCancel(p0: Animator?) {

                    }

                    override fun onAnimationRepeat(p0: Animator?) {

                    }
                })


            }
        }

        /**
         * Despliega el menú de navegación lateral
         */
        binding.menuButton.setOnClickListener {
            super.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.buttonAboutUs.setOnClickListener {
            val intent:Intent = Intent(this@ConfigurationActivity, AboutUs::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

    }

    /**
     * Función que aplica los cambios al archivo de configuración y refresca la actividad
     */
    fun applyChanges() : Unit {
        preferencesEditor.commit()
        finish();
        startActivity(intent);
    }



}