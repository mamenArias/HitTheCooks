package com.appverse.hitthecooks.model

import java.io.Serializable

/**
 * Clase que modela los elementos de las listas
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.4
 * @param name Nombre de la lista
 * @param imageId ID de la imagen de fondo seleccionada
 * @param id ID de la lista
 * @param users Lista de usuarios que comparten la lista
 */
data class ShoppingList( val name: String="",val imageId:Int=0, var id :String ="",val users : ArrayList<String> = arrayListOf(), val items : ArrayList<Item> = arrayListOf()) :Serializable {

    /**
     * Genera un informe en modo texto con los atributos de la lista
     */
    override fun toString(): String {
        return "ShoppingList(name='$name', imageId=$imageId, id='$id', users=$users, items='$items')"
    }
}