package com.appverse.hitthecooks

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.appverse.hitthecooks.model.ShoppingList
import com.appverse.hitthecooks.databinding.ActivityShoppingListBinding
import com.appverse.hitthecooks.model.User
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.appverse.hitthecooks.recyclers.ShoppingListAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

/**
 * Actividad que contiene la pantalla con las listas creadas o compartidas por el usuario
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian Gracía
 * @author Sergio López
 * @since 1.4
 */
class ShoppingListActivity : SuperActivity() {

    /** Objeto que recupera la instancia al storage de Firebase **/
    private val dbStorage = FirebaseStorage.getInstance()
    private lateinit var storageRef : StorageReference
    /** Objeto que que contiene el autentificador de Firebase **/
    private lateinit var auth: FirebaseAuth
    /** ArrayList que contiene la coleccion de listas de la compra **/
    private lateinit var shoppingList : ArrayList<ShoppingList>
    /** Objeto que contiene la instancia a la base de datos Firestore **/
    private val db : FirebaseFirestore by lazy { Firebase.firestore }
    /** Obejto que permite enlazar las vistas del layout **/
    private val binding by lazy { ActivityShoppingListBinding.inflate(layoutInflater) }

    /**
     * Inicializa la actividad
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inicializa el autentificador de Firebase
        auth = FirebaseAuth.getInstance()
        //Infla la vista en el layout de la actividad superior
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_lists)
        fetchData(true)
        //Aplica el modo oscuro en el caso de que esté activado
        applyDarkMode(binding.root)

    }

    /***
     *
     */
    private fun fetchData(alert : Boolean){
        var user :User = User()
        val builderAlertDialog = AlertDialog.Builder(this)
        builderAlertDialog.setView(R.layout.loading)
        builderAlertDialog.create()
        val alertDialog = builderAlertDialog.show()
        alertDialog.window?.setLayout(400,400)

        shoppingList = arrayListOf()
        db.collection(FirestoreCollections.USERS).document(auth.currentUser?.email.toString()).get().addOnCompleteListener {
            if(it.isSuccessful){
                user = it.result.toObject(User::class.java)!!
            }
        }.addOnSuccessListener {
            if(user != null) {
                val docRef =   db.collection(FirestoreCollections.LISTS)
                docRef.whereArrayContains("users", user.email).get().addOnCompleteListener {
                    if(it.isSuccessful){
                        for (doc in it.result){
                            val list :ShoppingList = doc.toObject(ShoppingList::class.java)
                            shoppingList.add(list)
                        }
                    }
                }.addOnSuccessListener {
                    alertDialog.cancel()
                    binding.recyclerView.layoutManager = LinearLayoutManager(this)
                    val adapter = ShoppingListAdapter(shoppingList, binding.recyclerView,ShoppingListActivity@this,this)
                    binding.recyclerView.adapter = adapter
                    var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter,this))
                    itemTouchHelper.attachToRecyclerView(binding.recyclerView)
                }
            }
        }
    }
}