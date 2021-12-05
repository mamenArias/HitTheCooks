package com.appverse.hitthecooks

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.appverse.hitthecooks.databinding.ActivityConfigurationBinding
import com.appverse.hitthecooks.databinding.ActivityMainBinding

class ConfigurationActivity : AppCompatActivity() {

    private val binding by lazy { ActivityConfigurationBinding.inflate(layoutInflater) }
    private val buttonProfile: Button by lazy { binding.buttonProfile }

    /** Editor de preferencias **/
    lateinit var preferencesEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /** Botón para cambiar de pantalla **/
        buttonProfile.setOnClickListener {
            startActivity(Intent(this@ConfigurationActivity, EditProfile::class.java))
        }

        //Gestiona las preferencias de la app
        val preferences: SharedPreferences = this.getSharedPreferences("preferences", Context.MODE_PRIVATE)

        //Inicializa el editor de preferencias con el gestor preferences
        preferencesEditor = preferences.edit()

        //Establece el valor de la preferencia "modo oscuro", si no hay ninguno, usara el valor por defecto
        val darkMode : Boolean = preferences.getBoolean("darkMode", false)

        if(darkMode){
            //Si el valor es verdadero, activa el switch del modo oscuro
            binding.switchNight.isChecked = true
            //binding.rootView.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }

        binding.switchNight.setOnClickListener {
            if (binding.switchNight.isChecked){
                preferencesEditor.putBoolean("darkMode", true)
                applyChanges()
            }else{
                preferencesEditor.putBoolean("darkMode", false)
                applyChanges()
            }
        }

        Toast.makeText(this, ""+darkMode.toString(), Toast.LENGTH_LONG).show()

    }

    fun applyChanges() : Unit {
        preferencesEditor.commit()
        finish();
        startActivity(intent);
    }

}