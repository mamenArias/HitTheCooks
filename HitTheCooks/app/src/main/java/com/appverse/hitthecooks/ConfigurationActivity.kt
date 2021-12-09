package com.appverse.hitthecooks

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import com.appverse.hitthecooks.databinding.ActivityConfigurationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

/**
 * Actividad que contiene la pantalla de preferencias
 * @author
 * @since
 */
class ConfigurationActivity : SuperActivity() {

    /** Objeto que permite enlazar las vistas del layout **/
    private val binding by lazy { ActivityConfigurationBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    /** Botón de navegación a la pantalla de perfil de usuario" **/
    private val buttonProfile: Button by lazy { binding.buttonProfile }

    /** Editor de preferencias **/
    lateinit var preferencesEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_configuration)

        //Aplica el modo oscuro en el caso de que el valor sea true
        applyDarkMode(binding.root)

        /** Botón para cambiar de pantalla **/
        buttonProfile.setOnClickListener {
            val intent:Intent = Intent(this@ConfigurationActivity, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        binding.buttonLogOut.setOnClickListener {
            auth = Firebase.auth
            Firebase.auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
        }

        //Inicializa el editor de preferencias con el gestor preferences
        preferencesEditor = preferences.edit()

        //Establece el valor de la preferencia "modo oscuro", si no hay ninguno, usará el valor por defecto
        val darkMode : Boolean = preferences.getBoolean("darkMode", false)

        if(darkMode){
            //Si el valor es verdadero, activa el switch del modo oscuro
            binding.switchNight.isChecked = true
        }

        //Modifica el valor del modo oscuro mediante el switch
        binding.switchNight.setOnClickListener {
            if (binding.switchNight.isChecked){
                preferencesEditor.putBoolean("darkMode", true)
                applyChanges()
            }else{
                preferencesEditor.putBoolean("darkMode", false)
                applyChanges()
            }
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