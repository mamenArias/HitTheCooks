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

class ShoppingListActivity : SuperActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var shoppingList : ArrayList<ShoppingList>
    private val db : FirebaseFirestore by lazy { Firebase.firestore }
    private val binding by lazy { ActivityShoppingListBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_lists)

        fetchData(true)
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
                    val adapter = ShoppingListAdapter(shoppingList, binding.recyclerView,ShoppingListActivity@this)
                    binding.recyclerView.adapter = adapter
                    var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter,this))
                    itemTouchHelper.attachToRecyclerView(binding.recyclerView)
                }
            }
        }
    }
}