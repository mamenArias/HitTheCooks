package com.appverse.hitthecooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.appverse.hitthecooks.model.ShoppingList
import com.appverse.hitthecooks.databinding.ActivityShoppingListBinding

class ShoppingListActivity : SuperActivity() {

    private var  shoppingList = mutableListOf(
        ShoppingList("Lista de Casa de Pepe", listOf("Pepe","Manuel")),
            ShoppingList("Lista con Amigos", listOf("Fran","Alex")),
        ShoppingList("Lista con Boni", listOf("Fran","Alex")),
        ShoppingList("Lista con Jesús", listOf("Fran","Alex")),
        ShoppingList("Lista con compis de piso", listOf("Fran","Alex")),
        ShoppingList("Lista con Samuel", listOf("Fran","Alex")),
        ShoppingList("Lista con Pedro", listOf("Fran","Alex")),

        )

    private val binding by lazy { ActivityShoppingListBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(binding.root)
        drawerLayout.addView(binding.root, 1)
        navigationView.setCheckedItem(R.id.nav_lists)

        initRecycler()

        applyDarkMode(binding.root)


    }

   private fun initRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ShoppingListAdapter(shoppingList, binding.recyclerView,ShoppingListActivity@this)
        binding.recyclerView.adapter = adapter

       var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter,this))
       itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

}