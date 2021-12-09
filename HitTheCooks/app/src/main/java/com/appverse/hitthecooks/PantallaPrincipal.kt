package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.appverse.hitthecooks.databinding.ActivityPrincipalBinding
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

/**
 * Actividad de la pantalla principal desde la que navegar al resto de pantallas
 * @author
 * @since
 */
class PantallaPrincipal : SuperActivity() {

    /** Constante que permite enlazar directamente con las vistas del layout **/
    private val binding by lazy { ActivityPrincipalBinding.inflate(layoutInflater) }
    private val db= FirebaseFirestore.getInstance()

    /**
     * Inicializa la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_main)

        applyDarkMode(binding.root)

        /**
         * Función que permite navegar a la pantalla de configuración
         */
        binding.configButton.setOnClickListener {
            val intent:Intent = Intent(this, ConfigurationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        /**
         * Función que permite navegar a la pantalla de editar perfil
         */
        binding.userButton.setOnClickListener {
            val intent:Intent = Intent(this, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        /**
         * Función que permite navegar a la pantalla de "Mis Listas"
         */
        binding.myListsButton.setOnClickListener {
            val intent:Intent = Intent(this, ShoppingListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        /**
         * Función que permite navegar a la pantalla de "Sobre Nosotros"
         */
        binding.aboutUsButton.setOnClickListener {
            val intent:Intent = Intent(this, AboutUs::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        db.collection(FirestoreCollections.USERS).document(Firebase.auth.currentUser!!.email.toString()).get().addOnSuccessListener {
            binding.usernameText.text = it.get("email").toString().substring(0,it.get("email").toString().indexOf('@'))
            Glide.with(this).load(it.get("profileImage")).circleCrop().into(binding.userButton as ImageView)
        }



    }

}