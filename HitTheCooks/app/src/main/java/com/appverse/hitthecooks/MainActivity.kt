package com.appverse.hitthecooks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.appverse.hitthecooks.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.SplashScreen)
        Thread.sleep(2000)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var text:TextView = binding.googleButton.getChildAt(0) as TextView
        text.setText("Iniciar sesión con Google")

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webid))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)






       binding.signInButton.setOnClickListener {
            val intent= Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)


        }

        binding.googleButton.setOnClickListener {
            responseLauncher.launch(googleSignInClient.signInIntent)
        }






    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        var currentUser = auth.getCurrentUser()
        updateUI(currentUser);

        val credential = GoogleAuthProvider.getCredential(R.string.webid.toString(), null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user. )
                    updateUI(null)
                }
            }
    }

    fun updateUI(account: FirebaseUser?) {
        if (account != null) {

            startActivity(Intent(this, PantallaPrincipal::class.java))
        } else {

        }
    }


    private val responseLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            val account = task.getResult(ApiException::class.java)

            if(account!=null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    updateUI(auth.currentUser)
                }
            }
        }
    }

}