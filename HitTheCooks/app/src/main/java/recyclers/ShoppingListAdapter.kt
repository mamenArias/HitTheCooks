package recyclers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.FoodList
import com.appverse.hitthecooks.ListCreationActivity
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.model.ShoppingList
import com.appverse.hitthecooks.utils.FirestoreCollections
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ShoppingListAdapter(private val shoppingList: ArrayList<ShoppingList>, private val view: View, private val context: Context): RecyclerView.Adapter<ShoppingListHolder>(){

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
          holder.itemView.findViewById<TextView>(R.id.createListText).setOnClickListener {
              context.startActivity(Intent(context,ListCreationActivity::class.java))
          }
      }else{
          holder.imageBackground.setImageResource(shoppingList[position].imageId)
          holder.textViewShoppingListName.text = shoppingList[position].name
          holder.cardViewList.setOnClickListener {
              //Aquí se debería pasar a la lista con la id correspondiente
              context.startActivity(Intent(context,FoodList::class.java))
          }
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
     */
    fun deleteItem(position: Int){
        var list : ShoppingList? = null
        val db = Firebase.firestore
        //  TODO("CAMBIAR EL EMAIL HARDCODEADO CUANDO ESTÉ EL LOGIN HECHO")
        val update = hashMapOf<String,Any>(
            "listsIds" to FieldValue.arrayRemove(shoppingList[position].id)
        )
        db.collection(FirestoreCollections.USERS).document("sergio@gmail.com").update(update).addOnCompleteListener {}
        db.collection(FirestoreCollections.LISTS).document(shoppingList[position].id).get().addOnCompleteListener {
            if (it.isSuccessful){
                 list = it.result.toObject(ShoppingList::class.java)!!
            }
        }.addOnSuccessListener {
            if(list!!.users.size <=1){
                db.collection(FirestoreCollections.LISTS).document(list!!.id).delete().addOnCompleteListener {  }
            }else{
                val emailToDelete = hashMapOf<String,Any>("users" to FieldValue.arrayRemove("sergio@gmail.com"))
                db.collection(FirestoreCollections.LISTS).document(shoppingList[position].id).update(emailToDelete)
            }

        }
        Snackbar.make(view,"Has borrado la lista de ${shoppingList[position].name}",Snackbar.LENGTH_SHORT).show()
        shoppingList.removeAt(position)
        notifyItemRemoved(position)

    }

}





