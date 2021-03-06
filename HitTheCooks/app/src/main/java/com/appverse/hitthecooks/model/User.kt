package com.appverse.hitthecooks.model

/**
 * Clase que modela los objetos de tipo Usuario
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 * @param email Email del usuario
 * @param profileImage imagen de perfil del usuario
 * @param listsIds listas de la compra del usuario
 */
data class User(var email:String ="",var profileImage :String ="",var listsIds :ArrayList<String> = arrayListOf()) {

    /**
     * Genera un informe en modo texto con los atributos del usuario
     */
    override fun toString(): String {
        return "User(email='$email', profileImage='$profileImage', listsIds=$listsIds)"
    }
}