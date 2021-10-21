package com.appverse.hitthecooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.appverse.hitthecooks.databinding.ActivityShoppingListBinding

class ShoppingListActivity : AppCompatActivity() {
    private val binding by lazy { ActivityShoppingListBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.shoppingList.setOnClickListener {
            Toast.makeText(this,"prueba",Toast.LENGTH_SHORT).show()
        }
    }
}