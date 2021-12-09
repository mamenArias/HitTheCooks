package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.appverse.hitthecooks.databinding.ActivityListCreationBinding
import com.appverse.hitthecooks.interfaces.RecyclerTransferData
import com.appverse.hitthecooks.model.ShoppingList
import com.appverse.hitthecooks.model.User
import com.appverse.hitthecooks.utils.CommonItemDecoration
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import recyclers.ListCreationAdapter

class ListCreationActivity : SuperActivity(),RecyclerTransferData {
    private var imageId : Int? = null
    private val binding by lazy { ActivityListCreationBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        drawerLayout.addView(binding.root, 1)
        applyDarkMode(binding.root)

        val db = Firebase.firestore

        val backgroundsList = arrayListOf<Int>(R.drawable.background_list_1,
         R.drawable.background_list_2,R.drawable.background_list_3,R.drawable.background_list_4)

        binding.recyclerViewBackgroundLists.addItemDecoration(CommonItemDecoration(10,10))
        binding.recyclerViewBackgroundLists.adapter = ListCreationAdapter(this,backgroundsList)
        binding.recyclerViewBackgroundLists.layoutManager = GridLayoutManager(this,2)

        binding.nextButton.setOnClickListener {
            if(binding.nameListTextView.text.isNotEmpty() && imageId != null) {
                //    TODO(" //Obtener usuario al registrarse una vez esté hecho el login y registro")
                val ref = db.collection(FirestoreCollections.LISTS).document()
                var user : User ?=null
                db.collection(FirestoreCollections.USERS).document("sergio@gmail.com").get().addOnCompleteListener {
                    if(it.isSuccessful){
                        user = it.result.toObject(User::class.java)!!
                    }
                }.addOnSuccessListener {
                    user?.listsIds?.add(ref.id)
                    user?.email?.let { email -> db.collection(FirestoreCollections.USERS).document(email).set(user!!) }
                    ref.set(
                        ShoppingList(
                            binding.nameListTextView.text.toString(),
                            imageId!!,
                            ref.id,                 //email hardcodeado para prueba
                            arrayListOf(user?.email?:""))
                        )

                }.addOnSuccessListener {
                    val bundle = Bundle()
                    bundle.putString("listId",ref.id)
                    startActivity(Intent(this, InvitationActivity::class.java).putExtras(bundle))
                }

                //Ir a la pantalla de invitación de usuarios que genera un enlace de Dynamics Links
            }else{
                if(imageId == null){
                    Toast.makeText(this, R.string.imageListNotSelected, Toast.LENGTH_SHORT).show()
                }
                if(binding.nameListTextView.text.isEmpty()){
                    Toast.makeText(this, R.string.nameListIsEmpty, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun passData(value: Int ) {
        imageId=  value
    }


}