package com.appverse.hitthecooks.model

data class User(var email:String ="",var profileImage :String ="",var listsIds :ArrayList<String> = arrayListOf()) {


    override fun toString(): String {
        return "User(email='$email', listsIds=$listsIds)"
    }
}