package recyclers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.FoodList
import com.appverse.hitthecooks.ListCreationActivity
import com.appverse.hitthecooks.R
import com.appverse.hitthecooks.model.ShoppingList
import com.google.android.material.snackbar.Snackbar

class ShoppingListAdapter(private val shoppingList : MutableList<ShoppingList>,private val view: View, private val context: Context): RecyclerView.Adapter<ShoppingListHolder>(){

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

    fun deleteItem(position: Int){
        Snackbar.make(view,"Has borrado la lista de ${shoppingList[position].name}",Snackbar.LENGTH_SHORT).show()
        shoppingList.removeAt(position)
        notifyItemRemoved(position)
    }


}





