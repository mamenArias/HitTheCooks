package com.appverse.hitthecooks.recyclers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.*
import com.appverse.hitthecooks.model.ShoppingList
import com.appverse.hitthecooks.model.User
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ShoppingListAdapter(private val shoppingList: ArrayList<ShoppingList>, private val view: View, private val activity: Activity,private val context: Context): RecyclerView.Adapter<ShoppingListHolder>(){
    private val db = Firebase.firestore
    private lateinit var userProfileImageList : ArrayList<String>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListHolder {
       var layoutInflater : View?
           if(viewType == R.layout.item_shopping_list){
           layoutInflater= LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list,parent,false)
        }else{
         layoutInflater =   LayoutInflater.from(parent.context).inflate(R.layout.item_creation_list,parent,false)
        }
        return ShoppingListHolder(layoutInflater!!)
    }

    override fun onBindViewHolder(holder: ShoppingListHolder, position: Int) {

      if(position==shoppingList.size){
          if(shoppingList.isEmpty()){
              holder.containerEmptyList.visibility =View.VISIBLE
          }
          holder.itemView.findViewById<TextView>(R.id.createListText).setOnClickListener {
            context.startActivity(Intent(context,ListCreationActivity::class.java))
          }
      }else{
          holder.imageBackground.setImageResource(shoppingList[position].imageId)
          holder.textViewShoppingListName.text = shoppingList[position].name
          holder.cardViewList.setOnClickListener {
              val bundle = Bundle()
              bundle.putString("listId",shoppingList[position].id)
              context.startActivity(Intent(context,FoodList::class.java).putExtras(bundle))
          }
          holder.shareButton.setOnClickListener {
              val bundle = Bundle()
              bundle.putString("listId",shoppingList[position].id)
              context.startActivity(Intent(context, InvitationActivity::class.java).putExtras(bundle))
          }
          userProfileImageList = arrayListOf()
          for (i in  shoppingList[position].users){
                    userProfileImageList.add(i)
              }
            println(userProfileImageList)
              val adapter = UserProfileImageAdapter(context,  userProfileImageList)
              holder.recyclerViewProfilePics.adapter = adapter
              holder.recyclerViewProfilePics.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
              }
          }

    override fun getItemCount(): Int {
       return shoppingList.size+1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == shoppingList.size) R.layout.item_creation_list else R.layout.item_shopping_list
    }



    /**
     * Elimina una lista si solo queda un un usuario en ella, sino solo se sale el usuario de ella sin eliminarla
     * @author
     * @since 1.0
     */
    fun deleteItem(position: Int){
        var list : ShoppingList? = null

        db.collection(FirestoreCollections.USERS).document(Firebase.auth.currentUser?.email.toString()).update("listIds",FieldValue.arrayRemove(shoppingList[position].id)).addOnCompleteListener {}
        db.collection(FirestoreCollections.LISTS).document(shoppingList[position].id).get().addOnCompleteListener {
            if (it.isSuccessful){
                 list = it.result.toObject(ShoppingList::class.java)!!
            }
        }.addOnSuccessListener {
            if(list!!.users.size ==1){
                db.collection(FirestoreCollections.LISTS).document(list!!.id).delete().addOnCompleteListener {  }
            }else{
                db.collection(FirestoreCollections.LISTS).document(list!!.id).update("users",FieldValue.arrayRemove(Firebase.auth.currentUser?.email.toString())).addOnCompleteListener {  }
            }
        }
        Snackbar.make(view,activity.applicationContext.resources.getString(R.string.listDeleted)+" ${shoppingList[position].name}",Snackbar.LENGTH_SHORT).show()
        shoppingList.removeAt(position)
        notifyItemRemoved(position)
        if(shoppingList.isEmpty()) {
            notifyDataSetChanged()
        }
    }


}





