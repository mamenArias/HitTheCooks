package com.appverse.hitthecooks

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.appverse.hitthecooks.databinding.ActivityMainBinding
import com.appverse.hitthecooks.model.User
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private val db=FirebaseFirestore.getInstance()
    private lateinit var storageRef : StorageReference
    val dbStorage = FirebaseStorage.getInstance()
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
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webid))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)






       binding.registerButton.setOnClickListener {

           val auth = FirebaseAuth.getInstance()
           //pasamos por argumentos el usuario y contraseña
           val tarea = auth.createUserWithEmailAndPassword(binding.nameGap.text.toString(),binding.passwordGap.text.toString())
           tarea.addOnCompleteListener(this,object: OnCompleteListener<AuthResult> {
               override fun onComplete(p0: Task<AuthResult>) {
                   if (tarea.isSuccessful) {
                       Toast.makeText(
                           this@MainActivity,
                           R.string.registroCompletado,
                           Toast.LENGTH_LONG
                       ).show()

                       storageRef = dbStorage.reference.child("imagenesPerfil").child("default.png")
                       storageRef.downloadUrl.addOnSuccessListener {url->
                           var user = User(binding.nameGap.text.toString(),url.toString())
                           db.collection( FirestoreCollections.USERS).document(user.email).set(
                               user
                           )
                       }
                       startActivity(Intent(this@MainActivity,PantallaPrincipal::class.java))

                   } else {
                       Toast.makeText(
                           this@MainActivity,
                           R.string.registroFallido,
                           Toast.LENGTH_LONG
                       ).show()
                   }
               }


           })

        }

        binding.signInButton.setOnClickListener {
            val auth = FirebaseAuth.getInstance()
            //pasamos por argumentos el usuario y contraseña
            val tarea = auth.signInWithEmailAndPassword(binding.nameGap.text.toString(),binding.passwordGap.text.toString())

            tarea.addOnCompleteListener(this,object: OnCompleteListener<AuthResult> {
                override fun onComplete(p0: Task<AuthResult>) {
                    if (tarea.isSuccessful) {
                        startActivity(Intent(this@MainActivity,PantallaPrincipal::class.java))

                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            R.string.loginIncorrecto,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }


            })

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