package com.appverse.hitthecooks.model

import java.io.Serializable

/**
 * Clase que modela los objetos tipo Item, que representan cada producto en la app
 * @author Miguel Àngel Arcos
 * @author Mamen Arias
 * @author Manuel Carrillo
 * @author Christian García
 * @author Sergio López
 * @since 1.1
 * @param name Nombre del producto
 * @param picUrl Imagen que representa el producto
 */
data class Item(var name :String="",var picUrl:String=""):Serializable{

    /**
     * Crea un informe en modo texto del producto
     */
    override fun toString(): String {
        return "Item(name='$name', imageUrl='$picUrl')"
    }
}
