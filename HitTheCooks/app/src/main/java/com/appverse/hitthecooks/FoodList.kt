package com.appverse.hitthecooks

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.appverse.hitthecooks.databinding.ActivityFoodListBinding
import com.appverse.hitthecooks.model.Item
import com.appverse.hitthecooks.recyclers.FoodListAdapter
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/**
 * Clase que contiene todos los alimentos que podemos añadir a la lista de la compra.
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian Gracía
 * @author Sergio López
 * @since 1.4
 */
class FoodList : SuperActivity() {

    /**Constante que nos permite enlazar cada elemento de la vista directamente.*/
    private val binding by lazy { ActivityFoodListBinding.inflate(layoutInflater) }
    /**Constante para enlazar con Firebase.*/
    private val db= FirebaseFirestore.getInstance()

    /**
     * Función que inicializa las vistas.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Infla la vista en el layout de la actividad superior
        drawerLayout.addView(binding.root, 1)

        //Aplica el modo oscuro si esta activado
        applyDarkMode(binding.root)

        //Recoge el id de la lista por bundle
        val listId = intent.extras?.getString("listId")


        /**
         * Función para volver a la pantalla anterior.
         */
        binding.buttonBack.setOnClickListener {
            val intent:Intent = Intent(this,ShoppingListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        /**
         * Función para acceder al perfil de usuario.
         */
        binding.buttonUser.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        /**
         * Función para ir a la pantalla de las listas de la compra.
         */
        binding.buttonAdd.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("listId",listId)
            val intent = Intent(this, InvitationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent.putExtras(bundle))
            finish()
        }

        //Recupera los datos del usuario logado (eMail e imagen)
        db.collection(FirestoreCollections.USERS).document(Firebase.auth.currentUser!!.email.toString()).get().addOnSuccessListener {
            Glide.with(this).load(it.get("profileImage")).circleCrop().into(binding.buttonUser as ImageView)
        }

        val arrayAlimentos:ArrayList<String> = arrayListOf("aceite", "agua", "azucar", "cerdo", "cereales", "chocolate", "congelados",
            "dulces", "fruta", "huevos", "leche", "legumbres", "pan", "pasta", "patatas", "pescado", "pollo", "queso", "sal", "snacks",
            "ternera", "verduras", "yogur")

        val adapter:FoodListAdapter = FoodListAdapter(this, arrayAlimentos)
        binding.foodListRecycler.adapter = adapter
        binding.foodListRecycler.layoutManager = GridLayoutManager(this, 3)

    }

    /**
     * Sobreescribe la función del boton volver del telefono,
     * navegando hacia la actividad de las listas de compra
     */
    override fun onBackPressed() {
        val intent:Intent = Intent(this,ShoppingListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }




}