package com.appverse.hitthecooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.appverse.hitthecooks.model.ShoppingList
import com.appverse.hitthecooks.databinding.ActivityShoppingListBinding
import com.appverse.hitthecooks.model.User
import recyclers.ShoppingListAdapter

class ShoppingListActivity : AppCompatActivity() {

    private var  shoppingList = mutableListOf(
        ShoppingList("fsfsfd",0,"", arrayListOf<User>())
        )
    private val binding by lazy { ActivityShoppingListBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initRecycler()
    }

   private fun initRecycler(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ShoppingListAdapter(shoppingList, binding.recyclerView,ShoppingListActivity@this)
        binding.recyclerView.adapter = adapter

       var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter,this))
       itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

}