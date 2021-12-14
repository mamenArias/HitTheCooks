package com.appverse.hitthecooks

import android.app.Activity
import android.content.Intent
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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


/**
 *
 * Actividad que contiene el login y registro de usuarios en la App
 *
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @version 1.0
 * @since   1.0
 */
class LoginActivity : AppCompatActivity() {

    /** Constante que permite enlazar directamente con las vistas del layout **/
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    /** Variable que sirve para instanciar la clase FirebaseAuth**/
    private lateinit var auth: FirebaseAuth

    /** Variable que sirve para instanciar la clase GoogleSignInClient**/
    private lateinit var googleSignInClient: GoogleSignInClient

    /** Constante que instancia un objeto de la clase FirebaseFirestore**/
    private val db = FirebaseFirestore.getInstance()

    /** Variable que sirve para instanciar la clase StorageReference**/
    private lateinit var storageRef: StorageReference

    /** Constante que instancia un objeto de la clase FirebaseStorage**/
    val dbStorage = FirebaseStorage.getInstance()

    /**
     * Inicializa la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //  setTheme(R.style.SplashScreen)
        Thread.sleep(2000)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        var text: TextView = binding.googleButton.getChildAt(0) as TextView
        text.text = resources.getString(R.string.googleLogin)

        /** Objeto que gestiona el login con Google **/
        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.webid))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        /**
         * Función que comprueba si el campo email y contraseña no están vacíos. Si no lo están registra un usuario
         * en la base de datos con dicho email y contraseña
         */
        binding.registerButton.setOnClickListener {
            if (binding.nameGap.text.isEmpty() || binding.passwordGap.text.isEmpty()) {
                Toast.makeText(this, R.string.campoVacio, Toast.LENGTH_LONG).show()

            } else if(binding.passwordGap.text.toString().length<8){
                Toast.makeText(
                    this,"La contraseña debe contener al menos 8 caracteres",Toast.LENGTH_LONG).show()
            }else if (binding.nameGap.text.toString().endsWith("gmail.com")) {
                Toast.makeText(
                    this,
                    "Si quiere registrarse con una cuenta de Gmail pulse en el botón inferior para ello",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val auth = FirebaseAuth.getInstance()
                //pasamos por argumentos el usuario y contraseña
                val tarea = auth.createUserWithEmailAndPassword(
                    binding.nameGap.text.toString(),
                    binding.passwordGap.text.toString()
                )
                tarea.addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(p0: Task<AuthResult>) {
                        if (tarea.isSuccessful) {
                            Toast.makeText(
                                this@LoginActivity,
                                R.string.registroCompletado,
                                Toast.LENGTH_LONG
                            ).show()

                            storageRef =
                                dbStorage.reference.child("imagenesPerfil").child("default.png")
                            storageRef.downloadUrl.addOnSuccessListener { url ->
                                var user = User(binding.nameGap.text.toString(), url.toString())
                                db.collection(FirestoreCollections.USERS).document(user.email).set(
                                    user
                                )
                            }
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                R.string.registroFallido,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                })
            }
        }
        /**
         * Función que comprueba si el campo email y contraseña no están vacíos. Si no lo están realiza el login de un usuario
         * ya guardado en la base de datos con dicho email y contraseña
         */
        binding.signInButton.setOnClickListener {
            if (binding.nameGap.text.isEmpty() || binding.passwordGap.text.isEmpty()) {
                Toast.makeText(this, R.string.campoVacio, Toast.LENGTH_LONG).show()

            } else if (binding.passwordGap.text.toString().length < 8) {
                Toast.makeText(this, R.string.passCorto, Toast.LENGTH_SHORT).show()
            } else {
                val auth = FirebaseAuth.getInstance()
                //pasamos por argumentos el usuario y contraseña
                val tarea = auth.signInWithEmailAndPassword(
                    binding.nameGap.text.toString(),
                    binding.passwordGap.text.toString()
                )

                tarea.addOnCompleteListener(this, object : OnCompleteListener<AuthResult> {
                    override fun onComplete(p0: Task<AuthResult>) {
                        if (tarea.isSuccessful) {
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                R.string.loginIncorrecto,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                })
            }
        }
        /**
         * Función que realiza el logIn con una cuenta de Gmail
         */
        binding.googleButton.setOnClickListener {
            responseLauncher.launch(googleSignInClient.signInIntent)
        }

    }

    /**
     * Función que hace visible la actividad y se comprueba el estado del usuario actual
     */
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

    /**
     * Función que comprueba que el usuario actual no sea nulo para avanzar a la siguiente Pantalla
     *
     * @param account usuario de Firebase
     */
    fun updateUI(account: FirebaseUser?) {
        if (account != null) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {

        }
    }

    /** Constante que, a partir de los datos de la cuenta de Google, guarda el usuario en base de datos **/
    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
                val account = task.getResult(ApiException::class.java)
                val acct = GoogleSignIn.getLastSignedInAccount(this)
                var userToInsert =
                    User(acct!!.email as String, acct.photoUrl!!.toString() as String)
                db.collection(FirestoreCollections.USERS).document(acct.email as String).set(
                    userToInsert
                )
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener {
                        updateUI(auth.currentUser)
                    }
                }
            }
        }

    /***
     * No permite ir hacia atrás
     */
    override fun onBackPressed() {
    }

}