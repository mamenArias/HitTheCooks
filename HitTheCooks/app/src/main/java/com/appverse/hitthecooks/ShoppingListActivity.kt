package com.appverse.hitthecooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.appverse.hitthecooks.classes.ShoppingList
import com.appverse.hitthecooks.databinding.ActivityShoppingListBinding
import com.google.android.material.snackbar.Snackbar

class ShoppingListActivity : AppCompatActivity() {

    private var  shoppingList = mutableListOf(
        ShoppingList("Lista de Casa de Pepe", listOf("Pepe","Manuel")),
            ShoppingList("Lista con Amigos", listOf("Fran","Alex")),
        ShoppingList("Lista con Amigos", listOf("Fran","Alex")),
        ShoppingList("Lista con Amigos", listOf("Fran","Alex")),
        ShoppingList("Lista con Amigos", listOf("Fran","Alex")),
        ShoppingList("Lista con Amigos", listOf("Fran","Alex")),

        ShoppingList("Lista con Amigos", listOf("Fran","Alex")),

        )
    private val binding by lazy { ActivityShoppingListBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRecycler()


    }

   private fun initRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ShoppingListAdapter(shoppingList, binding.recyclerView)
        binding.recyclerView.adapter = adapter

       var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter,this))
       itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

}