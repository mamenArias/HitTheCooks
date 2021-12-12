package com.appverse.hitthecooks.model

import java.io.Serializable

data class Item(var name :String="",var picUrl:String=""):Serializable{
    override fun toString(): String {
        return "Item(name='$name', imageUrl='$picUrl')"
    }
}
