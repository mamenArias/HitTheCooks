package com.appverse.hitthecooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.appverse.hitthecooks.databinding.ActivityShoppingListBinding

class ShoppingListActivity : AppCompatActivity() {

    val  shoppingList : List<ShoppingList> = listOf(
        ShoppingList("Lista de Casa de Pepe", listOf("Pepe","Manuel")),
            ShoppingList("Lista con Amigos", listOf("Fran","Alex")),
        ShoppingList("Lista de la casa", listOf("Pablo","Paloma"))
        )

    private val binding by lazy { ActivityShoppingListBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecycler()

    }

    fun initRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ShoppingListAdapter(shoppingList)
        binding.recyclerView.adapter = adapter
    }
}