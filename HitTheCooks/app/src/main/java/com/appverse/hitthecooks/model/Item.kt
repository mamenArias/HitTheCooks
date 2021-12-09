package com.appverse.hitthecooks.model

data class Item(var name :String="",var imageUrl:String="", var items: ArrayList<Item> = arrayListOf()) {

}