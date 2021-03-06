package com.appverse.hitthecooks

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import com.appverse.hitthecooks.databinding.ActivityFoodListBinding
import com.appverse.hitthecooks.model.Item
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

import android.view.MotionEvent
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.appverse.hitthecooks.recyclers.FoodListAdapter
import com.appverse.hitthecooks.recyclers.SearchAdapter
import android.app.Activity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.GravityCompat
import com.appverse.hitthecooks.interfaces.RecyclerTransferItem
import com.appverse.hitthecooks.model.ShoppingList
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.toObject


/**
 * Clase que contiene todos los alimentos que podemos añadir a la lista de la compra.
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 */
class FoodList : SuperActivity(), RecyclerTransferItem {
    /** Constante de la lista de los elementos buscados */
    private lateinit var itemsSearched : ArrayList<Item>
    /**Constante de los elementos que añadimos a la lista de la compra*/
    private var itemsFoodList : ArrayList<Item> = arrayListOf()
    /**Constante que nos permite enlazar cada elemento de la vista directamente.*/
    private val binding by lazy { ActivityFoodListBinding.inflate(layoutInflater) }
    /**Constante para enlazar con Firebase.*/
    private val db= FirebaseFirestore.getInstance()
    /** ID de referencia de la lista **/
    private lateinit var listId : String
    /** Objeto que permite modificar el comportamiento del panel **/
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    /**
     * Función que inicializa las vistas.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Infla la vista en el layout de la actividad superior
        drawerLayout.addView(binding.root, 1)

        //Aplica el modo oscuro si está activado
        applyDarkMode(binding.root)
        itemsSearched = arrayListOf()

        //Recoge el id de la lista por bundle
        listId = intent.extras?.getString("listId") as String

        //Da el comportamiento al panel
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        /**
         * Modificador al panel escondido: el tamaño que tiene que asomar y el estado que debe estar
         */
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            peekHeight = 200
            state = BottomSheetBehavior.STATE_COLLAPSED
        }

        //Recoger la lista en cuestión y además se queda escuchando por hay algún cambio
        updateLists()

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

        /**
         * Recupera los datos del usuario logado (eMail e imagen)
         */
        db.collection(FirestoreCollections.USERS).document(Firebase.auth.currentUser!!.email.toString()).get().addOnSuccessListener {
            Glide.with(this).load(it.get("profileImage")).circleCrop().into(binding.buttonUser as ImageView)
        }

        binding.recyclerSearch.layoutManager = GridLayoutManager(this,3,
            LinearLayoutManager.VERTICAL,false)

        /**
         * Función para la búsqueda a través del searchView.
         */
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.searchView.clearFocus()
                binding.searchView.isFocusable = true
               return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                binding.searchView.isFocusable = true
                var  searchViewText : String = p0.toString()
                if(searchViewText.isNotEmpty()) {
                    binding.emptySearchRecycler.visibility = View.GONE
                    binding.recyclerSearch.visibility = View.VISIBLE
                    searchInFirestore(searchViewText.lowercase())
                }
                return false
            }
        })

        /**
         * Despliega el menú de navegación lateral
         */
        binding.menuButton.setOnClickListener {
            super.drawerLayout.openDrawer(GravityCompat.START)
        }
        
    }

    /***
     * Función que ejecuta una query a la base de datos cada vez que es insertado un caracter en el buscador.
     * Pasa por argumentos al Adapter la lista recibida de los items buscados en la base de datos.
     */
    private fun searchInFirestore(searchText : String) {
      db.collection(FirestoreCollections.ITEMS).orderBy("name").startAt(searchText).endAt("$searchText\uf8ff").get().addOnCompleteListener {
          if(it.isSuccessful){
              itemsSearched = it.result.toObjects(Item::class.java) as ArrayList<Item>
              if(itemsSearched.isNotEmpty()) {
                  val adapter = SearchAdapter(this, itemsSearched,this)
                  binding.recyclerSearch.adapter = adapter
                  adapter.notifyDataSetChanged()

              }else{
                 val firstChar = searchText[0]
                  db.collection(FirestoreCollections.ITEMS).document(firstChar.toString()).get().addOnCompleteListener{
                      if(it.isSuccessful){
                          it.result.toObject(Item::class.java)
                              ?.let { it1 ->
                                  it1.name = searchText
                                  itemsSearched.add(it1) }
                          val adapter = SearchAdapter(this, itemsSearched, this)
                          binding.recyclerSearch.adapter = adapter
                          adapter.notifyDataSetChanged()
                      }
                  }
              }
          }
      }
    }

    /**
     * Sobreescribe la función del boton volver del telefono, navegando hacia la actividad de las listas de compra.
     * Además si el panel de abajo está expandido, al dar hacia atrás primero lo cierra.
     */
    override fun onBackPressed() {
            if(BottomSheetBehavior.from(binding.bottomSheet).state == BottomSheetBehavior.STATE_EXPANDED) {
                binding.searchView.clearFocus()
                binding.searchView.setQuery("", false)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }else{
                val intent:Intent = Intent(this,ShoppingListActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
    }

    /**
     * Función que cierra el panel de abajo cuando pulsas fuera de él y quita el foco sobre el buscador
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {

            if (event.action == MotionEvent.ACTION_DOWN) {
                if (bottomSheetBehavior.state === BottomSheetBehavior.STATE_EXPANDED && this.currentFocus !=null) {
                    closeSearch()
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    hideKeyboard(this)
                } else {
                    closeSearch()
                }
            }
        return super.dispatchTouchEvent(event)
    }

    /**
     * Función que quita el foco y resetea el buscador
     */
    private fun closeSearch(){
        binding.searchView.clearFocus()
        binding.searchView.setQuery("",false)
    }

    /**
     * Función que oculta el teclado al sacar el foco de la barra de busqueda
     * @param activity Actividad de referencia de la función
     */
    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Actualiza la lista
     */
    private fun updateLists() {
        db.collection(FirestoreCollections.LISTS).document(listId).addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapshot.let {
                val shoppingList = it?.toObject<ShoppingList>()
                if(shoppingList!=null) {
                    val adapter = FoodListAdapter(this, shoppingList?.items!!, listId)
                    binding.foodListRecycler.adapter = adapter
                    binding.foodListRecycler.layoutManager = GridLayoutManager(this, 3)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    /**
     * Función para añadir elementos al recycler de los alimentos que vamos a comprar
     */
    override fun passItem(itemToInsert: Item) {
            var shoppingList :ShoppingList? = null
       db.collection(FirestoreCollections.LISTS).document(listId).get().addOnCompleteListener {
           if(it.isSuccessful){
               shoppingList = it.result.toObject(ShoppingList::class.java) as ShoppingList
           }
       }.addOnSuccessListener {
           if(shoppingList?.items?.isEmpty()!!){
               db.collection(FirestoreCollections.LISTS).document(listId)
                   .update("items", FieldValue.arrayUnion(itemToInsert))
                   .addOnCompleteListener {}
           }else {
               for (item in shoppingList?.items!!) {
                   if (item == itemToInsert) {
                       Toast.makeText(this, resources.getString(R.string.cannotAddRepeatedItem)+": ${itemToInsert.name}", Toast.LENGTH_SHORT).show()
                   } else {
                       db.collection(FirestoreCollections.LISTS).document(listId)
                           .update("items", FieldValue.arrayUnion(itemToInsert))
                           .addOnCompleteListener {}
                   }
               }
           }

       }
   }
}
