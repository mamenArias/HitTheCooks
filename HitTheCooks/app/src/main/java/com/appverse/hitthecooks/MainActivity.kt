package com.appverse.hitthecooks

import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.appverse.hitthecooks.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashScreen)
        Thread.sleep(2000)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var text:TextView = binding.googleButton.getChildAt(0) as TextView
        text.setText("Iniciar sesión con Google")

        FirebaseDynamicLinks.getInstance().getDynamicLink(intent).addOnSuccessListener {
            var deepLink : Uri? = null;
            if (it !=null){
                deepLink = it.link as Uri
                val listId = deepLink.getQueryParameter("list")
                Toast.makeText(this, "$listId", Toast.LENGTH_SHORT).show()
               startActivity(Intent(this,FoodList::class.java))
            }
        }


       binding.signInButton.setOnClickListener {
            val intent= Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }
    }




}