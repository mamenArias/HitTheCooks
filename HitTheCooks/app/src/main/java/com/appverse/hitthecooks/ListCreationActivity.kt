package com.appverse.hitthecooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import classes.Student
import com.appverse.hitthecooks.databinding.ActivityListCreationBinding
import com.appverse.hitthecooks.interfaces.RecyclerTransferData
import com.appverse.hitthecooks.model.ShoppingList
import com.appverse.hitthecooks.model.User
import com.appverse.hitthecooks.utils.CommonItemDecoration
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import recyclers.AboutUsAdapter
import recyclers.ListCreationAdapter

class ListCreationActivity : AppCompatActivity(),RecyclerTransferData {
    private  var imageId=0
    private val binding by lazy { ActivityListCreationBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val db = Firebase.firestore

        val backgroundsList = arrayListOf<Int>(R.drawable.background_list_1,
         R.drawable.background_list_2,R.drawable.background_list_3,R.drawable.background_list_4)

        binding.recyclerViewBackgroundLists.addItemDecoration(CommonItemDecoration(10,10))
        binding.recyclerViewBackgroundLists.adapter = ListCreationAdapter(this,backgroundsList)
        binding.recyclerViewBackgroundLists.layoutManager = GridLayoutManager(this,2)

        binding.nextButton.setOnClickListener {
           val ref = db.collection(FirestoreCollections.LISTS).document()                                     //Obtener usuario al registrarse
            ref.set(ShoppingList(binding.nameListTextView.text.toString(),imageId,ref.id, arrayListOf(User("sergio@gmail.com"))))

            //Insert en base de datos de lista con su nombre y el id de la imagen de fondo de la misma

            //Ir a la pantalla de invitación de usuarios que genera un enlace de Dynamics Links
        }
    }

    override fun passData(value: Int) {
      imageId=  value
    }


}