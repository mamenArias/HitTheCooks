package com.appverse.hitthecooks

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.appverse.hitthecooks.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
     private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val goToPantallaPrincipal by lazy { Intent(this, PantallaPrincipal::class.java) }
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashScreen)
        Thread.sleep(2000)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val analytics = FirebaseAnalytics.getInstance(this)
        auth = Firebase.auth




        var text:TextView = binding.googleButton.getChildAt(0) as TextView
        text.setText("Iniciar sesión con Google")


       binding.signInButton.setOnClickListener {
            startActivity(goToPantallaPrincipal)
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleButton.setOnClickListener {
            googleSignInClient.signOut()
            val signInIntent = googleSignInClient.signInIntent
            responseLauncher.launch(signInIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        checkUser()
    }

    private fun checkUser(){
        if(auth.currentUser!=null) {
            startActivity(goToPantallaPrincipal)
        }
    }



    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            val account = task.getResult(ApiException::class.java)

            if(account!=null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if(it.isSuccessful){
                        startActivity(goToPantallaPrincipal)
                    }else{
                        //añadir a string el texto del toast
                        Toast.makeText(this,"Error al iniciar sesión", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
    }



}