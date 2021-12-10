package com.appverse.hitthecooks.model

data class Item(var name :String="",var picUrl:String="") {
    override fun toString(): String {
        return "Item(name='$name', imageUrl='$picUrl')"
    }
}
