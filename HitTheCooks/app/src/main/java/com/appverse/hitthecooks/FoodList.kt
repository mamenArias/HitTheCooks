package com.appverse.hitthecooks

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.appverse.hitthecooks.databinding.ActivityFoodListBinding
import com.appverse.hitthecooks.model.Item
import com.appverse.hitthecooks.recyclers.FoodListAdapter
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import android.graphics.Rect
import android.text.TextWatcher

import android.view.MotionEvent
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.appverse.hitthecooks.model.ShoppingList
import com.appverse.hitthecooks.recyclers.SearchAdapter


/**
 * Clase que contiene todos los alimentos que podemos añadir a la lista de la compra.
 * @author Miguel Angel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian Gracia
 * @author Sergio Lopez
 * @since 1.4
 */
class FoodList : SuperActivity() {
    private lateinit var items : ArrayList<Item>
    /**Constante que nos permite enlazar cada elemento de la vista directamente.*/
    private val binding by lazy { ActivityFoodListBinding.inflate(layoutInflater) }
    /**Constante para enlazar con Firebase.*/
    private val db= FirebaseFirestore.getInstance()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    /**
     * Función que inicializa las vistas.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        drawerLayout.addView(binding.root, 1)

        //Aplica el modo oscuro si esta activado
        applyDarkMode(binding.root)
        items = arrayListOf()
        val listId = intent.extras?.getString("listId")


        //Da el comportamiento al panel
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
        /**
         * Modificador al panel escondido: el tamaño que tienes que asomar y el estado que debe estar
         */
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 200
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
        /***
         * Función que expande el panel escondido cuando pulsas la lupa del buscador
         */
        binding.searchView.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        /***
         * Función que expande el panel de escondido al darle en cualquier parte al buscador
         */
        binding.searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }


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

       // db.collection(FirestoreCollections.ITEMS).document(list[position].name).get().addOnSuccessListener {
       // }

        val arrayAlimentos:ArrayList<String> = arrayListOf("aceite", "agua", "azucar", "cerdo", "cereales", "chocolate", "congelados",
            "dulces", "fruta", "huevos", "leche", "legumbres", "pan", "pasta", "patatas", "pescado", "pollo", "queso", "sal", "snacks",
            "ternera", "verduras", "yogur")

       // val adapter:FoodListAdapter = FoodListAdapter(this, arrayAlimentos)
       // binding.foodListRecycler.adapter = adapter
       // binding.foodListRecycler.layoutManager = GridLayoutManager(this, 3)

        binding.recyclerSearch.layoutManager = GridLayoutManager(this,3,
            LinearLayoutManager.VERTICAL,false)



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searchView.clearFocus()
               return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                var  searchViewText : String = p0.toString()
                if(searchViewText.isNotEmpty()) {
                    searchInFirestore(searchViewText.lowercase())
                }
                return false
            }
        })


    }

    private fun searchInFirestore(searchText : String) {
      db.collection(FirestoreCollections.ITEMS).orderBy("name").startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener {
          if(it.isSuccessful){
              items = it.result.toObjects(Item::class.java) as ArrayList<Item>
                  val adapter = SearchAdapter(this, items)
                  binding.recyclerSearch.adapter = adapter
                  adapter.notifyDataSetChanged()
          }
      }
    }

    /**
     * Sobreescribe la función del boton volver del telefono, navegando hacia la actividad de las listas de compra.
     * Además si el panel de abajo está expandido, al dar hacia atrás primero lo cierra.
     */
    override fun onBackPressed() {
            if(BottomSheetBehavior.from(binding.bottomSheet).state == BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }else{
                val intent:Intent = Intent(this,ShoppingListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
    }

    /**
     * Quita el foco sobre el buscador
     */
    override fun onResume() {
        super.onResume()
        binding.searchView.setQuery("", false)
        binding.root.requestFocus()
    }

    /**
     * Función que cierra el panel de abajo cuando pulsas fuera de él y quita el foco sobre el buscador
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (bottomSheetBehavior.state === BottomSheetBehavior.STATE_EXPANDED) {
                binding.searchView.clearFocus()
                binding.searchView.setQuery("", false)
               bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }else{
                binding.searchView.clearFocus()
                binding.searchView.setQuery("", false)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        return super.dispatchTouchEvent(event)
    }




}