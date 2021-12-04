package com.appverse.hitthecooks.model

data class ShoppingList( val name: String="",val imageId:Int=0, var id :String ="",val users : ArrayList<String> = arrayListOf() ) {

    override fun toString(): String {
        return "ShoppingList(name='$name', imageId=$imageId, id='$id', users=$users)"
    }
}