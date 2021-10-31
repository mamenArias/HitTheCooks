package com.appverse.hitthecooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingListAdapter(val shoppingList : List<ShoppingList>): RecyclerView.Adapter<ShoppingListAdapter.ShoppingListHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ShoppingListHolder(layoutInflater.inflate(R.layout.item_shopping_list,parent,false))
    }

    override fun onBindViewHolder(holder: ShoppingListHolder, position: Int) {
      holder.render(shoppingList[position],position,shoppingList.size)
    }

    override fun getItemCount(): Int {
       return shoppingList.size
    }

    class ShoppingListHolder(private val view: View):RecyclerView.ViewHolder(view){
        fun render(shoppingList: ShoppingList,position: Int,sizeList: Int){
            view.findViewById<TextView>(R.id.shoppingListName).text = shoppingList.name
            if(position+1==sizeList) {
                val textView = view.findViewById<TextView>(R.id.createListText)
                textView.visibility = View.VISIBLE
            }
        }
    }

}