package com.appverse.hitthecooks

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.appverse.hitthecooks.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

enum class ProviderType{
    GOOGLE
}
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var text:TextView = binding.googleButton.getChildAt(0) as TextView
        text.setText("Iniciar sesión con Google")
        setContentView(binding.root)

        binding.botonInicioSesion.setOnClickListener {
            val intento = Intent(this, EditProfile::class.java)
            startActivity(intento)
        }
    }




}