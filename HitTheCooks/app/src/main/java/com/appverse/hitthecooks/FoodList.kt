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

class FoodList : SuperActivity() {

    private val binding by lazy { ActivityFoodListBinding.inflate(layoutInflater) }
    private val db= FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        drawerLayout.addView(binding.root, 1)

        applyDarkMode(binding.root)

        binding.buttonBack.setOnClickListener {
            val intent:Intent = Intent(this,ShoppingListActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        binding.buttonUser.setOnClickListener {
            val intent = Intent(this, EditProfile::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this, InvitationActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        db.collection(FirestoreCollections.USERS).document(Firebase.auth.currentUser!!.email.toString()).get().addOnSuccessListener {
            Glide.with(this).load(it.get("profileImage")).circleCrop().into(binding.buttonUser as ImageView)
        }

        /*val storage:FirebaseStorage = FirebaseStorage.getInstance()
        val gsReference = storage.getReferenceFromUrl("gs://hit-the-cooks.appspot.com/iconosLista/iconoA.png")
        gsReference.child("iconosLista/iconoA.png").downloadUrl.addOnSuccessListener {

        }.addOnFailureListener {

        }*/
        val arrayItems:ArrayList<Item> = arrayListOf()
        arrayItems.add(Item("Patatas", "https://firebasestorage.googleapis.com/v0/b/hit-the-cooks.appspot.com/o/iconosLista%2Ficonocongelados.png?alt=media&token=125754f0-80e7-435f-a651-2cfab382d52a"))
        arrayItems.add(Item("Patatas", "https://firebasestorage.googleapis.com/v0/b/hit-the-cooks.appspot.com/o/iconosLista%2Ficonocongelados.png?alt=media&token=125754f0-80e7-435f-a651-2cfab382d52a"))
        arrayItems.add(Item("Patatas", "https://firebasestorage.googleapis.com/v0/b/hit-the-cooks.appspot.com/o/iconosLista%2Ficonocongelados.png?alt=media&token=125754f0-80e7-435f-a651-2cfab382d52a"))
        arrayItems.add(Item("Patatas", "https://firebasestorage.googleapis.com/v0/b/hit-the-cooks.appspot.com/o/iconosLista%2Ficonocongelados.png?alt=media&token=125754f0-80e7-435f-a651-2cfab382d52a"))
        arrayItems.add(Item("Patatas", "https://firebasestorage.googleapis.com/v0/b/hit-the-cooks.appspot.com/o/iconosLista%2Ficonocongelados.png?alt=media&token=125754f0-80e7-435f-a651-2cfab382d52a"))

        val adapter:FoodListAdapter = FoodListAdapter(this, arrayItems)
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