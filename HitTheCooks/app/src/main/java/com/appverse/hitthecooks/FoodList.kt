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
        //setContentView(binding.root)
        drawerLayout.addView(binding.root, 1)

        applyDarkMode(binding.root)

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
            val intent = Intent(this, InvitationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

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

    override fun onBackPressed() {
        val intent:Intent = Intent(this,ShoppingListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }




}