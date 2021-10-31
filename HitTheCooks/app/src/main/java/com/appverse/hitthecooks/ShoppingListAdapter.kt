package com.appverse.hitthecooks

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.classes.ShoppingList
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class ShoppingListAdapter(private val shoppingList : MutableList<ShoppingList>,private val view: View): RecyclerView.Adapter<ShoppingListAdapter.ShoppingListHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListHolder {
       var layoutInflater = if(viewType == R.layout.item_shopping_list){
            LayoutInflater.from(parent.context).inflate(R.layout.item_shopping_list,parent,false)
        }else{
            LayoutInflater.from(parent.context).inflate(R.layout.item_creation_list,parent,false)
        }
        return ShoppingListHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ShoppingListHolder, position: Int) {
      if(position ==shoppingList.size){
          //Acción del botón iría aquí
      }else{
          holder.render(shoppingList[position])
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

    class ShoppingListHolder(private val view: View):RecyclerView.ViewHolder(view){
        fun render(shoppingList: ShoppingList){
            view.findViewById<TextView>(R.id.shoppingListName).text = shoppingList.name
            }
        }
    }



