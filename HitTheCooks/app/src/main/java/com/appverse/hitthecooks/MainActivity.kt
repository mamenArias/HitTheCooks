package com.appverse.hitthecooks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.GravityCompat
import com.appverse.hitthecooks.databinding.ActivityPrincipalBinding
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

/**
 * Actividad de la pantalla principal desde la que navegar al resto de pantallas
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian Gracía
 * @author Sergio López
 * @since 1.4
 */
class MainActivity : SuperActivity() {

    /** Constante que permite enlazar directamente con las vistas del layout **/
    private val binding by lazy { ActivityPrincipalBinding.inflate(layoutInflater) }
    /** Objeto que contiene la instancia a base de datos de Firebase **/
    private val db= FirebaseFirestore.getInstance()

    /**
     * Inicializa la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Infla la vista en el layout de la actividad superior para compartir la barra de navegación
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_main)
        //Aplica el modo oscuro si está activado
        applyDarkMode(binding.root)
        //Comprueba la recepción de invitaciones
        receivedInvitationLink()
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

        //Muestra el nombre del usuario y la foto de perfil en la parte superior de la pantalla
        db.collection(FirestoreCollections.USERS).document(Firebase.auth.currentUser!!.email.toString()).get().addOnSuccessListener {
            binding.usernameText.text = it.get("email").toString().substringBefore('@')
            Glide.with(this).load(it.get("profileImage")).circleCrop().into(binding.userButton as ImageView)
        }

        /**
         * Despliega el menú de navegación lateral
         */
        binding.menuButton.setOnClickListener {
            super.drawerLayout.openDrawer(GravityCompat.START)
        }

    }

    /**
     * Función que, en caso de recibir una invitación a una lista, redirecciona a la actividad
     */
    private fun receivedInvitationLink(){
        FirebaseDynamicLinks.getInstance().getDynamicLink(intent).addOnSuccessListener {
            var deepLink : Uri? = null;
            if (it !=null){
                deepLink = it.link as Uri
                val listId = deepLink.getQueryParameter("list")
                db.collection(FirestoreCollections.USERS).document(Firebase.auth.currentUser?.email.toString()).update("listIds",FieldValue.arrayUnion(listId)).addOnCompleteListener {  }
                db.collection(FirestoreCollections.LISTS).document(listId.toString()).update("users",FieldValue.arrayUnion(Firebase.auth.currentUser?.email.toString())).addOnCompleteListener {  }
                startActivity(Intent(this,FoodList::class.java))
            }
        }
    }
}